# Task Management API

> ⚠️ **Disclaimer:** This is a showcase / portfolio project, not a production-ready system. It may evolve toward production use in the future, but as of now it hasn't been hardened, audited, or tested for that purpose. Use it at your own risk — no responsibility is taken for bugs, security issues, or data loss resulting from its use.

RESTful task management API built with Java and Spring Boot. Backend showcase project focused on authentication, authorization, REST API design, persistence, validation, testing, and clean separation of responsibilities.

## Features
- User registration and authentication (JWT access + refresh tokens)
- Refresh token rotation with reuse prevention
- Refresh tokens stored as SHA-256 hashes (never in plaintext)
- Logout with refresh token revocation
- Role based authorization
- Project, task, and comment management
- Task status and priority management, task assignment
- Pagination and sorting
- Request validation and global exception handling
- PostgreSQL persistence
- Integration tests with Testcontainers
- Docker support

## Tech Stack

Java 25 · Spring Boot · Spring Security · Spring Data JPA · Hibernate · PostgreSQL · Gradle · JUnit 5 · MockMvc · Testcontainers · Docker

## Architecture

Layered architecture with domain based package organization (controller → service → repository), separating entities (persistent domain objects) from DTOs (API contract).

```text
src/main/java/dev/iamforyy/taskmanagmentapi/
├── auth/       AuthController, AuthService, JwtService
├── user/       User, UserController, UserService, UserRepository
├── project/    Project, ProjectController, ProjectService, ProjectRepository
├── task/       Task, TaskController, TaskService, TaskRepository
└── comment/    Comment, CommentController, CommentService, CommentRepository
```

## Authentication

Two-token model:

- **Access token** — short lived, authenticates API requests
- **Refresh token** — long lived, used to obtain a new access token; stored as a SHA-256 hash

**Refresh flow (rotation):** validate existing token → revoke it → issue new access + refresh tokens → store new hash → return both to the client. This prevents a consumed refresh token from being reused.

## API Endpoints

### Authentication

| Method | Endpoint         | Description                       | Auth   |
|--------|------------------|-----------------------------------|--------|
| POST   | `/auth/register` | Register a new user               | Public |
| POST   | `/auth/login`    | Authenticate a user               | Public |
| POST   | `/auth/refresh`  | Refresh access and refresh tokens | Public |
| POST   | `/auth/logout`   | Revoke a refresh token            | Public |

### Users

| Method | Endpoint        | Description                          | Auth     |
|--------|-----------------|--------------------------------------|----------|
| GET    | `/api/users/me` | Get the currently authenticated user | Required |

### Projects

| Method | Endpoint                    | Description         | Auth     |
|--------|-----------------------------|---------------------|----------|
| GET    | `/api/projects`             | Get projects        | Required |
| GET    | `/api/projects/{projectId}` | Get a project by ID | Required |
| POST   | `/api/projects`             | Create a project    | Required |
| PATCH  | `/api/projects/{projectId}` | Update a project    | Required |
| DELETE | `/api/projects/{projectId}` | Delete a project    | Required |

### Tasks

| Method | Endpoint                     | Description        | Auth     |
|--------|------------------------------|--------------------|----------|
| GET    | `/api/tasks`                 | Get tasks          | Required |
| GET    | `/api/tasks/{taskId}`        | Get a task by ID   | Required |
| POST   | `/api/tasks`                 | Create a task      | Required |
| PATCH  | `/api/tasks/{taskId}`        | Update a task      | Required |
| PATCH  | `/api/tasks/{taskId}/status` | Update task status | Required |
| DELETE | `/api/tasks/{taskId}`        | Delete a task      | Required |

### Comments

| Method | Endpoint                       | Description       | Auth     |
|--------|--------------------------------|-------------------|----------|
| GET    | `/api/tasks/{taskId}/comments` | Get task comments | Required |
| POST   | `/api/tasks/{taskId}/comments` | Create a comment  | Required |
| PATCH  | `/api/comments/{commentId}`    | Update a comment  | Required |
| DELETE | `/api/comments/{commentId}`    | Delete a comment  | Required |

## Example Flow

```http
POST /auth/register
Content-Type: application/json

{ "username": "john", "email": "john@example.com", "password": "password123" }
```

```http
POST /auth/login
Content-Type: application/json

{ "email": "john@example.com", "password": "password123" }
```

Response:
```json
{ "accessToken": "eyJ...", "refreshToken": "..." }
```

Authenticated request:
```http
GET /api/users/me
Authorization: Bearer eyJ...
```

Refresh (returns a new access + refresh token pair; previous refresh token is revoked):
```http
POST /auth/refresh
Content-Type: application/json

{ "refreshToken": "..." }
```

Logout (revokes the refresh token):
```http
POST /auth/logout
Content-Type: application/json

{ "refreshToken": "..." }
```

## Pagination

Collection endpoints support pagination and sorting via Spring Data's `Pageable`:

```http
GET /api/projects?page=0&size=20&sort=name,asc
```

## Validation & Error Handling

Request DTOs use Jakarta Bean Validation. Invalid requests, auth errors, missing resources, and invalid/expired tokens are handled by a global exception handler with a consistent error shape:

```json
{
  "status": 404,
  "code": "RESOURCE_NOT_FOUND",
  "message": "Task not found",
  "timestamp": "2026-08-25T12:00:00Z"
}
```

## Database

```text
User
 ├── Project
 │    └── Task
 │         └── Comment
 └── RefreshToken
```

PostgreSQL, mapped via JPA. Passwords are hashed with Spring Security's `PasswordEncoder`; refresh tokens are stored as SHA-256 hashes. No sensitive data is ever stored in plaintext.

## Testing

Integration tests with JUnit 5, Spring Boot Test, MockMvc, and Testcontainers, covering auth requirements, token refresh/logout, registration, validation, response status/content, and persistence.

```bash
./gradlew test        # gradlew.bat test on Windows
```

## Running Locally

**Requirements:** Java 25, Docker, PostgreSQL, Git

```bash
git clone <repository-url>
cd task-management-api
```

Set the required environment variables (e.g. via `.env` — never commit real secrets):

```text
POSTGRES_DB=tmapdb
POSTGRES_USER=tmap
POSTGRES_PASSWORD=your_postgres_password

JWT_SECRET=your_jwt_secret
JWT_EXPIRATION=300000
```

Run:
```bash
./gradlew bootRun     # gradlew.bat bootRun on Windows
```

API available at `http://localhost:8080`.

## Docker

```bash
./gradlew build
docker build -t task-management-api .
docker run -p 8080:8080 task-management-api
```

Or run the full stack (API + PostgreSQL) with Docker Compose:
```bash
docker compose up
```

## Project Goals

Demonstrate production-oriented backend development: authentication/authorization with Spring Security, Spring Data JPA + Hibernate, relational modeling, layered architecture, domain/DTO separation, validation, centralized error handling, integration testing, and containerization.

## Future Improvements

- [X] OpenAPI / Swagger documentation
- [ ] Rate limiting
- [ ] Redis caching
- [ ] Better refresh token reuse detection
- [ ] Email verification & password reset
- [ ] Account management
- [ ] Advanced task filtering
- [ ] Project membership and permissions
- [ ] Audit logging
- [ ] CI/CD improvements & production deployment

## License

MIT License.