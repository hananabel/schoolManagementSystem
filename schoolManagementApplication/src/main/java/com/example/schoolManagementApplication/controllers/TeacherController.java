package com.example.schoolManagementApplication.controllers;

import com.example.schoolManagementApplication.models.Teacher;
import com.example.schoolManagementApplication.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/teacher")
public class TeacherController { //Controller Layer of Teacher

    private final TeacherService teacherService;

    @Autowired
    // Constructor to initialize TeacherController with TeacherService
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    public ResponseEntity<String> addTeacher(@RequestBody Teacher teacher) { //calls the addTeacher method of the Service Layer
        if (teacherService.addTeacher(teacher)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Teacher added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add Teacher");
        }
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() { //calls the getAllTeachers method of the Service Layer
        List<Teacher> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    @PutMapping(path="{teacherId}")
    public ResponseEntity<String> updateTeacher(@PathVariable("teacherId") Long teacherId,
                                                @RequestParam(required = false) String name,
                                                @RequestParam(required = false) String email) { //calls the updateTeacher method of the Service Layer
        Integer updateSuccess = teacherService.updateTeacher(teacherId, name, email);
        if (updateSuccess == 1) {
            return ResponseEntity.ok("Teacher updated successfully");
        } else {
            if(updateSuccess == -1){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email taken");
            }
            if(updateSuccess == 0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Teacher not found");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update Teacher");
        }
    }

    @DeleteMapping(path = "{teacherId}")
    public ResponseEntity<String> deleteTeacher(@PathVariable("teacherId") Long teacherId) { //calls the deleteTeacher method of the Service Layer
        if (teacherService.deleteTeacher(teacherId)) {
            return ResponseEntity.ok("Teacher deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Teacher not found");
        }
    }
}
