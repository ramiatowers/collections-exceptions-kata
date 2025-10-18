package com.ramiatowers.collections_exceptions_kata.controller;

import com.ramiatowers.collections_exceptions_kata.model.Employee;
import com.ramiatowers.collections_exceptions_kata.model.EmployeeStatus;
import com.ramiatowers.collections_exceptions_kata.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository employees;

    public EmployeeController(EmployeeRepository employees) {
        this.employees = employees;
    }

    // Get all doctors
    @GetMapping
    public List<Employee> getAll() {
        return employees.findAll();
    }

    // Get doctor by ID
    @GetMapping("/{id}")
    public Employee getById(@PathVariable Integer id) {
        return employees.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
    }

    // Get doctors by status
    @GetMapping("/status/{status}")
    public List<Employee> getByStatus(@PathVariable String status) {
        EmployeeStatus s;
        try {
            s = EmployeeStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status. Use ON_CALL, ON, or OFF");
        }
        return employees.findByStatus(s);
    }

    // Get doctors by department
    @GetMapping("/department/{department}")
    public List<Employee> getByDepartment(@PathVariable String department) {
        return employees.findByDepartment(department);
    }
}