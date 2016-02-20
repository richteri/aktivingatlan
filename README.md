# aktivingatlan.com

The project goal is to replace the legacy CRM system of a real estate agency. The legacy system consists of multiple windows desktop applications:

* Real estate property DBMS (properties, owners, detailed information)
* Satement/contract management software (agreements about revealing addresses)
* Website (separate DB with images and contact information)
* Image management software (processing images, watermarks, slideshows, etc.)

Some of the drawbacks of the current system are:

* The operation and maintenance of the legacy system proves to be more difficult with the new OS versions and Workstation/Server upgrades.
* Only manual backups are available.
* There are redundancies and related data anomalies in the data management process

The goal is to provide a user-friendly, centralized, homogeneous system that scales with the number of employees and clients. The system also has to be open, secure and easy to maintain.

## Stack

See: 

* [JHipster](https://jhipster.github.io): Spring, RESTful Services, SPA, AngularJS, etc.
* [Cloudinary](http://cloudinary.com): Image And Video Management In The Cloud

## DevOps

See:

* [DigitalOcean](https://www.digitalocean.com)
* [Dokku](http://dokku.viewdocs.io/dokku)
* [Docker](https://www.docker.com)

### CI/CD

1. Create droplet

  * One-click Apps / Dokku v0.4.12 on 14.04 
  * Size: $10/mo
  * Chose Region
  * Add SSH keys
  * Chose Hostname

2. Add swap (optional, but good to have during builds)

  Guide: [How to add swap on Ubuntu @ DigitalOcean](https://www.digitalocean.com/community/tutorials/how-to-add-swap-on-ubuntu-14-04)

  ```
  sudo fallocate -l 4G /swapfile
  sudo chmod 600 /swapfile
  sudo mkswap /swapfile
  sudo swapon /swapfile
  sudo echo "/swapfile   none    swap    sw    0   0" >> /etc/fstab
  sudo sysctl vm.swappiness=10
  sudo echo "vm.swappiness=10" >> /etc/sysctl.conf
  sudo sysctl vm.vfs_cache_pressure=50
  sudo echo "vm.vfs_cache_pressure=50" >> /etc/sysctl.conf
  ```
3. Configure Dokku SSH keys

  ```
  cat /root/.ssh/authorized_keys | sshcommand acl-add dokku default-dokku-acl
  ```

4. Install Dokku DB plug-in

  ```
  dokku plugin:install https://github.com/dokku/dokku-mysql.git mysql
  ```

5. Create the App and its services
  ```
  dokku apps:create aktivingatlan-app
  dokku mysql:create aktivingatlan-db
  dokku mysql:link aktivingatlan-db aktivingatlan-app
  dokku config:set aktivingatlan-app MAVEN_CUSTOM_OPTS="-Pprod -DskipTests=true"
  dokku config:set aktivingatlan-app MAVEN_CUSTOM_GOALS=package
  ```
  
6. Force mysql to use UTF-8 collation and charset

  ```
  # connect to mysql container
  docker exec -it dokku.mysql.aktivingatlan-db bash -l
  
  # add extra configuration parameters to /etc/mysql/conf.d/charset-utf8mb4.cnf
  sprintf '[client]\ndefault-character-set = utf8mb4\n\n[mysql]\ndefault-character-set = utf8mb4\n\n[mysqld]\ncharacter-set-client-handshake = FALSE\ncharacter-set-server = utf8mb4\ncollation-server = utf8mb4_unicode_ci' > /etc/mysql/conf.d/charset-utf8mb4.cnf
  
  # logout and restart container
  dokku mysql:restart aktivingatlan-db
  
  # alter database and check settings
  ALTER DATABASE `aktivingatlan-db` CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
  SHOW VARIABLES WHERE Variable_name LIKE 'character\_set\_%' OR Variable_name LIKE 'collation%';
  
  
  # restart all linked apps
  dokku ps:restart aktivingatlan-app
  
  ```
6. Switch Node to development for CI

  ```
  dokku config:set aktivingatlan-app NODE_ENV=development
  ```
7. Define build packs for (Herokuish) Dokku in file `.buildpacks` in project root with the contents:

  ```
  https://github.com/heroku/heroku-buildpack-nodejs.git
  https://github.com/heroku/heroku-buildpack-java.git
  ```

8. Add `grunt-cli` to Node dependencies in `package.json`

9. Add `Procfile` to indicate App type and its boot parameters
  ```
  web: java $JAVA_OPTS -jar target/*.war  --spring.profiles.active=prod,heroku --server.port=$PORT --spring.datasource.heroku-url=$DATABASE_URL --metrics.jmx.enabled=false --spring.datasource.jmx-enabled=false --spring.jmx.enabled=false --management.security.enabled=false --endpoints.jmx.enabled=false
  ```
0. Create Dokku specific DataSource bean 
  [eg. HerokuDatabaseConfiguration.java](../master/src/main/java/com/aktivingatlan/config/HerokuDatabaseConfiguration.java)

0. Setup the Git repository for pushing code to Dokku

  ```
  git remote add dokku dokku@<IP or HOST>:aktivingatlan-app
  git push dokku master
  ```
## Useful commands

Connect to a Docker container:

    docker exec -it <container-name> bash -l

Connect to MySQL:

    mysql -u <username> -p <database-name>
    
Display container logs/output on the fly:

    docker logs -f <container-id>

## ToDo

* Refactor spaghetti into directives
* Find library for PDF generation
* Find time for tinkering;) 

# aktivingatlan (generated)

This application was generated using JHipster, you can find documentation and help at [https://jhipster.github.io](https://jhipster.github.io).

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools (like
[Bower][] and [BrowserSync][]). You will only need to run this command when dependencies change in package.json.

    npm install

We use [Grunt][] as our build system. Install the grunt command-line tool globally with:

    npm install -g grunt-cli

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

    mvn
    grunt

Bower is used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in `bower.json`. You can also run `bower update` and `bower install` to manage dependencies.
Add the `-h` flag on any command to see how you can use it. For example, `bower update -h`.

# Building for production

To optimize the aktivingatlan client for production, run:

    mvn -Pprod clean package

This will concatenate and minify CSS and JavaScript files. It will also modify `index.html` so it references
these new files.

To ensure everything worked, run:

    java -jar target/*.war --spring.profiles.active=prod

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

# Testing

Unit tests are run by [Karma][] and written with [Jasmine][]. They're located in `src/test/javascript` and can be run with:

    grunt test



# Continuous Integration

To setup this project in Jenkins, use the following configuration:

* Project name: `aktivingatlan`
* Source Code Management
    * Git Repository: `git@github.com:xxxx/aktivingatlan.git`
    * Branches to build: `*/master`
    * Additional Behaviours: `Wipe out repository & force clone`
* Build Triggers
    * Poll SCM / Schedule: `H/5 * * * *`
* Build
    * Invoke Maven / Tasks: `-Pprod clean package`
* Post-build Actions
    * Publish JUnit test result report / Test Report XMLs: `build/test-results/*.xml`

[JHipster]: https://jhipster.github.io/
[Node.js]: https://nodejs.org/
[Bower]: http://bower.io/
[Grunt]: http://gruntjs.com/
[BrowserSync]: http://www.browsersync.io/
[Karma]: http://karma-runner.github.io/
[Jasmine]: http://jasmine.github.io/2.0/introduction.html
[Protractor]: https://angular.github.io/protractor/
