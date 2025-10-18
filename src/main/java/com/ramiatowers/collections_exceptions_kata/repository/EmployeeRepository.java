package com.ramiatowers.collections_exceptions_kata.repository;

import com.ramiatowers.collections_exceptions_kata.model.Employee;
import com.ramiatowers.collections_exceptions_kata.model.EmployeeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByStatus(EmployeeStatus status);
    List<Employee> findByDepartment(String department);
}
