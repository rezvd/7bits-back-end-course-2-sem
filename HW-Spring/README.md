# Task tracker

###API
[Here](https://app.swaggerhub.com/apis-docs/pawlaz/base-web-development-restfull-task-manager/6.0.0) 
you can find an API for the application.


### Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- [PostgreSQL 9.6](https://www.postgresql.org/download/)


### Create a database and a user
```
psql -f createDB.sql
```

### Test
```
mvn test
```

### Build
```
mvn install
```

### Start
```
java -jar /target/hw-spring-1.0-SNAPSHOT.jar
```