Here’s a **clean and professional** version of your README with **icons/emojis** used for better visual structure, no excessive formatting (like `**bold**` unless needed), and all unnecessary markup removed:

---

```markdown
# 📋 Task Management API

Task Management API is a Spring Boot application for managing tasks with role-based access, JWT authentication, and real-time notifications using Server-Sent Events (SSE). Inspired by [AbhisekXD's Task-Management](https://github.com/AbhisekXD/Task-Management), it follows a clean and modular backend structure with PostgreSQL integration.

## 🚀 Features

- 🔐 JWT Authentication with role-based access
  - First user becomes `ADMIN`, others are `USER`
- ✅ Task management (create, assign, update status)
- 📡 Real-time notifications via SSE
- 📬 Notification history and unread count
- 🖥️ Static frontend page for live notification display

## 🛠️ Tech Stack

- Java 17, Spring Boot 3.4.4
- Spring Security, Spring Web, Spring Data JPA
- PostgreSQL
- JWT (jjwt, java-jwt)
- ModelMapper
- OpenAPI/Swagger for documentation

## 📁 Project Structure

```
src/
├── controller/         # REST Controllers
├── model/              # DTOs and Entities
├── service/            # Business logic
├── security/           # JWT and Auth config
└── resources/
    ├── static/         # HTML for SSE notifications
    └── application.properties
```

## ⚙️ Setup & Installation

### Prerequisites

- Java 17+
- Maven
- PostgreSQL

### Clone and Configure

```bash
git clone https://github.com/AbhisekXD/Task-Management.git
cd Task-Management
```

Update `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/task_management
spring.datasource.username=postgres
spring.datasource.password=postgres
jwt_secret=your_secret_key
jwt.expiration=36000000000
```

### Run the App

```bash
mvn clean install
mvn spring-boot:run
```

## 🔑 Authentication Endpoints

| Method | Endpoint       | Description                          |
|--------|----------------|--------------------------------------|
| POST   | `/auth/signup` | Register (first user becomes ADMIN)  |
| POST   | `/auth/login`  | Authenticate and get JWT token       |

## 📌 Task Endpoints

| Method | Endpoint                           | Role   | Description                      |
|--------|------------------------------------|--------|----------------------------------|
| POST   | `/tasks`                           | ADMIN  | Create a task                    |
| PUT    | `/tasks/{id}/assign/{userId}`      | ADMIN  | Assign task to user              |
| GET    | `/tasks`                           | USER   | View assigned tasks              |
| PUT    | `/tasks/{id}/status`               | USER   | Update task status               |

## 🔔 Notification Endpoints

| Method | Endpoint                                | Description                         |
|--------|-----------------------------------------|-------------------------------------|
| GET    | `/notifications`                        | Get unread notifications            |
| GET    | `/notifications/all`                    | Get all notifications               |
| PUT    | `/notifications/{id}/read`              | Mark notification as read           |
| GET    | `/api/notifications/stream/{userId}`    | SSE stream for real-time updates    |

## 🧠 Task Status Flow

```
PENDING → IN_PROGRESS → COMPLETED
```

## 🖼️ UI Notification Page

HTML page located in `src/main/resources/static`:
- Connects to SSE endpoint
- Displays incoming notifications in real-time
- Plays a sound and triggers vibration on each notification

## 🗃️ Database Schema

- Users (id, username, password, role)
- Tasks (id, title, description, status, assigned_to)
- Notifications (id, message, user_id, is_read, timestamp)

## 🔍 API Docs

Once the app is running, open:
```
http://localhost:8080/swagger-ui.html
```

## 📦 Postman Collection

Includes:
- Signup/Login
- Task Create/Assign
- Notification testing

(You can export from your local Postman or refer to the example request bodies in Swagger UI.)

## 🤝 Contributing

PRs and ideas are welcome. Fork the repo, raise issues, and help improve the project!

## 📄 License

This project is licensed under the MIT License.

---

🔗 Reference: Based on and inspired by [AbhisekXD/Task-Management](https://github.com/AbhisekXD/Task-Management)
```

---

Let me know if you want a version with badges (build status, license, etc.) or if you're planning to publish this on GitHub and need markdown tweaks for better display.
