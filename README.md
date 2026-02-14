# Task Tracker

A microservices-based task management application built with Spring Boot, PostgreSQL, and Docker. The system follows a decoupled architecture where a core task service communicates with a notification service to log activities.

## Tech Stack

* **Language:** Java 17.
* **Framework:** Spring Boot (versions 4.0.1 and 4.0.2).
* **Database:** PostgreSQL 17.
* **Security:** Spring Security with JWT (JSON Web Tokens).
* **Containerization:** Docker & Docker Compose.
* **Build Tool:** Maven.

## Project Structure

The repository is organized into two primary microservices:

* **`todo-service`**: Handles user authentication, registration, and CRUD operations for tasks.
* **`notification-service`**: A lightweight service that receives and logs task-related notifications.

## Services Overview

### 1. Todo Service
* **Port:** 8081 (externally mapped) and 8080 (internal container port).
* **Responsibilities:**
    * User management and JWT-based authentication.
    * Task creation and retrieval.
    * Data persistence using PostgreSQL.
    * Notifying the Notification Service upon task creation.

### 2. Notification Service
* **Port:** 8082.
* **Responsibilities:**
    * Exposes a REST endpoint to receive notification payloads.
    * Logs task descriptions and User IDs to the console.

## Getting Started

### Prerequisites
* Docker and Docker Compose installed.
* Java 17 (for local development).

### Running with Docker Compose
The entire stack, including the database, can be launched using the root `docker-compose.yml` file:

```bash
docker-compose up --build
```

### 🌐 Services will be available at:

* **Todo Service**: `http://localhost:8081`
* **Notification Service**: `http://localhost:8082`
* **PostgreSQL**: `localhost:5432`

---

### 🚀 API Endpoints

#### 🔑 Authentication
* **POST `/api/register`**: Register a new user.
* **POST `/api/login`**: Authenticate and receive a JWT token.

#### 📝 Tasks (Requires Authentication)
* **GET `/api/task/{user_id}`**: Retrieve all tasks for a specific user.
* **POST `/api/task/{user_id}`**: Create a new task for a user.

#### 🛠️ User Management
* **GET `/api/users`**: List all registered users.
* **DELETE `/api/user/{id}`**: Delete a specific user by ID.
* **DELETE `/api/user/clear`**: Remove all users except for the "admin" user.

---

### ⚙️ Configuration

#### 📊 Database
In the Docker environment, the Todo service connects to PostgreSQL using the following credentials:
* **URL**: `jdbc:postgresql://db:5432/todo-1`
* **User**: `postgres`
* **Password**: `admin`

#### 🛡️ Security
* The application uses a stateless JWT filter.
* Tokens are validated against a dynamically generated secret key during service startup.
* Token expiration is set to 2 minutes.

