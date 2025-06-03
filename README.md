
# Library Project

This is a Java-based library management system. The project allows you to manage books, users, and book loans efficiently.

## Features

- Add, edit, and remove books
- Register and manage users
- Loan and return books
- Search the collection and view loan history

## Requirements

- Java 17 or higher
- Maven (optional, for dependency management)

## How to Build

```bash
javac -d out src/**/*.java
```

Or, if using Maven:

```bash
mvn clean package
```

## How to Run

If compiled manually:

```bash
java -cp out main.Main
```

If using Maven:

```bash
mvn exec:java -Dexec.mainClass="main.Main"
```

## Project Structure

```
src/
  main/
    java/
      ...source code...
  test/
    java/
      ...tests...
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

This project is licensed under the MIT License.
