# app-crud: Spring Boot Customer CRUD API

[![CI](https://github.com/shane24sweeney/app-crud/actions/workflows/ci.yml/badge.svg)](https://github.com/shane24sweeney/app-crud/actions/workflows/ci.yml)

A REST API for managing banking customer records, built with **Spring Boot 3.2** and **Java 17**. It uses a controller → service → repository design with constructor injection, Spring Data JPA over an H2 database, and MockMvc tests running in GitHub Actions.

## Tech stack

Java 17 · Spring Boot 3.2 (Web, Data JPA) · Hibernate · H2 · JUnit 5 · MockMvc · Maven · GitHub Actions

## Data model

`Customer` maps to the `customers` table:

| Field | Column | Constraints |
|---|---|---|
| `customerId` | `customer_id` | primary key, auto-generated |
| `firstName` | `first_name` | not null |
| `lastName` | `last_name` | not null |
| `email` | `email` | not null, unique |
| `phoneNumber` | `phone_number` | not null |

## API

| Method | Endpoint | Description | Success | Not found |
|---|---|---|---|---|
| GET | `/api/customers` | List all customers | 200 | |
| GET | `/api/customers/{id}` | Get one customer | 200 | 404 |
| POST | `/api/customers` | Create a customer | 201 | |
| PUT | `/api/customers/{id}` | Update a customer | 200 | 404 |
| DELETE | `/api/customers/{id}` | Delete a customer | 200 | 404 |

## Run locally

Requires Java 17+.

```bash
./mvnw spring-boot:run
```

The API starts on `http://localhost:8080`. The H2 console is at `/h2-console` (JDBC URL `jdbc:h2:mem:test`).

```bash
# create
curl -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{"firstName":"John","lastName":"Doe","email":"john.doe@example.com","phoneNumber":"1234567890"}'

# list / get
curl http://localhost:8080/api/customers
curl http://localhost:8080/api/customers/1

# update
curl -X PUT http://localhost:8080/api/customers/1 \
  -H "Content-Type: application/json" \
  -d '{"firstName":"John","lastName":"Doe","email":"john.doe@example.com","phoneNumber":"5555555555"}'

# delete
curl -X DELETE http://localhost:8080/api/customers/1
```

## Tests

```bash
./mvnw test
```

- `CustomerControllerTests`: create, read, update and delete through MockMvc, plus 404 handling for unknown ids.
- `H2DatabaseStructureTest`: prints the generated `customers` table structure to confirm the JPA mapping.
