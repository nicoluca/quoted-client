# Quoted (JavaFX Client)
A JavaFX client-side quote management application.

## Description
This is a simple quote management desktop application. It allows you to create, edit, delete and view quotes and their respective sources and persist them in a database. You can also review random quotes and search quotes and sources by keyword.

It requires a server-side application to be running. The server-side application can be found [here](https://github.com/nicoluca/quoted-server).

## Features
- Create, edit, delete and view quotes
- Review random quotes
- Create, edit, delete and view sources (Books or Websites)
- Search quotes and by keyword or source, search sources by keyword

## User Manual
Can be found [here](UserManual.md).

## Requirements
- Java 19
- JavaFX 19
- Gradle 7.6 to build the project including dependencies

## Dependencies
- [SLF4J](http://www.slf4j.org) for logging (using [Log4j](https://logging.apache.org/log4j/))
- [Project Lombok](https://projectlombok.org) for automatic generation of getters, setters, constructors, etc.
- [Apache Http Client](https://hc.apache.org/httpcomponents-client-5.2.x/) and [Apache Http Core](https://hc.apache.org/httpcomponents-core-5.1.x/) for HTTP requests.
- [Jackson](https://github.com/FasterXML/jackson) for JSON serialization and deserialization.
- ([JUnit 5](https://junit.org/junit5) for unit testing.)
- ([Mockito](https://site.mockito.org) for mocking objects in unit tests.)

## Setup
1. Remove the suffix `.example` from `src/main/java/org/nico/quoted/config/Config.java.example` and fill out url, username and password for the server-side application.
2. To run the application, run `./gradlew run` in the project root directory.
3. (To run tests, run `./gradlew test` in the project root directory.)
4. (To build a jar file, uncomment the jar section in [build.gradle](build.gradle) run `./gradlew jar` in the project root directory. The executable 'fat' jar file will be located in `build/libs`. To run the jar file, run `java -jar build/libs/quoted-1.0-SNAPSHOT.jar` in the project root directory. To run 'normally' again, comment out the jar section in [build.gradle](build.gradle) and go to step 7.)