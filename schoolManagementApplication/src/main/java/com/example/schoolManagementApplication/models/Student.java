package com.example.schoolManagementApplication.models;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table
public class Student { //Entity Class

    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private LocalDate dob;
    // Defining the relationship with Parent Entity
    @ManyToOne
    @JoinColumn(name = "parentId",referencedColumnName = "id")
    private Parent parent;
    //private Long teacherId;

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    // Defining the relationship with Teacher Entity
    @ManyToOne
    @JoinColumn(name = "teacherId",referencedColumnName = "id")
    private Teacher teacher;

    public Teacher getTeacher() { return teacher;}

    public void setTeacher(Teacher teacher) {this.teacher = teacher;}

    public void setId(Long id) {
        this.id = id;
    }

    public Student() {
    }

    public Student(String name, String email, LocalDate dob) {
        this.name = name;
        this.email = email;
        this.dob = dob;

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

    // Calculating Age from dob
    public Integer getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }
}
