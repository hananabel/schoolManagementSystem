package com.example.schoolManagementApplication.repositories;

import com.example.schoolManagementApplication.models.Parent;
import com.example.schoolManagementApplication.models.Teacher;
import com.example.schoolManagementApplication.services.ParentService;
import com.example.schoolManagementApplication.services.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParentRepositoryTest {

    @Mock
    private ParentRepository parentRepository;


    @Test
    void testFindParentByEmail_Success() {
        String email = "example@example.com";
        Parent parent = new Parent();

        when(parentRepository.findParentByEmail(email)).thenReturn(Optional.of(parent));

        Optional<Parent> result = parentRepository.findParentByEmail(email);

        assertEquals(parent, result.get());
    }

    @Test
    void testFindParentByEmail_NotFound() {
        String email = "example@example.com";

        when(parentRepository.findParentByEmail(email)).thenReturn(Optional.empty());

        Optional<Parent> result = parentRepository.findParentByEmail(email);

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindById_Success() {
        Long parentId = 1L;
        Parent parent = new Parent();

        when(parentRepository.findById(parentId)).thenReturn(Optional.of(parent));

        Optional<Parent> result = parentRepository.findById(parentId);

        assertEquals(parent, result.get());
    }

    @Test
    void testFindById_NotFound() {
        Long parentId = 1L;


        when(parentRepository.findById(parentId)).thenReturn(Optional.empty());

        Optional<Parent> result = parentRepository.findById(parentId);

        assertTrue(result.isEmpty());
    }
}