package com.example.schoolManagementApplication.repositories;
import com.example.schoolManagementApplication.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByParentId(Long id);
    Optional<Student> findByTeacherId(Long id);
    Optional<Student> findStudentByEmail(String email);


}
