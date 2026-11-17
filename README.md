# Vacation Management System

A multi-tenant web application in which companies manage employee leave through approval chains
they configure themselves.

## Repository layout

| Path | Content |
| --- | --- |
| `build-logic/` | Gradle convention plugins `vms.java-library` and `vms.spring-service` |
| `libs/` | Shared libraries: `common-web`, `common-security`, `common-events`, `common-messaging`, `common-test` |
| `deploy/compose/` | Local infrastructure (Docker Compose) |
| `scripts/` | `dev-up.sh`, `dev-down.sh` |
| `services/` | `api-gateway` (8080), `organization-service` (8081), `leave-service` (8082), `approval-service` (8083), `notification-service` (8084) |

## Build

The only prerequisite is JDK 21. The Gradle wrapper downloads everything else.

```bash
./gradlew build                 # compile and run unit tests
./gradlew integrationTest       # run integration tests
./gradlew spotlessCheck         # verify formatting
./gradlew spotlessApply         # fix formatting
```

## Local infrastructure

PostgreSQL, Kafka, Keycloak, MinIO, Mailpit and Grafana run in Docker Compose (Docker with about 8 GB of free memory).

```bash
scripts/dev-up.sh               # start everything and wait until it is healthy
scripts/dev-down.sh             # stop; add --volumes to delete the data
```

| Component | Address |
| --- | --- |
| PostgreSQL | `localhost:5432` |
| Kafka | `localhost:9092` |
| Keycloak admin console | http://localhost:8090 |
| MinIO API / console | http://localhost:9000 / http://localhost:9001 |
| Mailpit (SMTP / inbox) | `localhost:1025` / http://localhost:8025 |
| Grafana | http://localhost:3000 |

Credentials are the local defaults in `deploy/compose/.env.example`.

## Run a service

```bash
./gradlew :services:organization-service:bootRun
curl localhost:9081/actuator/health/readiness
```

Each service listens on the port listed above (override with `SERVER_PORT`) and exposes Actuator on a
separate management port, 9080–9084 in the same order (override with `MANAGEMENT_PORT`).
