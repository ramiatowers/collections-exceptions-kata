package com.ramiatowers.collections_exceptions_kata.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "employee_id")
    private Integer id;

    private String department;

    private String name;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    public Employee() { }

    public Employee(Integer id, String department, String name, EmployeeStatus status) {
        this.id = id;
        this.department = department;
        this.name = name;
        this.status = status;
    }

    public Integer getId() { return id; }
    public String getDepartment() { return department; }
    public String getName() { return name; }
    public EmployeeStatus getStatus() { return status; }

    public void setId(Integer id) { this.id = id; }
    public void setDepartment(String department) { this.department = department; }
    public void setName(String name) { this.name = name; }
    public void setStatus(EmployeeStatus status) { this.status = status; }
}
