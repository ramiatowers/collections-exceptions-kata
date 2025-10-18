DROP TABLE IF EXISTS patients;
DROP TABLE IF EXISTS employees;

CREATE TABLE employees (
    employee_id INT PRIMARY KEY,
    department VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE patients (
    patient_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    admitted_by INT NOT NULL,
    CONSTRAINT fk_patients_employee
        FOREIGN KEY (admitted_by)
        REFERENCES employees(employee_id)
);