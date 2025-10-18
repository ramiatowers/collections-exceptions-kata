package com.ramiatowers.collections_exceptions_kata.controller;

import com.ramiatowers.collections_exceptions_kata.model.EmployeeStatus;
import com.ramiatowers.collections_exceptions_kata.model.Patient;
import com.ramiatowers.collections_exceptions_kata.repository.PatientRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientRepository patients;

    public PatientController(PatientRepository patients) {
        this.patients = patients;
    }

    // Get all patients
    @GetMapping
    public List<Patient> getAll() {
        return patients.findAll();
    }

    // Get patient by ID
    @GetMapping("/{id}")
    public Patient getById(@PathVariable Integer id) {
        return patients.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));
    }

    // Get patients by date of birth range: /patients/dob?from=YYYY-MM-DD&to=YYYY-MM-DD
    @GetMapping("/dob")
    public List<Patient> getByDobRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        if (from.isAfter(to)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "`from` must be before or equal to `to`");
        }
        return patients.findByDateOfBirthBetween(from, to);
    }

    // Get patients by admitting doctor's department
    @GetMapping("/by-doctor-department/{department}")
    public List<Patient> getByDoctorDepartment(@PathVariable String department) {
        return patients.findByAdmittedBy_Department(department);
    }

    // Get all patients with a doctor whose status is OFF (generic by status)
    @GetMapping("/by-doctor-status/{status}")
    public List<Patient> getByDoctorStatus(@PathVariable String status) {
        EmployeeStatus s;
        try {
            s = EmployeeStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status. Use ON_CALL, ON, or OFF");
        }
        return patients.findByAdmittedBy_Status(s);
    }

    // Alias to match the exact lab wording
    @GetMapping("/with-doctor-off")
    public List<Patient> getPatientsWithDoctorOff() {
        return patients.findByAdmittedBy_Status(EmployeeStatus.OFF);
    }
}
