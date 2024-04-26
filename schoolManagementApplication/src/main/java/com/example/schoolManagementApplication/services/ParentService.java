package com.example.schoolManagementApplication.services;

import com.example.schoolManagementApplication.models.Parent;
import com.example.schoolManagementApplication.models.Student;
import com.example.schoolManagementApplication.models.Teacher;
import com.example.schoolManagementApplication.repositories.ParentRepository;
import com.example.schoolManagementApplication.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ParentService { //Service Layer of Parent

    private final ParentRepository parentRepository;
    private final StudentService studentService;
    private final StudentRepository studentRepository;

    @Autowired
    // Constructor to inject repositories
    public ParentService(ParentRepository parentRepository, StudentService studentService, StudentRepository studentRepository) {
        this.parentRepository = parentRepository;
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }


    public boolean addParent(Parent parent) {
        Optional<Parent> parentOptional = parentRepository.findParentByEmail(parent.getEmail());
        if(parentOptional.isPresent()){
            return false; // Parent exists
        }
        parentRepository.save(parent);
        return true; // Parent added
    }

    public List<Parent> getAllParents() {
        return parentRepository.findAll();
    }

    public boolean deleteParent(Long parentId) {
        boolean exists = parentRepository.existsById(parentId);

        if(!exists) {
            return false; //Parent not found
        }

        Optional<Student> studentOptional = studentRepository.findByParentId(parentId);

        // Remove parent association if the student exists
        studentOptional.ifPresent(student -> {
            student.setParent(null);
            studentRepository.save(student);
        });
        parentRepository.deleteById(parentId);
        return true; //Parent Deleted

    }

    @Transactional
    public Integer updateParent(Long parentId, String parentName, String parentEmail){

        Parent parent = parentRepository.findById(parentId).orElse(null);
        if(parent == null){
            return 0; //Parent not found
        }

        if(parentName != null && parentName.length() > 0 && !Objects.equals(parent.getName(),parent)){
            parent.setName(parentName);
        }
        if(parentEmail != null && parentEmail.length() > 0 && !Objects.equals(parent.getEmail(),parentEmail)){
            Optional<Parent> parentOptional = parentRepository.findParentByEmail(parentEmail);
            if(parentOptional.isPresent()){
                return -1; //Email exists
            }
            parent.setEmail(parentEmail);
        }
        return 1; //Parent updated

    }
}
