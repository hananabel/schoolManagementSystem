package com.example.schoolManagementApplication.controllers;

import com.example.schoolManagementApplication.models.Parent;
import com.example.schoolManagementApplication.models.Teacher;
import com.example.schoolManagementApplication.services.ParentService;
import com.example.schoolManagementApplication.services.StudentService;
import com.example.schoolManagementApplication.services.TeacherService;
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
import static org.mockito.Mockito.*;


class TeacherControllerTest {

    @Mock
    private TeacherService teacherService;

    @InjectMocks
    private TeacherController teacherController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTeacher_Success() {
        Teacher teacher = new Teacher();

        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.CREATED).body("Teacher added successfully");

        when(teacherService.addTeacher(teacher)).thenReturn(true);

        ResponseEntity<String> actualResponse = teacherController.addTeacher(teacher);
        assertEquals(expectedResponse.getStatusCode(),actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(),actualResponse.getBody());
        verify(teacherService, times(1)).addTeacher(teacher);

    }

    @Test
    void testAddTeacher_Failure() {
        Teacher teacher = new Teacher();

        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add Teacher");

        when(teacherService.addTeacher(teacher)).thenReturn(false);

        ResponseEntity<String> actualResponse = teacherController.addTeacher(teacher);
        assertEquals(expectedResponse.getStatusCode(),actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(),actualResponse.getBody());
        verify(teacherService, times(1)).addTeacher(teacher);

    }

    @Test
    void testGetAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(new Teacher("Example1", "example1@example.com",LocalDate.of(1987,8,26)));
        teachers.add(new Teacher("Example2", "example2@example.com",LocalDate.of(1987,8,26) ));

        ResponseEntity<List<Teacher>> expectedResponse = ResponseEntity.ok(teachers);

        when(teacherService.getAllTeachers()).thenReturn(teachers);

        ResponseEntity<List<Teacher>> actualResponse = teacherController.getAllTeachers();

        assertEquals(expectedResponse.getStatusCode(),actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(),actualResponse.getBody());
        verify(teacherService,times(1)).getAllTeachers();

    }

    @Test
    void testUpdateTeacher_Success() {
        Long teacherId = 1L;
        String name = "Example";
        String email = "example@example.com";

        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Teacher updated successfully");

        when(teacherService.updateTeacher(anyLong(),any(),any())).thenReturn(1);

        ResponseEntity<String> actualResponse = teacherController.updateTeacher(teacherId,name,email);
        assertEquals(expectedResponse.getStatusCode(),actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(),actualResponse.getBody());
        verify(teacherService,times(1)).updateTeacher(teacherId,name,email);
    }

    @Test
    void testUpdateTeacher_EmailTaken() {
        Long teacherId = 1L;
        String name = "Example";
        String email = "example@example.com";

        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.CONFLICT).body("Email taken");

        when(teacherService.updateTeacher(anyLong(),any(),any())).thenReturn(-1);

        ResponseEntity<String> actualResponse = teacherController.updateTeacher(teacherId,name,email);
        assertEquals(expectedResponse.getStatusCode(),actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(),actualResponse.getBody());
        verify(teacherService,times(1)).updateTeacher(teacherId,name,email);
    }

    @Test
    void testUpdateTeacher_ParentNotFound() {
        Long teacherId = 1L;
        String name = "Example";
        String email = "example@example.com";

        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Teacher not found");

        when(teacherService.updateTeacher(anyLong(),any(),any())).thenReturn(0);

        ResponseEntity<String> actualResponse = teacherController.updateTeacher(teacherId,name,email);
        assertEquals(expectedResponse.getStatusCode(),actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(),actualResponse.getBody());
        verify(teacherService,times(1)).updateTeacher(teacherId,name,email);
    }

    @Test
    void testDeleteParent_Success() {
        Long teacherId = 1L;
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Teacher deleted successfully");

        when(teacherService.deleteTeacher(teacherId)).thenReturn(true);

        ResponseEntity<String> actualResponse = teacherController.deleteTeacher(teacherId);
        assertEquals(expectedResponse.getStatusCode(),actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(),actualResponse.getBody());
        verify(teacherService,times(1)).deleteTeacher(teacherId);
    }

    @Test
    void testDeleteParent_Failure() {
        Long teacherId = 1L;
        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Teacher not found");

        when(teacherService.deleteTeacher(teacherId)).thenReturn(false);

        ResponseEntity<String> actualResponse = teacherController.deleteTeacher(teacherId);
        assertEquals(expectedResponse.getStatusCode(),actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(),actualResponse.getBody());
        verify(teacherService,times(1)).deleteTeacher(teacherId);
    }
}