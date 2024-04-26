package com.example.schoolManagementApplication.repositories;

import com.example.schoolManagementApplication.models.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParentRepository extends JpaRepository<Parent, Long> {

    Optional<Parent> findById(Long id);
    Optional<Parent> findParentByEmail(String email);
}
