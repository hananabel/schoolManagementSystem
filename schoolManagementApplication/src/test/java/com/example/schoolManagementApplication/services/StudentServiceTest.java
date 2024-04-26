package com.example.schoolManagementApplication.services;

import com.example.schoolManagementApplication.models.Parent;
import com.example.schoolManagementApplication.models.Student;
import com.example.schoolManagementApplication.models.Teacher;
import com.example.schoolManagementApplication.repositories.ParentRepository;
import com.example.schoolManagementApplication.repositories.StudentRepository;
import com.example.schoolManagementApplication.repositories.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ParentService parentService;

    @Mock
    private TeacherService teacherService;

    @Mock
    private ParentRepository parentRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddStudent_Success() {
        // Create a new Student object
        Student student = new Student();
        student.setEmail("student@example.com");

        // Define the parentId and teacherId
        Long parentId = 1L;
        Long teacherId = 2L;

        // Mock the behavior of the studentRepository to return an empty Optional when finding a student by email
        when(studentRepository.findStudentByEmail(student.getEmail())).thenReturn(Optional.empty());

        when(parentRepository.findById(parentId)).thenReturn(Optional.of(new Parent()));
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(new Teacher()));

        // Mock the behavior of the studentRepository to return the saved student when saving
        when(studentRepository.save(student)).thenReturn(student);

        // Call the addStudent method of the studentService
        boolean result = studentService.addStudent(parentId, teacherId, student);

        // Assert that the result is true, indicating success
        assertTrue(result);

        // Verify that the save method of the studentRepository was called exactly once with the student object
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void testAddStudent_EmailExists() {
        // Create a new Student object
        Student existingStudent = new Student();
        existingStudent.setId(1L);
        existingStudent.setEmail("student@example.com");

        Student newStudent = new Student();
        newStudent.setEmail("student@example.com");

        // Define the parentId and teacherId
        Long parentId = 1L;
        Long teacherId = 2L;

        // Mock the behavior of the studentRepository to return an  Optional when finding a student by email
        when(studentRepository.findStudentByEmail(newStudent.getEmail())).thenReturn(Optional.of(existingStudent));

        boolean result = studentService.addStudent(parentId, teacherId, newStudent);

        // Assert that the result is false, indicating failure
        assertFalse(result);

        // Verify that the save method of the studentRepository was never called
        verify(studentRepository, never()).save(newStudent);
    }

    @Test
    void testAddStudent_TeacherOrParentNotFound() {
        // Create a new Student object
        Student student = new Student();
        student.setEmail("student@example.com");

        // Define the parentId and teacherId
        Long parentId = 1L;
        Long teacherId = 2L;

        // Mock the behavior of the studentRepository to return an empty Optional when finding a student by email
        when(studentRepository.findStudentByEmail(student.getEmail())).thenReturn(Optional.empty());

        when(parentRepository.findById(parentId)).thenReturn(Optional.empty());
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());

        // Mock the behavior of the studentRepository to return the saved student when saving
        //when(studentRepository.save(student)).thenReturn(student);

        // Call the addStudent method of the studentService
        boolean result = studentService.addStudent(parentId, teacherId, student);

        // Assert that the result is true, indicating success
        assertFalse(result);

        // Verify that the save method of the studentRepository was called exactly once with the student object
        verify(studentRepository, never()).save(student);
    }


    @Test
    void getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student());
        studentList.add(new Student());

        when(studentRepository.findAll()).thenReturn(studentList);

        List<Student> result = studentService.getAllStudents();
        assertEquals(2, result.size());
        verify(studentRepository, times(1)).findAll();
    }



    @Test
    void updateStudent_Success() {
        Long studentId = 1L;
        String studentName = "Example";
        String studentEmail = "example@example.com";
        Long teacherId = 2L;

        // Mock student
        Student student = new Student();
        student.setId(studentId);

        // Mock teacher
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);

        // Mock behavior of repositories
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(studentRepository.findStudentByEmail(studentEmail)).thenReturn(Optional.empty());
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));

        // Call the method under test
        Integer result = studentService.updateStudent(studentId, studentName, studentEmail, teacherId);

        // Verify that the student is updated
        assertEquals(1, result);
        assertEquals(studentName, student.getName());
        assertEquals(studentEmail, student.getEmail());
        assertEquals(teacher, student.getTeacher());

        // Verify interactions with repositories
        verify(studentRepository, times(1)).findById(studentId);
        verify(studentRepository, times(1)).findStudentByEmail(studentEmail);
        verify(teacherRepository, times(1)).findById(teacherId);

    }

    @Test
    void UpdateStudent_EmailExists() {
        Long studentId = 1L;
        String studentName = "Example";
        String studentEmail = "example@example.com";
        Long teacherId = 1L;
        Student student = new Student();
        student.setId(studentId);
        Student existingStudent = new Student();
        existingStudent.setEmail(studentEmail);
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(studentRepository.findStudentByEmail(studentEmail)).thenReturn(Optional.of(existingStudent));


        Integer result = studentService.updateStudent(studentId, studentName, studentEmail, teacherId);
        assertEquals(-1, result);
        assertEquals(studentName, student.getName());
        assertNotEquals(studentEmail, student.getEmail());


        verify(studentRepository, times(1)).findById(studentId);
        verify(studentRepository, times(1)).findStudentByEmail(studentEmail);
        verify(studentRepository, never()).save(student);

    }

    @Test
    void updateStudent_TeacherNotFound() {
        Long studentId = 1L;
        String studentName = "Example";
        String studentEmail = "example@example.com";
        Long teacherId = 2L;

        // Mock student
        Student student = new Student();
        student.setId(studentId);

        // Mock teacher
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);

        // Mock behavior of repositories
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(studentRepository.findStudentByEmail(studentEmail)).thenReturn(Optional.empty());
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());

        // Call the method under test
        Integer result = studentService.updateStudent(studentId, studentName, studentEmail, teacherId);

        // Verify that the student is updated
        assertEquals(-2, result);
        assertEquals(studentName, student.getName());
        assertEquals(studentEmail, student.getEmail());
        assertNotEquals(teacher, student.getTeacher());

        // Verify interactions with repositories
        verify(studentRepository, times(1)).findById(studentId);
        verify(studentRepository, times(1)).findStudentByEmail(studentEmail);
        verify(studentRepository, never()).save(student);
    }

    @Test
    void updateStudent_StudentNotFound() {
        Long studentId = 1L;
        String studentName = "Example";
        String studentEmail = "example@example.com";
        Long teacherId = 2L;

        // Mock student
        Student student = new Student();
        student.setId(studentId);

        // Mock teacher
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);

        // Mock behavior of repositories
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());


        // Call the method under test
        Integer result = studentService.updateStudent(studentId, studentName, studentEmail, teacherId);

        // Verify that the student is updated
        assertEquals(0, result);


        // Verify interactions with repositories
        verify(studentRepository, times(1)).findById(studentId);

    }


    @Test
    void deleteStudent_Success() {
        Long studentId = 1L;

        when(studentRepository.existsById(studentId)).thenReturn(true);

        boolean result = studentService.deleteStudent(studentId);
        assertTrue(result);
        verify(studentRepository, times(1)).existsById(studentId);
        verify(studentRepository, times(1)).deleteById(studentId);
    }

    @Test
    void deleteStudent_Failure() {
        Long studentId = 1L;

        when(studentRepository.existsById(studentId)).thenReturn(false);

        boolean result = studentService.deleteStudent(studentId);
        assertFalse(result);
        verify(studentRepository, times(1)).existsById(studentId);

    }
}