# 🚀 Task Tracker — Scalable Microservices Task Management Platform

A production-style **microservices-based task management system** demonstrating real-world backend engineering practices including **JWT authentication**, **service decoupling**, **containerized deployment**, and **inter-service communication**.

Built using **Java**, **Spring Boot**, **PostgreSQL**, and **Docker**, this project simulates a scalable backend where a core task service communicates with a notification service to process activity events.

---

## 🎯 Project Highlights

- 🧱 Microservices architecture with clear service boundaries
- 🔐 Stateless JWT-based authentication & authorization
- 🐳 Fully containerized environment using Docker Compose
- 📡 REST-based inter-service communication
- 🗄️ PostgreSQL-backed persistent storage
- ⚙️ Clean RESTful API design
- 🧩 Production-style backend structure
- 🔄 Decoupled event notification flow

---

## 🏗️ System Architecture

```
Client
  │
  ▼
┌──────────────────────┐
│      Todo Service     │
│  Auth + Task Logic    │
└──────────┬───────────┘
           │ Kafka Stream
           ▼
┌──────────────────────┐
│  Notification Service │
│     Event Logging     │
└──────────────────────┘
           │
           ▼
      PostgreSQL
```

---

## 🧱 Microservices Overview

### 📝 Todo Service

**Ports**
- External: `8081`
- Internal: `8080`

**Responsibilities**
- User registration & authentication
- JWT token issuance and validation
- Task CRUD operations
- Database persistence
- Sends task events to Notification Service

---

### 🔔 Notification Service

**Port**
- `8082`

**Responsibilities**
- Receives task event payloads
- Logs user actions and task descriptions
- Demonstrates service decoupling

---

## 🛠️ Tech Stack

| Category | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 4.x |
| Database | PostgreSQL 17 |
| Security | Spring Security + JWT |
| Containerization | Docker & Docker Compose |
| Build Tool | Maven |

---

## 📁 Project Structure

```
task-tracker/
│
├── todo-service/
│   ├── controllers/
│   ├── services/
│   ├── security/
│   └── repositories/
│
├── notification-service/
│   ├── controllers/
│   └── services/
│
└── docker-compose.yml
```

---

## 🚀 Getting Started

### ✅ Prerequisites

- Docker & Docker Compose
- Java 17 (optional for local development)

---

### ▶️ Run the Application

```bash
docker-compose up --build
```

---

## 🌐 Services

| Service | URL |
|---|---|
| Todo Service | http://localhost:8081 |
| Notification Service | http://localhost:8082 |
| PostgreSQL | localhost:5432 |

---

## 🔗 API Endpoints

### 🔐 Authentication

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/register` | Register new user |
| POST | `/api/login` | Authenticate & receive JWT |

---

### 📝 Tasks *(Authentication Required)*

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/task/{user_id}` | Retrieve tasks |
| POST | `/api/task/{user_id}` | Create new task |

---

### 👤 User Management

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/users` | List users |
| DELETE | `/api/user/{id}` | Delete user |
| DELETE | `/api/user/clear` | Clear users except admin |

---

## ⚙️ Configuration

### 🗄️ Database (Docker Environment)

```
URL      : jdbc:postgresql://db:5432/todo-1
User     : postgres
Password : admin
```

---

### 🔒 Security

- Stateless JWT authentication
- Runtime-generated signing key
- Token expiration: **2 minutes**
- Spring Security filter-based validation

---

## 📌 Engineering Design Decisions

- Separate services to demonstrate microservice boundaries
- Notification logic extracted to simulate asynchronous/event-driven systems
- Stateless authentication for scalability
- Containerization ensures reproducibility & portability
- Clean layered architecture (Controller → Service → Repository)

---

## 🧠 Future Improvements

- Replace REST notifications with message broker (Kafka/RabbitMQ)
- Add API Gateway & centralized authentication
- Implement distributed tracing & logging
- Introduce CI/CD pipeline with automated tests
- Add rate limiting & monitoring

---
