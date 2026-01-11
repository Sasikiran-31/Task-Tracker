# 📋 Event-Driven Task Tracker

A robust, **microservices-oriented task management system** built with **Java 17** and **Spring Boot**. This project demonstrates an **Event-Driven Architecture (EDA)** by decoupling task management logic from event processing using **Apache Kafka**.

Designed to showcase backend engineering best practices such as asynchronous messaging, fault tolerance, and containerized infrastructure.

---

## ✨ Features

* **Decoupled Architecture**
  Separates the Task **Producer (CLI)** from the Task **Consumer** to improve scalability and availability.

* **Event-Driven Communication**
  Uses **Apache Kafka** to publish and consume task lifecycle events (`CREATE`, `UPDATE`, `DELETE`) asynchronously.

* **Custom Persistence Layer**
  Implements a custom repository that persists tasks to a local **JSON file**, with automated backups before every write.

* **Fault Tolerance & Error Handling**
  Gracefully handles Kafka connectivity issues and file I/O failures.

* **Interactive Command Line Interface**
  User-friendly CLI to manage task states: `PENDING`, `IN_PROGRESS`, `DONE`.

* **Containerized Infrastructure**
  One-command setup for **Kafka** and **Zookeeper** using **Docker Compose**.

---

## 🧱 Architecture Overview

```
[ Task Tracker CLI ]  --->  [ Kafka Topic ]  --->  [ Task Consumer Service ]
       (Producer)              (Events)              (Persistence)
```

* The **CLI** publishes task events to Kafka
* The **Consumer** listens to events and updates persistent storage
* Services are loosely coupled and independently deployable

---

## 🛠️ Technologies

### Core

* **Java 17** – Core programming language
* **Spring Boot** – Microservices framework

### Messaging & Data

* **Apache Kafka** – Distributed event streaming platform
* **Spring Kafka** – Kafka integration for Spring
* **Jackson** – JSON serialization/deserialization

### DevOps

* **Docker** – Containerization platform
* **Docker Compose** – Multi-container orchestration

---

## 🚀 Getting Started

### Prerequisites

* Java 17+
* Maven
* Docker & Docker Compose

---

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/Sasikiran-31/task-tracker.git
cd Event-Driven-Task-Tracker
```

---

### 2️⃣ Infrastructure Setup (Kafka & Zookeeper)

Navigate to the consumer service directory:

```bash
cd task-consumer
```

Start Kafka and Zookeeper:

```bash
docker-compose up -d
```

---

### 3️⃣ Start the Consumer Service

From the same directory:

```bash
mvn spring-boot:run
```

The consumer will begin listening for task events from Kafka.

---

### 4️⃣ Start the Producer (CLI)

Open a **new terminal**, navigate to the CLI module, and run:

```bash
cd ../task-tracker-cli
mvn spring-boot:run
```

Follow the on-screen menu to **create, update, or delete tasks**.

---

## ⚠️ Important Notes

* **Docker Requirement**
  Ensure Docker Desktop is running before executing `docker-compose up`.

* **Local Data Storage**
  Tasks are stored in:

  ```
  task-tracker-cli/tasks.json
  ```

* **Automatic Backups**
  Before each write operation, backups are created in:

  ```
  task-tracker-cli/.backups/
  ```

* **Port Conflicts**
  Make sure the following ports are available:

  * `2181` – Zookeeper
  * `9092` – Kafka

---

## 📦 Dependencies

Each module maintains its own dependency configuration:

* `task-tracker-cli/pom.xml`
* `task-consumer/pom.xml`

Refer to these files for the full list of Maven dependencies and versions.

---

## 🎯 Learning Outcomes

This project demonstrates:

* Event-Driven Architecture (EDA)
* Kafka-based asynchronous communication
* Microservices separation of concerns
* Fault-tolerant file persistence
* Dockerized local development environments

---

