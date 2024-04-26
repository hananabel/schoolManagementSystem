package com.example.schoolManagementApplication.repositories;

import com.example.schoolManagementApplication.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findById(Long id);
    Optional<Teacher> findTeacherByEmail(String email);
}
