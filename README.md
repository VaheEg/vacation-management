# Vacation Management System

A multi-tenant web application in which companies manage employee leave through approval chains
they configure themselves.

## Repository layout

| Path | Content |
| --- | --- |
| `build-logic/` | Gradle convention plugins `vms.java-library` and `vms.spring-service` |
| `libs/` | Shared libraries: `common-web`, `common-security`, `common-events`, `common-messaging`, `common-test` |
| `services/` | `api-gateway` (8080), `organization-service` (8081), `leave-service` (8082), `approval-service` (8083), `notification-service` (8084) |

## Build

The only prerequisite is JDK 21. The Gradle wrapper downloads everything else.

```bash
./gradlew build                 # compile and run unit tests
./gradlew integrationTest       # run integration tests
./gradlew spotlessCheck         # verify formatting
./gradlew spotlessApply         # fix formatting
```

## Run a service

```bash
./gradlew :services:organization-service:bootRun
curl localhost:8081/actuator/health
```

Each service listens on the port listed above; override it with `SERVER_PORT`.
