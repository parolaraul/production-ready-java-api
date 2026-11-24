# Setup Guide – Recipe Service REST API

This guide provides instructions for setting up and running the Recipe Service REST API on a local development environment.

## 1. Prerequisites

Before starting, make sure you have installed:

- Java JDK 17 or later
- Apache Maven
- Git
- Docker (optional)

## 2. Clone the Repository

Open a terminal or command prompt and run:

```bash
git clone https://github.com/NeoZmanLAA/RecipeAPI.git
```

## 3. Navigate to the Project Folder

```bash
cd RecipeAPI
```

## 4. Configure the Database

Configure the database connection in the `application.yml` file.

The project supports:

- H2 Database for development and testing.
- MariaDB for production.

Make sure the database configuration matches your local environment.

## 5. Build the Project

Run the following command to download dependencies and build the application:

```bash
mvn clean install
```

## 6. Run the Application

Start the application using:

```bash
mvn spring-boot:run
```

Once the application starts successfully, access it using the configured application port.

Example:

```text
http://localhost:8081/
```

## 7. Run Tests

To execute the project's tests, run:

```bash
mvn test
```

The project was verified with the following test results:

- Tests run: 17
- Failures: 0
- Errors: 0
- Skipped: 0

## 8. Access API Documentation

The API documentation is provided through Swagger UI.

After starting the application, open:

```text
http://localhost:8081/swagger-ui
```

The exact documentation URL depends on the application's configuration.

## 9. Troubleshooting

If the application cannot start:

1. Check that Java JDK 17 or later is installed.
2. Verify that Maven is installed correctly.
3. Check the database configuration in `application.yml`.
4. Make sure the application port is not being used by another process.
5. Review the error messages in the terminal.

## 10. Additional Information

For more information about the project features, technologies, architecture and API functionality, refer to the `README.md` file.
