package com.example.schoolManagementApplication.services;
import com.example.schoolManagementApplication.models.Student;
import com.example.schoolManagementApplication.models.Teacher;
import com.example.schoolManagementApplication.repositories.StudentRepository;
import com.example.schoolManagementApplication.repositories.TeacherRepository;
import com.example.schoolManagementApplication.services.TeacherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private TeacherService teacherService;

    @Test
    public void testAddTeacher_Success() {
        Teacher teacher = new Teacher();
        teacher.setEmail("teacher@example.com");

        when(teacherRepository.findTeacherByEmail("teacher@example.com")).thenReturn(Optional.empty());
        when(teacherRepository.save(teacher)).thenReturn(teacher);

        assertTrue(teacherService.addTeacher(teacher));
    }

    @Test
    public void testAddTeacher_Failure_EmailExists() {
        Teacher teacher = new Teacher();
        teacher.setEmail("teacher@example.com");

        when(teacherRepository.findTeacherByEmail("teacher@example.com")).thenReturn(Optional.of(new Teacher()));

        assertFalse(teacherService.addTeacher(teacher));
    }

    @Test
    public void testUpdateTeacher_Success() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("Old Name");
        teacher.setEmail("old@example.com");

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherRepository.findTeacherByEmail("new@example.com")).thenReturn(Optional.empty());

        assertEquals(1, teacherService.updateTeacher(1L, "New Name", "new@example.com"));
        assertEquals("New Name", teacher.getName());
        assertEquals("new@example.com", teacher.getEmail());
    }

    @Test
    public void testUpdateTeacher_NotFound() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        assertEquals(0, teacherService.updateTeacher(1L, "New Name", "new@example.com"));
    }

    @Test
    public void testDeleteTeacher_Success() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);

        when(teacherRepository.existsById(1L)).thenReturn(true);
        when(studentRepository.findByTeacherId(1L)).thenReturn(Optional.empty());

        assertTrue(teacherService.deleteTeacher(1L));
        verify(teacherRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteTeacher_NotFound() {
        when(teacherRepository.existsById(1L)).thenReturn(false);

        assertFalse(teacherService.deleteTeacher(1L));
        verify(teacherRepository, never()).deleteById(1L);
    }

    @Test
    public void testDeleteTeacher_WithAssociatedStudents() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);

        when(teacherRepository.existsById(1L)).thenReturn(true);
        when(studentRepository.findByTeacherId(1L)).thenReturn(Optional.of(new Student()));

        assertTrue(teacherService.deleteTeacher(1L));
        verify(studentRepository, times(1)).save(any(Student.class));
        verify(teacherRepository, times(1)).deleteById(1L);
    }
}
