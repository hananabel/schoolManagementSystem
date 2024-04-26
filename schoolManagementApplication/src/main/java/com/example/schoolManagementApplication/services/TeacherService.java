package com.example.schoolManagementApplication.services;

import com.example.schoolManagementApplication.models.Student;
import com.example.schoolManagementApplication.models.Teacher;

import com.example.schoolManagementApplication.repositories.StudentRepository;
import com.example.schoolManagementApplication.repositories.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TeacherService { //Service Layer of Teacher

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Autowired
    // Constructor to inject repositories
    public TeacherService(TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }


    public boolean addTeacher(Teacher teacher) {
        Optional<Teacher> teacherOptional = teacherRepository.findTeacherByEmail(teacher.getEmail());
        if(teacherOptional.isPresent()){
            return false;
        }
        teacherRepository.save(teacher);
        return true;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Transactional
    public Integer updateTeacher(Long teacherId, String teacherName, String teacherEmail){

        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
        if(teacher == null){
            return 0; // Teacher not found
        }

        if(teacherName != null && teacherName.length() > 0 && !Objects.equals(teacher.getName(),teacherName)){
            teacher.setName(teacherName);
        }
        if(teacherEmail != null && teacherEmail.length() > 0 && !Objects.equals(teacher.getEmail(),teacherEmail)){
            Optional<Teacher> teacherOptional = teacherRepository.findTeacherByEmail(teacherEmail);
            if(teacherOptional.isPresent()){
                return -1; // Email exists
            }
            teacher.setEmail(teacherEmail);
        }
        return 1; // Teacher updated

    }

    public boolean deleteTeacher(Long teacherId) {

        boolean exists = teacherRepository.existsById(teacherId);
        if (!exists) {
            return false;
        }

        Optional<Student> studentOptional = studentRepository.findByTeacherId(teacherId);
        studentOptional.ifPresent(student -> {
            student.setTeacher(null);
            studentRepository.save(student);
        });
        teacherRepository.deleteById(teacherId);
        return true;
    }


}
