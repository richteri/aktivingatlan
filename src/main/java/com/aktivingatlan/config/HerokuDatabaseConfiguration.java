package com.aktivingatlan.config;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class HerokuDatabaseConfiguration implements EnvironmentAware {

  private final Logger log = LoggerFactory.getLogger(HerokuDatabaseConfiguration.class);

  private RelaxedPropertyResolver propertyResolver;

  private Environment environment;

  @Override
  public void setEnvironment(Environment environment) {
    this.environment = environment;
    this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource.");
  }

  @Bean(destroyMethod = "close")
  @ConditionalOnExpression("#{environment.acceptsProfiles('heroku')}")
  public DataSource dataSource(DataSourceProperties dataSourceProperties, JHipsterProperties jHipsterProperties) {
    log.debug("Configuring Heroku Datasource");
    
    String herokuUrl = propertyResolver.getProperty("heroku-url");
    if (herokuUrl != null) {
      log.info("Using Heroku, parsing their $DATABASE_URL to use it with JDBC");
      URI dbUri = null;
      try {
        dbUri = new URI(herokuUrl);
      } catch (URISyntaxException e) {
        throw new ApplicationContextException("Heroku database connection pool is not configured correctly");
      }
      String username = dbUri.getUserInfo().split(":")[0];
      String password = dbUri.getUserInfo().split(":")[1];
      String dbUrl = "jdbc:mysql://" +
      dbUri.getHost() +
      ':' +
      dbUri.getPort() +
      dbUri.getPath() +
      "?useUnicode=true&characterEncoding=utf8";

      HikariConfig config = new HikariConfig();
      config.setDataSourceClassName(dataSourceProperties.getDriverClassName());
      
      log.debug(dbUrl);
      
      config.addDataSourceProperty("url", dbUrl);
      config.addDataSourceProperty("user", username);
      config.addDataSourceProperty("password", password);
      
      if ("com.mysql.jdbc.jdbc2.optional.MysqlDataSource".equals(dataSourceProperties.getDriverClassName())) {
          config.addDataSourceProperty("cachePrepStmts", jHipsterProperties.getDatasource().isCachePrepStmts());
          config.addDataSourceProperty("prepStmtCacheSize", jHipsterProperties.getDatasource().getPrepStmtCacheSize());
          config.addDataSourceProperty("prepStmtCacheSqlLimit", jHipsterProperties.getDatasource().getPrepStmtCacheSqlLimit());
      }
      return new HikariDataSource(config);
    } else {
      throw new ApplicationContextException("Heroku database URL is not configured, you must set --spring.datasource.heroku-url=$DATABASE_URL");
    }
  }
}