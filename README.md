# RecipeAPI – Simple Java REST API

## Project Overview

RecipeAPI is a Java-based RESTful API application that allows users to manage their favourite recipes. Users can create, retrieve, update, delete, and search recipes based on different criteria.

The application is developed using **Java 17**, **Spring Boot**, and **Maven**. It demonstrates software development best practices, including layered architecture, database integration, API security, automated testing, logging, and containerisation.

## Main Features

- Create new recipes.
- Retrieve all available recipes.
- Retrieve a specific recipe.
- Update existing recipes.
- Delete recipes.
- Filter recipes based on vegetarian status.
- Search recipes by number of servings.
- Include or exclude specific ingredients.
- Search recipe instructions using text keywords.

## Search Examples

The API supports searches such as:

1. Retrieve all vegetarian recipes.
2. Find recipes that serve four people and include potatoes.
3. Find recipes that exclude salmon and contain the word "oven" in their instructions.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 17 | Main programming language |
| Spring Boot | Application framework |
| Spring Web | REST API development |
| Spring Data JPA | Database operations |
| Hibernate | Object-relational mapping |
| MariaDB | Production database |
| H2 Database | Development and testing database |
| Spring Security | API security and API-key validation |
| Spring Actuator | Application health monitoring |
| Maven | Dependency and build management |
| JUnit 5 | Unit testing |
| Mockito | Mocking and testing |
| Docker | Application containerisation |
| Swagger / OpenAPI | API documentation |
| SLF4J | Application logging |
| MapStruct | Object mapping |

## System Architecture

The application follows a layered architecture based on the Model-View-Controller (MVC) approach and Repository pattern.

The main architectural components include:

- **Controller Layer:** Handles incoming HTTP requests and API responses.
- **Service Layer:** Contains the application's business logic.
- **Repository Layer:** Manages database operations using Spring Data JPA.
- **Entity Layer:** Represents the application's database entities.
- **DTO (Data Transfer Object):** Transfers data between application components.
- **Database Layer:** Stores recipe information using MariaDB or H2.

These architectural choices improve code organisation, maintainability, reusability, and testability.

## Project Requirements

The application addresses the following requirements:

- [x] Implement a RESTful API using Java.
- [x] Use a Java framework such as Spring Boot.
- [x] Apply appropriate software design patterns and coding practices.
- [x] Provide documentation for REST API endpoints.
- [x] Persist application data using a database.
- [x] Include unit tests.
- [x] Include integration tests.
- [x] Support Docker containerisation.

## Testing

The project includes automated testing to verify application functionality and reliability.

- **JUnit 5:** Tests individual application components.
- **Mockito:** Creates mock objects for isolated testing.
- **Spring Boot Test:** Supports application-level testing.
- **MockMvc:** Tests HTTP requests and controller responses.
- **H2 Database:** Provides an in-memory database for testing.

## API Documentation

The application uses Springdoc OpenAPI to generate interactive API documentation.

After starting the application, access the documentation through:

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`

## Prerequisites

Before running the application, ensure that the following software is installed:

- Java Development Kit (JDK) 17 or later.
- Apache Maven (if not using the Maven Wrapper).
- Docker (optional, for containerisation).
- Git.

## Getting Started

To obtain the project, clone the team repository:

```bash
git clone https://github.com/NeoZmanLAA/RecipeAPI.git
cd RecipeAPI
```

For detailed installation instructions, dependency information, database configuration, and commands to build and run the application, refer to [SETUP.md](SETUP.md).

## Version Control and Team Collaboration

This project is maintained using **Git and GitHub** for version control and team collaboration.

The team uses the following practices:

- A shared GitHub repository for project management.
- Individual branches for team members to develop and manage changes.
- Meaningful commit messages to record development activities.
- Pull Requests to review and merge changes into the main branch.
- A shared project configuration to maintain consistency.

## Repository Structure

```text
RecipeAPI/
├── .mvn/
├── src/
│   ├── main/
│   └── test/
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
└── SETUP.md
```

*Note: This is a simplified representation of the project structure.*

## Original Project

The application is based on the original Simple Java API project:

https://github.com/parolaraul/simple-java-api

The original project has been imported into the team's GitHub repository for collaborative development and version control.

  
