# Collections & Exceptions – Spring Boot REST

Hands-on Java kata to practice REST endpoints, JPA mappings and exception handling using a hospital dataset (employees/doctors and patients). Includes POST, PUT, PATCH with Bean Validation and H2 in-memory DB initialized via `schema.sql` and `data.sql`.

## How to run
```bash
./mvnw spring-boot:run

App starts on http://localhost:8080.
H2 Console: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:hospitaldb).

Dataset

Loaded automatically from src/main/resources/schema.sql and data.sql:
	•	employees (employee_id, department, name, status)
	•	patients (patient_id, name, date_of_birth, admitted_by → FK to employees)

Endpoints

Employees (Doctors)
	•	GET  /employees — get all
	•	GET  /employees/{id} — get by id
	•	GET  /employees/status/{ON_CALL|ON|OFF} — filter by status
	•	GET  /employees/department/{department} — filter by department
	•	POST /employees — create doctor
Body:
# Collections & Exceptions – Spring Boot REST

Hands-on Java kata to practice REST endpoints, JPA mappings and exception handling using a hospital dataset (employees/doctors and patients). Includes **POST**, **PUT**, **PATCH** with Bean Validation and H2 in-memory DB initialized via `schema.sql` and `data.sql`.

## How to run
```bash
./mvnw spring-boot:run
```
App starts on **http://localhost:8080**  
H2 Console: **http://localhost:8080/h2-console** (JDBC URL: `jdbc:h2:mem:hospitaldb`)

## Dataset
Loaded automatically from `src/main/resources/schema.sql` and `data.sql`:
- **employees** (`employee_id`, `department`, `name`, `status`)
- **patients** (`patient_id`, `name`, `date_of_birth`, `admitted_by` → FK to employees)

## Endpoints

### Employees (Doctors)
- `GET  /employees` — get all
- `GET  /employees/{id}` — get by id
- `GET  /employees/status/{ON_CALL|ON|OFF}` — filter by status
- `GET  /employees/department/{department}` — filter by department
- `POST /employees` — **create** doctor  
  Body:
  ```json
  {
    "id": 999999,
    "department": "cardiology",
    "name": "New Doc",
    "status": "ON"
  }
  ```
- `PATCH /employees/{id}/status` — **change status**  
  Body:
  ```json
  { "status": "OFF" }
  ```
- `PATCH /employees/{id}/department` — **update department**  
  Body:
  ```json
  { "department": "immunology" }
  ```

### Patients
- `GET  /patients` — get all
- `GET  /patients/{id}` — get by id
- `GET  /patients/dob?from=YYYY-MM-DD&to=YYYY-MM-DD` — filter by DOB range
- `GET  /patients/by-doctor-department/{department}` — by admitting doctor’s department
- `GET  /patients/by-doctor-status/{ON_CALL|ON|OFF}` — by admitting doctor’s status
- `POST /patients` — **create** patient  
  Body:
  ```json
  {
    "id": 999,
    "name": "Test Patient",
    "dateOfBirth": "1990-05-05",
    "admittedById": 999999
  }
  ```
- `PUT /patients/{id}` — **update** full patient  
  Body:
  ```json
  {
    "name": "Test Patient Updated",
    "dateOfBirth": "1990-06-06",
    "admittedById": 999999
  }
  ```

> **Validation:** DTOs enforce constraints via Bean Validation (JSR 380). Invalid input → **400 Bad Request**.  
> **Conflicts:** duplicated id → **409 Conflict**. Not found → **404 Not Found**.

## Quick test with curl
```bash
# create doctor
curl -X POST http://localhost:8080/employees \
  -H "Content-Type: application/json" \
  -d '{"id":999999,"department":"cardiology","name":"New Doc","status":"ON"}'

# change status
curl -X PATCH http://localhost:8080/employees/999999/status \
  -H "Content-Type: application/json" \
  -d '{"status":"OFF"}'

# change department
curl -X PATCH http://localhost:8080/employees/999999/department \
  -H "Content-Type: application/json" \
  -d '{"department":"immunology"}'

# create patient
curl -X POST http://localhost:8080/patients \
  -H "Content-Type: application/json" \
  -d '{"id":999,"name":"Test Patient","dateOfBirth":"1990-05-05","admittedById":999999}'

# update patient (full)
curl -X PUT http://localhost:8080/patients/999 \
  -H "Content-Type: application/json" \
  -d '{"name":"Test Patient Updated","dateOfBirth":"1990-06-06","admittedById":999999}'
```

## IntelliJ HTTP Client (optional)
Create a `requests.http` at the project root and run each block with ▶️ or Cmd/Ctrl+Enter:
```http
### Add NEW DOCTOR (POST)
POST http://localhost:8080/employees
Content-Type: application/json

{
  "id": 999999,
  "department": "cardiology",
  "name": "New Doc",
  "status": "ON"
}

### Change DOCTOR STATUS (PATCH)
PATCH http://localhost:8080/employees/999999/status
Content-Type: application/json

{
  "status": "OFF"
}

### Update DOCTOR DEPARTMENT (PATCH)
PATCH http://localhost:8080/employees/999999/department
Content-Type: application/json

{
  "department": "immunology"
}

### Add NEW PATIENT (POST)
POST http://localhost:8080/patients
Content-Type: application/json

{
  "id": 999,
  "name": "Test Patient",
  "dateOfBirth": "1990-05-05",
  "admittedById": 999999
}

### Update PATIENT (PUT)
PUT http://localhost:8080/patients/999
Content-Type: application/json

{
  "name": "Test Patient Updated",
  "dateOfBirth": "1990-06-06",
  "admittedById": 999999
}

### Verify DOCTOR after department change (GET)
GET http://localhost:8080/employees/999999

### Verify by department (GET)
GET http://localhost:8080/employees/department/immunology

### Verify PATIENT after PUT (GET)
GET http://localhost:8080/patients/999
```

## Q&amp;A

**Did you use the same type of route to update patient information as to update an employee's department?**  
No. I used **PUT** to update the entire patient resource and **PATCH** to update a single field (the employee’s department).

**Why this strategy?**  
Patients typically require a full, consistent snapshot (name, DOB, admitting doctor) — perfect for **PUT** (complete replacement).  
The department change is a narrow, partial update — ideal for **PATCH**.

**Advantages and disadvantages of these choices**  
- **PUT (patient):**  
  - ✅ Clear contract (client sends the full resource)  
  - ✅ Easy to reason about state consistency  
  - ❌ Heavier payloads; client must supply all fields
- **PATCH (employee department/status):**  
  - ✅ Minimal payloads, focused updates  
  - ✅ Reduces accidental field overwrites  
  - ❌ Slightly more complex validation/semantics

**Cost–benefit between PUT and PATCH**  
- **PUT:** higher payload cost but simpler and idempotent for full updates.  
- **PATCH:** efficient for small changes; slightly more complex semantics.