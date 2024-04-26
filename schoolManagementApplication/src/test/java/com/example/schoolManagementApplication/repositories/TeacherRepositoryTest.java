package com.example.schoolManagementApplication.repositories;

import com.example.schoolManagementApplication.models.Parent;
import com.example.schoolManagementApplication.models.Teacher;
import com.example.schoolManagementApplication.services.ParentService;
import com.example.schoolManagementApplication.services.StudentService;
import com.example.schoolManagementApplication.services.TeacherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeacherRepositoryTest {

    @Mock
    private TeacherRepository teacherRepository;


    @Test
    void testFindTeacherByEmail_Success() {
        String email = "example@example.com";
        Teacher teacher = new Teacher();

        when(teacherRepository.findTeacherByEmail(email)).thenReturn(Optional.of(teacher));

        Optional<Teacher> result = teacherRepository.findTeacherByEmail(email);

        assertEquals(teacher, result.get());
    }

    @Test
        void testFindTeacherByEmail_NotFound() {
        String email = "example@example.com";

        when(teacherRepository.findTeacherByEmail(email)).thenReturn(Optional.empty());

        Optional<Teacher> result = teacherRepository.findTeacherByEmail(email);

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindById_Success() {
        Long teacherId = 1L;
        Teacher teacher = new Teacher();

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));

        Optional<Teacher> result = teacherRepository.findById(teacherId);

        assertEquals(teacher, result.get());
    }

    @Test
    void testFindById_NotFound() {
        Long teacherId = 1L;


        when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());

        Optional<Teacher> result = teacherRepository.findById(teacherId);

        assertTrue(result.isEmpty());
    }
}