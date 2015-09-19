package com.aktivingatlan.repository.search;

import com.aktivingatlan.domain.Statement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Statement entity.
 */
public interface StatementSearchRepository extends ElasticsearchRepository<Statement, Long> {
}
