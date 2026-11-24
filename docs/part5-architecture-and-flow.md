# Part 5 — AI Comprehension: System Architecture & Execution Flow

## 1. System Overview & Main Functionalities
RecipeAPI is a standalone RESTful backend service built with Java 17 and Spring Boot 3.1.1 that allows users to manage and search their favourite recipes[cite: 4].

Based on the API endpoints exposed in RecipeResource and IngredientResource, the core functionalities include:
* Recipe Management (CRUD): Creating (POST /api/recipes), updating (PUT /api/recipes/{id}), retrieving by ID (GET /api/recipes/{id}), and deleting (DELETE /api/recipes/{id}) recipes.
* Dynamic Multi-Criteria Recipe Filtering (GET /api/recipes): Supporting paginated search queries filtered by vegetarian status, number of servings, included/excluded ingredients, and text search within instructions (RecipeCriteria + RecipeQBService)[cite: 4].
* Ingredient Management: Managing ingredient entities (IngredientResource, IngredientService) associated with recipes[cite: 8].
* API Documentation & Monitoring: Interactive Swagger/OpenAPI documentation (springdoc-openapi) and runtime health metrics (spring-boot-starter-actuator)[cite: 4].

---

## 2. Key Dependencies (pom.xml)
The project relies on the following core frameworks and libraries[cite: 4]:
* Spring Boot 3.1.1 (spring-boot-starter-web): Core framework providing embedded web server and RESTful MVC capabilities (@RestController, @RequestMapping).
* Spring Data JPA (spring-boot-starter-data-jpa) & Hibernate: Object-Relational Mapping (ORM) and repository abstraction for database persistence.
* Databases (h2 & mariadb-java-client): Embedded H2 database for development/testing (dev profile) and MariaDB JDBC driver for production (prod profile).
* Spring Security (spring-boot-starter-security): Authentication and endpoint security configuration (SecurityConfig)[cite: 8].
* MapStruct (1.5.3.Final): Compile-time bean mapper converting between JPA Domain Entities (Recipe) and Data Transfer Objects (RecipeDTO)[cite: 8].
* Springdoc OpenAPI (2.1.0): Automatically generates OpenAPI 3.0 specifications and Swagger UI (OpenApiConfig)[cite: 8].
* Spring Boot Validation (spring-boot-starter-validation): Declarative request payload validation.
* Spring Boot Actuator & Jib Plugin: Operational monitoring endpoints and container image building (recipeapi:latest).

---

## 3. Architecture & Module Overview
The codebase (com.parolaraul.recipeapi) follows a clean Layered Architecture separating HTTP transport, business logic, data mapping, and persistence[cite: 8]:

| Package / Layer | Key Classes | Responsibilities |
| :--- | :--- | :--- |
| rest (Presentation / API Layer) | RecipeResource, IngredientResource, BadRequestException, PaginationUtil, ResponseUtil[cite: 8] | Exposes REST endpoints under /api, validates incoming HTTP parameters/IDs, invokes service methods, and wraps responses with HTTP status codes and pagination headers. |
| service (Business Logic Layer) | RecipeService, RecipeQBService, RecipeDTO, RecipeCriteria[cite: 8] | Encapsulates core business operations (save, update, findOne, delete), builds dynamic Query-By-Criteria specifications (RecipeQBService), and maps between Entities and DTOs via MapStruct. |
| repository (Data Access Layer) | RecipeRepository, IngredientRepository[cite: 8] | Extends Spring Data JPA repositories to execute CRUD operations and dynamic queries against the underlying relational database. |
| domain (Domain Model Layer) | Recipe, Ingredient, RecipeCategory[cite: 8] | Defines JPA entities, table relationships (e.g., Recipe-Ingredient associations), and domain enums. |
| config (Configuration Layer) | SecurityConfig, DatabaseConfiguration, OpenApiConfig, ApplicationProperties[cite: 8] | Configures web security rules, database connections/profiles (dev/prod), Swagger documentation, and custom application properties. |

---

## 4. Major Execution Flow Trace
Below is the step-by-step execution trace for Creating a New Recipe (POST /api/recipes) and Filtering Recipes (GET /api/recipes):

### Flow A: Create a New Recipe (POST /api/recipes)
Client (HTTP POST /api/recipes + JSON Body)
-> [1] RecipeResource.createRecipe(RecipeDTO): Validates recipeDTO.id() is null (throws BadRequestException if ID exists), then calls recipeService.save(recipeDTO).
-> [2] RecipeService.save(RecipeDTO): Converts RecipeDTO to Recipe entity via MapStruct and calls recipeRepository.save(recipe).
-> [3] RecipeRepository.save(Recipe): Persists the Recipe record into H2 / MariaDB via Spring Data JPA.
-> [4] Return Path: RecipeService maps saved entity back to RecipeDTO (with generated ID); RecipeResource returns HTTP 201 Created with Location header (/api/recipes/{id}) and JSON body.

### Flow B: Search / Filter Recipes by Criteria (GET /api/recipes)
Client (HTTP GET /api/recipes?vegetarian=true&servings=4&page=0&size=10)[cite: 11]
-> [1] RecipeResource.getRecipesByCriteria(RecipeCriteria, Pageable): Delegates filtering and pagination to recipeQBService.findByCriteria(criteria, pageable)[cite: 11].
-> [2] RecipeQBService.findByCriteria(RecipeCriteria, Pageable): Translates RecipeCriteria fields into dynamic JPA Specifications, queries RecipeRepository with Specification + Pageable, and maps results to Page<RecipeDTO>[cite: 11].
-> [3] Return Path: PaginationUtil generates X-Total-Count and Link HTTP headers; RecipeResource returns HTTP 200 OK with pagination headers and List<RecipeDTO> body[cite: 11].