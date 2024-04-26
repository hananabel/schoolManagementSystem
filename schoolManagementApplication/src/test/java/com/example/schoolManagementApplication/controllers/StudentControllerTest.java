package com.example.schoolManagementApplication.controllers;

import com.example.schoolManagementApplication.models.Student;
import com.example.schoolManagementApplication.services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddStudent_Success() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Hanan");
        student.setEmail("hanan@example.com");
        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.CREATED).body("Student added successfully");

        when(studentService.addStudent(anyLong(), anyLong(), any(Student.class))).thenReturn(true);

        ResponseEntity<String> actualResponse = studentController.addStudent(1L, 2L, student);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
        verify(studentService, times(1)).addStudent(anyLong(), anyLong(), any(Student.class));
    }

    @Test
    void testAddStudent_Conflict() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Hanan Abel");
        student.setEmail("hanan@example.com");
        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.CONFLICT).body("Student already exists");

        when(studentService.addStudent(anyLong(), anyLong(), any(Student.class))).thenReturn(false);

        ResponseEntity<String> actualResponse = studentController.addStudent(1L, 2L, student);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
        verify(studentService, times(1)).addStudent(anyLong(), anyLong(), any(Student.class));
    }

    @Test
    void testGetAllStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Example1", "example1@gmail.com", LocalDate.of(2003, 1, 8)));
        students.add(new Student("Example2", "example2@gmail.com", LocalDate.of(2003, 1, 8)));
        ResponseEntity<List<Student>> expectedResponse = ResponseEntity.ok(students);

        when(studentService.getAllStudents()).thenReturn(students);

        ResponseEntity<List<Student>> actualResponse = studentController.getAllStudents();

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
        verify(studentService, times(1)).getAllStudents();
    }


    @Test
    void updateStudent_Success() {
        Long studentId = 1L;
        String studentName = "Updated Name";
        String studentEmail = "updated@example.com";
        Long teacherId = 2L;

        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Student updated successfully");

        when(studentService.updateStudent(studentId, studentName, studentEmail, teacherId)).thenReturn(1);

        ResponseEntity<String> actualResponse = studentController.updateStudent(studentId, studentName, studentEmail, teacherId);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
        verify(studentService, times(1)).updateStudent(studentId, studentName, studentEmail, teacherId);

    }

    @Test
    void updateStudent_EmailAlreadyTaken() {
        Long studentId = 1L;
        String studentName = "Updated Name";
        String studentEmail = "updated@example.com";
        Long teacherId = 2L;

        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.CONFLICT).body("Email already taken");

        when(studentService.updateStudent(studentId, studentName, studentEmail, teacherId)).thenReturn(-1);

        ResponseEntity<String> actualResponse = studentController.updateStudent(studentId, studentName, studentEmail, teacherId);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
        verify(studentService, times(1)).updateStudent(studentId, studentName, studentEmail, teacherId);

    }

    @Test
    void updateStudent_TeacherNotFound() {
        Long studentId = 1L;
        String studentName = "Updated Name";
        String studentEmail = "updated@example.com";
        Long teacherId = 2L;

        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Teacher with ID " + teacherId + " not found");

        when(studentService.updateStudent(studentId, studentName, studentEmail, teacherId)).thenReturn(-2);

        ResponseEntity<String> actualResponse = studentController.updateStudent(studentId, studentName, studentEmail, teacherId);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
        verify(studentService, times(1)).updateStudent(studentId, studentName, studentEmail, teacherId);

    }

    @Test
    void updateStudent_StudentNotFound() {
        Long studentId = 1L;
        String studentName = "Updated Name";
        String studentEmail = "updated@example.com";
        Long teacherId = 2L;

        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");

        when(studentService.updateStudent(studentId, studentName, studentEmail, teacherId)).thenReturn(0);

        ResponseEntity<String> actualResponse = studentController.updateStudent(studentId, studentName, studentEmail, teacherId);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
        verify(studentService, times(1)).updateStudent(studentId, studentName, studentEmail, teacherId);

    }

    @Test
    void testDeleteStudent_Success() {
        Long studentId = 1L;
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Student deleted successfully");

        when(studentService.deleteStudent(studentId)).thenReturn(true);

        ResponseEntity<String> actualResponse = studentController.deleteStudent(studentId);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
        verify(studentService, times(1)).deleteStudent(studentId);
    }

    @Test
    void testDeleteStudent_Failure() {
        Long studentId = 1L;
        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");

        when(studentService.deleteStudent(studentId)).thenReturn(false);

        ResponseEntity<String> actualResponse = studentController.deleteStudent(studentId);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
        verify(studentService, times(1)).deleteStudent(studentId);
    }
}