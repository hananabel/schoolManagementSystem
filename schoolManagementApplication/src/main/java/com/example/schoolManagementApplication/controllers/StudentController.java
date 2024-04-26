package com.example.schoolManagementApplication.controllers;

import com.example.schoolManagementApplication.models.Student;
import com.example.schoolManagementApplication.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController { //Controller layer for Student



    private final StudentService studentService;


    @Autowired
    // Constructor to initialize StudentController with StudentService
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    // Endpoint to add a new student
    @PostMapping("/{parentId}/{teacherId}")
    public ResponseEntity<String> addStudent(@PathVariable("parentId") Long parentId, @PathVariable("teacherId") Long teacherId, @RequestBody Student student) {
        if (studentService.addStudent(parentId, teacherId, student)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Student added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Student already exists");
        }

    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    // Endpoint to update a student
    @PutMapping(path="{studentId}")
    public ResponseEntity<String> updateStudent(@PathVariable("studentId") Long studentId,
                                                @RequestParam(required = false) String name,
                                                @RequestParam(required = false) String email,
                                                @RequestParam(required = false) Long teacherId) {
        Integer updateSuccess = studentService.updateStudent(studentId, name, email, teacherId);
        // Handle different update scenarios and return appropriate ResponseEntity
        if (updateSuccess == 1) {
            return ResponseEntity.ok("Student updated successfully");
        }
            if (updateSuccess == -1) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already taken");
            }

            // Check if teacher with the given ID is not found
            if (updateSuccess == -2) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Teacher with ID " + teacherId + " not found");
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");


    }

    // Endpoint to delete a student
    @DeleteMapping("/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable("studentId") Long studentId) {
        if (studentService.deleteStudent(studentId)) {
            return ResponseEntity.ok("Student deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }
    }
}
