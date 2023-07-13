# Simple Java API Service

This repository contains a simple Java API project that demonstrates best practices for building a production-ready API using Java +17. The project incorporates various features
and technologies commonly used in professional Java applications: Spring Web, Docker, logging, unit tests, functional tests and monitoring.

---

# Assessment

## Objective

Create a standalone java application which allows users to manage their favourite recipes. It should allow adding, updating, removing and fetching recipes. Additionally users
should be able to filter available recipes based on one or more of the following criteria:

1. Whether or not the dish is vegetarian
2. The number of servings
3. Specific ingredients (either include or exclude)
4. Text search within the instructions.

## Search Examples

The application's API supports the following search requests:

1. Get all vegetarian recipes
2. Get recipes that serve 4 persons and include "potatoes" as an ingredient
3. Get recipes that do not include "salmon" as an ingredient and have "oven" in the instructions

## Requirements

Please ensure that we have some documentation about the architectural choices and also how to run the application. The project is expected to be delivered as a GitHub (or any other
public git hosting) repository URL.

All these requirements needs to be satisfied:

- [x] The application must be implemented as a RESTful API using Java. You are free to choose a Java framework of your preference.

[Spring Boot](https://spring.io/projects/spring-boot) was used for easily start the project,
using [Spring Web](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html) for managing the REST
API, [Spring Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html) for
health-check, [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference) for the DB
connection, [Spring Security](https://docs.spring.io/spring-security/site/docs/current/reference/html5/) for the API-Key validation and endpoint security
checks. [Spring Validation](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/validation.html) was used for API RequestBody validation.

- [x] Ensure that the code is production-ready, adhering to best practices and design patterns.

The code is well documented, it was commited using [ConventionalCommits](https://www.conventionalcommits.org/en/v1.0.0/), it logs where it is needed and was build using
production-ready design patterns: Factory Pattern, Singleton, MVC, DTO, Repository, ec. It also include compile processors such as [Google Error Prone](https://errorprone.info/)
and[Spring DevTools](https://docs.spring.io/spring-boot/docs/1.5.16.RELEASE/reference/html/using-boot-devtools.html) to comply with standard best-practices.

- [x] Document the REST API endpoints and their usage for future reference.

[springdoc-openapi](https://springdoc.org/) was used to automatically generate a SwaggerUI available at `/swagger-ui` and `/api-docs`.

- [x] Persist the data in a database for reliable storage and retrieval.

The application uses [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference) ([Hibernate](https://hibernate.org/orm/documentation/5.5/))
for database connection, [H2 Database](https://www.h2database.com/html/main.html) in memory database for development environment (`spring.profile: dev`)
and [MariaDB](https://mariadb.com/kb/en/documentation/) JDBC connector for production (`spring.profile: prod`), the later also available for develompent using the included Docker
compose image. The Entity Mapping was done with [MapStruct](https://mapstruct.org/documentation/stable/reference/html/).

- [x] Include unit tests to verify the functionality of individual components.

[SpringBootTest](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing.spring-boot-applications) was used for Unit Tests, this
includes [JUnit](https://junit.org/junit5/docs/current/user-guide/) and [Mockito](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html).

- [x] Integration tests must be present to test the interaction between different parts of the application.

[SpringBootTest](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing.spring-boot-applications)
and [AutoConfigureMockMvc](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing.spring-boot-applications.testing-autoconfigured-mvc-tests)
were used to create the integration tests, using H2 in memory database for test environment.

- [x] Container compatibility

[Google JIB](https://cloud.google.com/java/getting-started/jib?hl=it) was used to build the Java Docker Image and Docker Containers for testing locally.

---

## Architecture and Documentation

The Recipe Service is a production-ready Java application that provides a RESTful API for managing favorite recipes and ingredients. It leverages popular frameworks and tools,
including Spring, Spring Boot, Hibernate, Spring Data JPA, MariaDB, H2, SLF4J, JUnit, Docker, and Swagger API documentation. The project uses Java 17 and Maven for building and
dependency management.

The selection of these libraries and tools was driven by their maturity, community support, and ability to seamlessly integrate with each other. Here's an overview of their roles
in the application:

- **Spring**: The Spring framework offers extensive support for developing enterprise-grade Java applications, including dependency injection, data access, and transaction
  management. It promotes modular and testable code.

- **Spring Boot**: Spring Boot simplifies the development of stand-alone, production-ready Spring applications by providing opinionated defaults and auto-configuration. It
  eliminates boilerplate configuration, allowing developers to focus on business logic.

- **Hibernate**: Hibernate is an industry-standard Object-Relational Mapping (ORM) framework. It simplifies database operations by mapping Java objects to database tables and
  providing an intuitive API for querying and manipulating data.

- **MariaDB**: MariaDB is a robust, open-source relational database management system. It is known for its performance, scalability, and compatibility with MySQL, making it an
  ideal choice for data persistence.

- **H2**: H2 is an in-memory database engine that facilitates rapid development and testing. It allows developers to run tests against an in-memory database, ensuring a fast and
  isolated test environment.

- **SLF4J**: SLF4J provides a logging facade for various logging frameworks, including Logback and Log4j. It offers a unified logging API, enabling flexible logging configurations
  and integration with different logging implementations.

- **JUnit**: JUnit is a widely-used testing framework for Java applications. It supports writing unit tests that verify the correctness of individual components, ensuring
  robustness and maintainability.

- **Docker**: Docker enables the packaging of applications and their dependencies into lightweight containers. It simplifies deployment, scalability, and portability by providing a
  consistent runtime environment.

- **Swagger**: Swagger is an industry-standard toolset for documenting and interacting with APIs. It generates interactive documentation, making it easier to understand and consume
  the API endpoints.

## Prerequisites

- Java +17
- Maven
- Docker

## Getting Started

Follow the steps below to run the application:

1. Clone the [GitHub repository](https://github.com/parolaraul/simple-java-api)
2. Set up your development environment with JDK 17 and Apache Maven.
3. Configure the MariaDB database connection in the `application.yml` file, specifying the database URL, username, and password. Or launch the Dockerized mariadb included
   in `/src/docker/mariadb.yml`
4. Build the project using Maven:
   ```shell
   mvn clean install
   ```
5. Run the application using Maven:
   ```shell
   mvn spring-boot:run
   ```
6. Access the API documentation by visiting the Swagger UI endpoint: `http://localhost:8080/swagger-ui.html`. Use this interface to explore the available API endpoints and test
   them interactively.

If you prefer running the application in a Docker container, perform the following additional steps:

7. Build the Docker image from the project's root directory, il builds the image 'recipeapi' with tag 'latest':
   ```shell
   mvn compile jib:dockerBuild
   ```
8. Run the Docker container using the container description `src/docker/app.yml`:
9. Access the API documentation at the appropriate Docker container IP address and port: `http://localhost:8443/swagger-ui.html`.

