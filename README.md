# Dakiya — a courier & parcel-tracking API

Dakiya (डाकिया, *the postman*) is the reference application for RizTech Academy's
[**Java + Spring Web Development**](https://www.riztechacademy.com/learn/spring-web-development)
course. It is the backend a courier company runs on — parcels, hubs, a status lifecycle,
partners — built as a REST API to the standard the course teaches: clean layering, DTOs
and validation, JPA done well, real security, concurrency-safe status updates, and a
test suite.

This repository exists so a learner who gets stuck has correct, runnable code to compare
against. Read it alongside the lessons; type the code yourself rather than cloning it.

## What it does

- **Parcels** with a status lifecycle (`BOOKED → IN_TRANSIT → DELIVERED`, or `CANCELLED`),
  a unique tracking code, and a `@Version` field for concurrency-safe updates.
- **Hubs** a parcel passes through (a `@ManyToOne`, lazily fetched).
- A **REST API** (`/api/parcels`): book (201 + `Location`), track, list (filtered), with
  DTOs, Bean Validation, and consistent `ProblemDetail` errors.
- **Security** — Spring Security with BCrypt passwords and role-based access; `/api/**`
  requires authentication.
- **Layering** — thin controller → `ParcelService` (business logic) → repository
  (Spring Data JPA); a global `@RestControllerAdvice` for errors.

## Requirements

- **Java 17+** and the Maven wrapper (`./mvnw`, included — no Maven install needed).
- Spring Boot **3.4** (pinned in `pom.xml`). The course teaches Boot 3.x, which is what
  production and hiring use.

## Running it

```bash
git clone https://github.com/RizTech-Academy/dakiya.git
cd dakiya
./mvnw spring-boot:run          # starts on http://localhost:8080 with an in-memory H2 DB
```

The default HTTP Basic user is configured in `SecurityConfig` (`staff` / `admin`). Try:

```bash
# create a parcel (authenticated)
curl -u staff:staff-pass-123 -X POST http://localhost:8080/api/parcels \
  -H 'Content-Type: application/json' \
  -d '{"recipientName":"Asha","destinationCity":"Pune"}'

# list, track, and health
curl -u staff:staff-pass-123 http://localhost:8080/api/parcels
curl http://localhost:8080/actuator/health
```

An anonymous request to `/api/parcels` returns **401**; an invalid body returns **400**
with a `ProblemDetail`; a missing id returns **404**.

## Running the tests

```bash
./mvnw test
```

The suite covers the layers the course teaches: a Mockito **unit test** of the service,
a **`@WebMvcTest`** of the controller (routing, validation → 400, missing → 404, auth →
401), a **`@DataJpaTest`** of the repository queries, and a **`@SpringBootTest`** context
load — the testing pyramid, weighted to the risky paths.

## Project layout

```
src/main/java/com/riztech/dakiya/
  DakiyaApplication.java     the Spring Boot entry point
  Parcel.java, Hub.java      JPA entities (status lifecycle, @Version, LAZY relationship)
  ParcelRepository.java      Spring Data repository (derived queries + a fetch-join)
  ParcelDtos.java            request/response DTOs (records) with Bean Validation
  ParcelController.java      thin REST controller (@RequestMapping /api/parcels)
  ParcelService.java         business logic
  GlobalExceptionHandler.java  @RestControllerAdvice → ProblemDetail (404, 400)
  NotFoundException.java
  SecurityConfig.java        Spring Security: BCrypt, roles, /api/** authenticated
src/test/java/...            unit, @WebMvcTest, @DataJpaTest, @SpringBootTest
pom.xml                      Spring Boot 3.4, Java 17
```

## Going to production

For production: set `SPRING_PROFILES_ACTIVE=prod`, a real `DATABASE_URL` (PostgreSQL),
and secrets via environment variables; use `spring.jpa.hibernate.ddl-auto=validate` with
Flyway owning the schema; serve over HTTPS; and build an optimised image with
`./mvnw spring-boot:build-image`. See the course's Configuration & Deployment module.

---

Built for RizTech Academy. Licensed for learning use.
