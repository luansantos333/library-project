# Library Project

A simple Java-based library management system. This project provides functionalities to manage books, users, and book loans.

## Features

*   **Book Management:** Add, edit, and remove books from the library's collection.
*   **User Management:** Register new users and manage existing ones.
*   **Loan System:** Handle book loans and returns.
*   **Search and History:** Search for books and view the history of loans.

## Technologies Used

*   **Java:** Core programming language for the application.
*   **Maven:** For dependency management and building the project.

## Getting Started

### Prerequisites

*   Java 17 or newer.
*   Maven 3.6 or newer.
*   A Git client.

### Installation

1.  Clone the repository:
    ```bash
    git clone <repository-url>
    ```
2.  Navigate to the project directory:
    ```bash
    cd library-project
    ```

## Build and Run

### Building the Project

You can build the project using Maven:

```bash
mvn clean package
```

This will compile the source code, run tests, and package the application into a JAR file in the `target` directory.

### Running the Application

Once the project is built, you can run it using:

```bash
mvn exec:java -Dexec.mainClass="main.Main"
```

Alternatively, you can run the JAR file directly:

```bash
java -jar target/library-project-1.0-SNAPSHOT.jar
```

*(Note: The JAR file name might differ based on the project version in `pom.xml`)*

## Development

This project can be imported into any Java-supporting IDE. The following IDEs are already configured in the `.gitignore`, ensuring a clean setup:

*   **IntelliJ IDEA:** Open the `pom.xml` file to import the project.
*   **Eclipse (STS):** Use the `File -> Import -> Existing Maven Projects` option.
*   **Visual Studio Code:** Open the project folder and make sure you have the "Language Support for Java(TM) by Red Hat" and "Maven for Java" extensions installed.
*   **NetBeans:** Open the project via `File -> Open Project`.

## Project Structure

```
.
├── src
│   ├── main
│   │   └── java
│   │       └── ... (source code)
│   └── test
│       └── java
│           └── ... (tests)
├── .gitignore
├── pom.xml
└── README.md
```

## Contributing

Contributions are welcome! Please feel free to open an issue or submit a pull request. When contributing, please ensure your IDE-specific files are not committed, as specified in the `.gitignore` file.

## License

This project is licensed under the MIT License.