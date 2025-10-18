package com.ramiatowers.collections_exceptions_kata.repository;

import com.ramiatowers.collections_exceptions_kata.model.EmployeeStatus;
import com.ramiatowers.collections_exceptions_kata.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    List<Patient> findByDateOfBirthBetween(LocalDate from, LocalDate to);
    List<Patient> findByAdmittedBy_Department(String department);
    List<Patient> findByAdmittedBy_Status(EmployeeStatus status);
}