# Fictional Public Library API

A RESTful API for managing a fictional public library's book collection. This Spring Boot application provides endpoints for creating, reading, updating, and deleting books in a library database.

## 📚 Features

- **Book Management**: Complete CRUD operations for books
- **RESTful API Design**: Following best practices for API design
- **Data Validation**: Input validation for book data
- **Exception Handling**: Global exception handling with meaningful error messages
- **H2 In-Memory Database**: For development and testing
- **MySQL Support**: Configurable for production use

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 3.4.4**
- **Spring Data JPA** - For database operations
- **H2 Database** - In-memory database for development and testing
- **MySQL** - Optional configuration for production
- **Lombok** - To reduce boilerplate code
- **Maven** - Dependency management and build tool
- **JUnit 5 & Mockito** - For testing

## 📋 API Endpoints

| Method | URL               | Description                | Request Body | Success Response                      |
|--------|-------------------|----------------------------|-------------|--------------------------------------|
| GET    | /v1/books         | Get all books              | None        | 200 OK with array of books           |
| GET    | /v1/books/{id}    | Get a book by ID           | None        | 200 OK with book details             |
| POST   | /v1/books         | Create a new book          | Book object | 201 Created with the created book    |
| PUT    | /v1/books/{id}    | Update an existing book    | Book object | 200 OK with the updated book         |
| DELETE | /v1/books/{id}    | Delete a book              | None        | 204 No Content                       |

## 🚀 Getting Started

### Prerequisites

- Java Development Kit (JDK) 17 or later
- Maven 3.6+ (or you can use the included Maven wrapper)
- Git

### Clone the Repository

```bash
git clone https://github.com/muhammed-ilyas/fictional-public-library.git
cd fictional-public-library
```

### Build and Run

Using Maven:

```bash
mvn clean install
mvn spring-boot:run
```

Using Maven Wrapper (included in the project):

```bash
./mvnw clean install
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

### Access the Application

- The API will be available at: http://localhost:8080/v1/books
- H2 Console (for development): http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:librarydb`
  - Username: `sa`
  - Password: `password`

## 📝 Configuration

The application uses an H2 in-memory database by default. If you want to use MySQL:

1. Uncomment the MySQL configuration in `src/main/resources/application.properties`
2. Comment out the H2 configuration
3. Add the MySQL dependency in `pom.xml`
4. Make sure you have a MySQL server running with the correct credentials

## 🧪 Testing

Run the tests using Maven:

```bash
mvn test
```

The project includes:
- Unit tests for services
- Repository tests
- Controller tests

## 📦 Project Structure

```
├── src
│   ├── main
│   │   ├── java/com/aim/fictionalpubliclibrary
│   │   │   ├── controllers       # REST API controllers
│   │   │   ├── dtos              # Data Transfer Objects
│   │   │   ├── exceptions        # Custom exceptions and handler
│   │   │   ├── models            # Entity models
│   │   │   ├── repositories      # Spring Data JPA repositories
│   │   │   ├── services          # Business logic services
│   │   │   └── FictionalPublicLibraryApplication.java
│   │   └── resources
│   │       └── application.properties # Application configuration
│   └── test                      # Test classes
└── pom.xml                       # Maven configuration
```
