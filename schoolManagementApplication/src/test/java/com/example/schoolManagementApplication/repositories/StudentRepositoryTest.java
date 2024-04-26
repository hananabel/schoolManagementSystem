package com.example.schoolManagementApplication.repositories;

import com.example.schoolManagementApplication.models.Student;
import com.example.schoolManagementApplication.repositories.StudentRepository;
import com.example.schoolManagementApplication.services.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentRepositoryTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void findByParentId_Success() {
        // Mock data
        Long parentId = 1L;
        Student student = new Student();

        // Mock the behavior of the studentRepository to return an Optional containing the student
        when(studentRepository.findByParentId(parentId)).thenReturn(Optional.of(student));

        // Call the findByParentId method of the studentRepository
        Optional<Student> result = studentRepository.findByParentId(parentId);

        // Assert that the result is not empty and contains the expected student
        assertEquals(student, result.get());
    }

    @Test
    void findByParentId_NotFound() {
        // Mock data
        Long parentId = 1L;

        // Mock the behavior of the studentRepository to return an empty Optional
        when(studentRepository.findByParentId(parentId)).thenReturn(Optional.empty());

        // Call the findByParentId method of the studentRepository
        Optional<Student> result = studentRepository.findByParentId(parentId);

        // Assert that the result is empty
        assertTrue(result.isEmpty());
    }

    @Test
    void findByTeacherId_Success() {
        // Mock data
        Long teacherId = 1L;
        Student student = new Student();
        // Mock the behavior of the studentRepository to return an Optional containing the student
        when(studentRepository.findByTeacherId(teacherId)).thenReturn(Optional.of(student));

        // Call the findByTeacherId method of the studentRepository
        Optional<Student> result = studentRepository.findByTeacherId(teacherId);

        // Assert that the result is not empty and contains the expected student
        assertEquals(student, result.get());
    }

    @Test
    void findByTeacherId_NotFound() {
        // Mock data
        Long teacherId = 1L;
        // Mock the behavior of the studentRepository to return an Optional containing the student
        when(studentRepository.findByTeacherId(teacherId)).thenReturn(Optional.empty());

        // Call the findByTeacherId method of the studentRepository
        Optional<Student> result = studentRepository.findByTeacherId(teacherId);

        // Assert that the result is empty
        assertTrue(result.isEmpty());
    }

    @Test
    void findStudentByEmail_Success() {
        // Mock data
        String email = "test@example.com";
        Student student = new Student();

        // Mock the behavior of the studentRepository to return an Optional containing the student
        when(studentRepository.findStudentByEmail(email)).thenReturn(Optional.of(student));

        // Call the findStudentByEmail method of the studentRepository
        Optional<Student> result = studentRepository.findStudentByEmail(email);

        // Assert that the result is not empty and contains the expected student
        assertEquals(student, result.get());
    }

    @Test
    void findStudentByEmail_NotFound() {
        // Mock data
        String email = "test@example.com";

        // Mock the behavior of the studentRepository to return an Optional containing the student
        when(studentRepository.findStudentByEmail(email)).thenReturn(Optional.empty());

        // Call the findStudentByEmail method of the studentRepository
        Optional<Student> result = studentRepository.findStudentByEmail(email);

        // Assert that the result is not empty and contains the expected student
        assertTrue(result.isEmpty());
    }
}
