# Simple Java API Service

This repository contains a simple Java API project that demonstrates best practices for building a production-ready API using Java +17. The project incorporates various features and technologies commonly used in professional Java applications: Spring Web, Docker, logging, unit tests, functional tests and monitoring.

---
# Assessment
## Objective
Create a standalone java application which allows users to manage their favourite recipes. It should allow adding, updating, removing and fetching recipes. Additionally users should be able to filter available recipes based on one or more of the following criteria:

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

Please ensure that we have some documentation about the architectural choices and also how to run the application. The project is expected to be delivered as a GitHub (or any other public git hosting) repository URL.

All these requirements needs to be satisfied:

- [ ] The application must be implemented as a RESTful API using Java. You are free to choose a Java framework of your preference.
- [ ] Ensure that the code is production-ready, adhering to best practices and design patterns.
- [ ] Document the REST API endpoints and their usage for future reference.
- [ ] Persist the data in a database for reliable storage and retrieval.
- [ ] Include unit tests to verify the functionality of individual components.
- [ ] Integration tests must be present to test the interaction between different parts of the application.

---

## Architecture and Documentation
The Recipe Service is a production-ready Java application that provides a RESTful API for managing favorite recipes and ingredients. It leverages popular frameworks and tools, including Spring, Spring Boot, Hibernate, Spring Data JPA, MariaDB, H2, SLF4J, JUnit, Docker, and Swagger API documentation. The project uses Java 17 and Maven for building and dependency management.

The selection of these libraries and tools was driven by their maturity, community support, and ability to seamlessly integrate with each other. Here's an overview of their roles in the application:

- **Spring**: The Spring framework offers extensive support for developing enterprise-grade Java applications, including dependency injection, data access, and transaction management. It promotes modular and testable code.

- **Spring Boot**: Spring Boot simplifies the development of stand-alone, production-ready Spring applications by providing opinionated defaults and auto-configuration. It eliminates boilerplate configuration, allowing developers to focus on business logic.

- **Hibernate**: Hibernate is an industry-standard Object-Relational Mapping (ORM) framework. It simplifies database operations by mapping Java objects to database tables and providing an intuitive API for querying and manipulating data.

- **MariaDB**: MariaDB is a robust, open-source relational database management system. It is known for its performance, scalability, and compatibility with MySQL, making it an ideal choice for data persistence.

- **H2**: H2 is an in-memory database engine that facilitates rapid development and testing. It allows developers to run tests against an in-memory database, ensuring a fast and isolated test environment.

- **SLF4J**: SLF4J provides a logging facade for various logging frameworks, including Logback and Log4j. It offers a unified logging API, enabling flexible logging configurations and integration with different logging implementations.

- **JUnit**: JUnit is a widely-used testing framework for Java applications. It supports writing unit tests that verify the correctness of individual components, ensuring robustness and maintainability.

- **Docker**: Docker enables the packaging of applications and their dependencies into lightweight containers. It simplifies deployment, scalability, and portability by providing a consistent runtime environment.

- **Swagger**: Swagger is an industry-standard toolset for documenting and interacting with APIs. It generates interactive documentation, making it easier to understand and consume the API endpoints.

## Prerequisites
- Java +17
- Maven
- Docker

## Getting Started

Follow the steps below to run the application:

1. Clone the [GitHub repository](https://github.com/parolaraul/simple-java-api)
2. Set up your development environment with JDK 17 and Apache Maven.
3. Configure the MariaDB database connection in the `application.yml` file, specifying the database URL, username, and password. Or launch the Dockerized mariadb included in `/src/docker/mariadb.yml`
4. Build the project using Maven:
   ```shell
   mvn clean install
   ```
5. Run the application using Maven:
   ```shell
   mvn spring-boot:run
   ```
6. Access the API documentation by visiting the Swagger UI endpoint: `http://localhost:8080/swagger-ui.html`. Use this interface to explore the available API endpoints and test them interactively.

If you prefer running the application in a Docker container, perform the following additional steps:

7. Build the Docker image from the project's root directory, il builds the image 'recipeapi' with tag 'latest':
   ```shell
   mvn compile jib:dockerBuild
   ```
8. Run the Docker container using the container description `src/docker/app.yml`:
9. Access the API documentation at the appropriate Docker container IP address and port: `http://localhost:8443/swagger-ui.html`.

