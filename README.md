# Library Project

A comprehensive library management system built with a microservices architecture using Java and Spring Boot. This project provides a scalable and resilient solution for managing books, users, and rentals.

## Architecture

This project follows a microservices pattern, with each service responsible for a specific business domain. The key components of the architecture are:

-   **Spring Cloud Gateway:** Acts as the single entry point for all client requests, routing them to the appropriate microservice.
-   **Eureka Discovery Server:** A service registry where all microservices register themselves, allowing for dynamic discovery and communication.
-   **Spring Cloud Config Server:** Provides centralized configuration management for all microservices, sourcing configurations from a Git repository.

## Microservices

The project is composed of the following microservices:

-   **`api-gateway`**: The main entry point for all API requests. It handles routing, and may also be used for cross-cutting concerns like authentication and rate limiting.
-   **`authentication`**: Manages user authentication and authorization, issuing tokens for secure access to other services.
-   **`catalog`**: Responsible for managing the library's collection of books and categories.
-   **`client`**: Handles the registration and management of library clients (customers).
-   **`configuration-server`**: Centralizes configuration for all microservices.
-   **`discovery`**: The service registry that allows services to find and communicate with each other.
-   **`rental`**: Manages the process of renting and returning books, including tracking due dates and availability.
-   **`user`**: Manages user accounts, roles, and permissions.

## Requirements

-   Java 21 or higher
-   Maven 3.x

## How to Build

To build all the microservices, run the following command from the root directory of the project:

```bash
mvn clean package
```

To build a specific microservice, navigate to its directory and run the same command.

## How to Run

To run the application, you need to start the infrastructure services first, followed by the application services.

### 1. Start the Infrastructure Services

1.  **Configuration Server:**
    ```bash
    cd configuration-server
    ./mvnw spring-boot:run
    ```

2.  **Discovery Server:**
    ```bash
    cd discovery
    ./mvnw spring-boot:run
    ```

### 2. Start the Application Services

Once the infrastructure services are running, you can start the application services in any order. For each service, navigate to its directory and run the `spring-boot:run` command.

-   **API Gateway:**
    ```bash
    cd api-gateway
    ./mvnw spring-boot:run
    ```
-   **Authentication Service:**
    ```bash
    cd authentication
    ./mvnw spring-boot:run
    ```
-   **Catalog Service:**
    ```bash
    cd catalog
    ./mvnw spring-boot:run
    ```
-   **Client Service:**
    ```bash
    cd client
    ./mvnw spring-boot:run
    ```
-   **Rental Service:**
    ```bash
    cd rental/rental
    ./mvnw spring-boot:run
    ```
-   **User Service:**
    ```bash
    cd user
    ./mvnw spring-boot:run
    ```

## API Endpoints

The primary API endpoints are exposed through the API Gateway. Here is a summary of the available endpoints:

### Client Service (`/api/client`)

| Method | Path                  | Description                               |
| :----- | :-------------------- | :---------------------------------------- |
| GET    | `/{id}`               | Find a client by their ID.                |
| GET    | `/`                   | Find clients by name and/or CPF.          |
| GET    | `/address/{id}`       | Find a client and their address by ID.    |
| GET    | `/address`            | Find clients and addresses by name or CPF.|
| POST   | `/`                   | Create a new client and associated user.  |

### Catalog Service (`/api/catalog`)

| Method | Path                  | Description                               |
| :----- | :-------------------- | :---------------------------------------- |
| GET    | `/book`               | Get a paginated list of all books.        |
| GET    | `/book/categories/{title}` | Search for books with categories.    |
| GET    | `/book/{id}`          | Find a book by its ID.                    |
| POST   | `/book`               | Add a new book to the catalog.            |
| DELETE | `/book/{id}`          | Delete a book by its ID.                  |
| PUT    | `/book/stock/{id}`    | Update the stock quantity of a book.      |
| GET    | `/book/by-ids`        | Find books by a list of IDs.              |
| GET    | `/categories`         | Get a list of all book categories.        |
| POST   | `/categories`         | Create a new book category.               |

### Rental Service (`/api/rental`)

| Method | Path                  | Description                               |
| :----- | :-------------------- | :---------------------------------------- |
| GET    | `/{id}`               | Get rental information for a client.      |
| POST   | `/`                   | Create a new book rental.                 |

### Authentication Service (`/api/authentication`)

| Method | Path                  | Description                               |
| :----- | :-------------------- | :---------------------------------------- |
| GET    | `/search`             | Find user details by email for authentication. |

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

This project is licensed under the MIT License.

