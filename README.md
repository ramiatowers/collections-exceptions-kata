# Collections & Exceptions – Spring Boot REST
Hands-on Java kata to practice REST endpoints, JPA relationships and exception handling using a hospital dataset.

## Endpoints
- GET /employees
- GET /employees/{id}
- GET /employees/status/{ON_CALL|ON|OFF}
- GET /employees/department/{dept}
- GET /patients
- GET /patients/{id}
- GET /patients/dob?from=YYYY-MM-DD&to=YYYY-MM-DD
- GET /patients/by-doctor-department/{dept}
- GET /patients/by-doctor-status/{ON_CALL|ON|OFF}
- (optional) GET /patients/with-doctor-off

## Run
```bash
./mvnw spring-boot:run