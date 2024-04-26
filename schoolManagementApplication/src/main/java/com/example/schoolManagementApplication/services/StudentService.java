package com.example.schoolManagementApplication.services;

import com.example.schoolManagementApplication.models.Parent;
import com.example.schoolManagementApplication.models.Student;
import com.example.schoolManagementApplication.models.Teacher;
import com.example.schoolManagementApplication.repositories.ParentRepository;
import com.example.schoolManagementApplication.repositories.StudentRepository;
import com.example.schoolManagementApplication.repositories.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService { // Service Layer of student

    private final StudentRepository studentRepository;
    private final ParentRepository parentRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    // Constructor to inject repositories
    public StudentService(StudentRepository studentRepository, ParentRepository parentRepository, TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.parentRepository = parentRepository;
        this.teacherRepository = teacherRepository;
    }

    public boolean addStudent(Long parentId, Long teacherId, Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()) {
            return false; // Student with the same email already exists
        }

        Optional<Parent> parentOptional = parentRepository.findById(parentId);
        Optional<Teacher> teacherOptional = teacherRepository.findById(teacherId);

        if (parentOptional.isPresent() && teacherOptional.isPresent()) {
            student.setParent(parentOptional.get());
            student.setTeacher(teacherOptional.get());
            studentRepository.save(student);
            return true; // Student added successfully
        } else {
            return false; // Parent or Teacher not found
        }
    }
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Transactional
    public Integer updateStudent(Long studentId, String studentName, String studentEmail, Long teacherId){

        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return 0; //Student not found
        }

        if(studentName != null && studentName.length() > 0 && !Objects.equals(student.getName(),studentName)){
            student.setName(studentName);
        }
        if(studentEmail != null && studentEmail.length() > 0 && !Objects.equals(student.getEmail(),studentEmail)){
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(studentEmail);
            if(studentOptional.isPresent()){
                return -1; //Email taken
            }
            student.setEmail(studentEmail);
        }
        if(teacherId != null){
            Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
            if (teacher == null) {
                return -2; //Teacher not found
            }
            student.setTeacher(teacher);
        }
        return 1; //Student updated
        }

        public boolean deleteStudent(Long studentId) {

        boolean exists = studentRepository.existsById(studentId);
        if(!exists){
            return false;
        }
        studentRepository.deleteById(studentId);
        return true;
    }


}




