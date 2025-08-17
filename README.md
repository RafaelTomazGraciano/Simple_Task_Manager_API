# Simple Task Manager API

[![Java](https://img.shields.io/badge/Java-21-red.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue.svg)](https://www.postgresql.org/)
[![License](https://img.shields.io/github/license/RafaelTomazGraciano/Simple_Task_Manager_API)](LICENSE)

## Overview

This repository contains two implementations of a simple RESTful API for managing personal tasks:

- **Java_Task_Manager_API**: Built using only core Java and JDBC (no frameworks).
- **Spring_Task_Manager_API**: Built using Spring Boot, Spring Data JPA, and other Spring ecosystem tools.

Both APIs use PostgreSQL for data persistence.

### Motivation

I created this project to deepen my understanding of API development in Java. The first version uses only Java and JDBC, demonstrating how to build an API from scratch. The second version uses Spring Boot to highlight how modern frameworks can simplify and accelerate development.

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Setup](#setup)
- [Testing](#testing)
- [API Documentation](#api-documentation)

---

## Features

- Create, read, update, and delete tasks
- PostgreSQL database integration
- Two implementations: pure Java/JDBC and Spring Boot

---

## Tech Stack

| API                      | Language | Frameworks/Libraries | Database     |
|--------------------------|----------|----------------------|--------------|
| Java_Task_Manager_API    | Java 21  | JDBC                 | PostgreSQL 17|
| Spring_Task_Manager_API  | Java 21  | Spring Boot 3.5.4, JPA| PostgreSQL 17|

---

## Setup

### Prerequisites

- Java 21
- Maven
- PostgreSQL 17
- Git

---

### Java_Task_Manager_API

1. **Configure Database**
   - Edit `src/main/resources/db.properties` with your PostgreSQL credentials.

2. **Build & Run**

   ```sh
   cd Java_Task_Manager_API
   mvn clean package
   java -cp target/classes org.graciano.Main
   ```

---

### Spring_Task_Manager_API

1. **Configure Database**
   - Edit `src/main/resources/application.properties` with your PostgreSQL credentials.

2. **Build & Run**

   ```sh
   cd Spring_Task_Manager_API
   ./mvnw spring-boot:run
   ```

---

## Testing

- Use tools like [Postman](https://www.postman.com/) or [curl](https://curl.se/) to send HTTP requests to the API endpoints.
- Example:
  ```sh
  curl -X GET http://localhost:8080/tasks
  ```

---

## API Documentation

Both APIs expose endpoints for:

- `GET /tasks/{id}` - Get a task by ID
- `GET /tasks` - List all tasks
- `POST /tasks` - Create a new task
- `PUT /tasks/{id}` - Update a task
- `DELETE /tasks/{id}` - Delete a task


