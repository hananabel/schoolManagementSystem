package com.example.schoolManagementApplication.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table
public class Parent {// Entity Class

    @Id
    @SequenceGenerator(
            name = "parent_sequemce",
            sequenceName = "parent_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "parent_sequence"
    )
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private LocalDate dob;

    public Parent() { // Default Constructor
    }

    public Parent(String name, String email, LocalDate dob) { // Parameterized Constructor
        this.name = name;
        this.email = email;
        this.dob = dob;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }


    public Long getId() {
        return id;
    }

    // Caclulating Age from dob
    public Integer getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }
}
