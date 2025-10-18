package com.ramiatowers.collections_exceptions_kata.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @Column(name = "patient_id")
    private Integer id;

    private String name;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "admitted_by", nullable = false)
    private Employee admittedBy;

    public Patient() { }

    public Patient(Integer id, String name, LocalDate dateOfBirth, Employee admittedBy) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.admittedBy = admittedBy;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public Employee getAdmittedBy() { return admittedBy; }

    public void setId(Integer id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setAdmittedBy(Employee admittedBy) { this.admittedBy = admittedBy; }
}
