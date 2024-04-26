package com.example.schoolManagementApplication.controllers;

import com.example.schoolManagementApplication.models.Parent;
import com.example.schoolManagementApplication.services.ParentService;
import com.example.schoolManagementApplication.services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ParentControllerTest {

    @Mock
    private ParentService parentService;

    @InjectMocks
    private ParentController parentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddParent_Success() {
        Parent parent = new Parent();

        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.CREATED).body("Parent added successfully");

        when(parentService.addParent(parent)).thenReturn(true);

        ResponseEntity<String> actualResponse = parentController.addParent(parent);
        assertEquals(expectedResponse.getStatusCode(),actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(),actualResponse.getBody());
        verify(parentService, times(1)).addParent(parent);

    }

    @Test
    void testAddParent_Failure() {
        Parent parent = new Parent();

        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.CONFLICT).body("Parent already exists");

        when(parentService.addParent(parent)).thenReturn(false);

        ResponseEntity<String> actualResponse = parentController.addParent(parent);
        assertEquals(expectedResponse.getStatusCode(),actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(),actualResponse.getBody());
        verify(parentService, times(1)).addParent(parent);

    }

    @Test
    void testGetAllParents() {
        List<Parent> parents = new ArrayList<>();
        parents.add(new Parent("Example1", "example1@example.com", LocalDate.of(1987,8,26)));
        parents.add(new Parent("Example2", "example2@example.com", LocalDate.of(1987,8,26)));

        ResponseEntity<List<Parent>> expectedResponse = ResponseEntity.ok(parents);

        when(parentService.getAllParents()).thenReturn(parents);

        ResponseEntity<List<Parent>> actualResponse = parentController.getAllParents();

        assertEquals(expectedResponse.getStatusCode(),actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(),actualResponse.getBody());
        verify(parentService,times(1)).getAllParents();

    }

    @Test
    void testUpdateParent_Success() {
        Long parentId = 1L;
        String name = "Example";
        String email = "example@example.com";

        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Parent updated successfully");

        when(parentService.updateParent(anyLong(),any(),any())).thenReturn(1);

        ResponseEntity<String> actualResponse = parentController.updateParent(parentId,name,email);
        assertEquals(expectedResponse.getStatusCode(),actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(),actualResponse.getBody());
        verify(parentService,times(1)).updateParent(parentId,name,email);
    }

    @Test
    void testUpdateParent_EmailTaken() {
        Long parentId = 1L;
        String name = "Example";
        String email = "example@example.com";

        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.CONFLICT).body("Email taken");

        when(parentService.updateParent(anyLong(),any(),any())).thenReturn(-1);

        ResponseEntity<String> actualResponse = parentController.updateParent(parentId,name,email);
        assertEquals(expectedResponse.getStatusCode(),actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(),actualResponse.getBody());
        verify(parentService,times(1)).updateParent(parentId,name,email);
    }

    @Test
    void testUpdateParent_ParentNotFound() {
        Long parentId = 1L;
        String name = "Example";
        String email = "example@example.com";

        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parent not found");

        when(parentService.updateParent(anyLong(),any(),any())).thenReturn(0);

        ResponseEntity<String> actualResponse = parentController.updateParent(parentId,name,email);
        assertEquals(expectedResponse.getStatusCode(),actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(),actualResponse.getBody());
        verify(parentService,times(1)).updateParent(parentId,name,email);
    }

    @Test
    void testDeleteParent_Success() {
        Long parentId = 1L;
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Parent deleted successfully");

        when(parentService.deleteParent(parentId)).thenReturn(true);

        ResponseEntity<String> actualResponse = parentController.deleteParent(parentId);
        assertEquals(expectedResponse.getStatusCode(),actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(),actualResponse.getBody());
        verify(parentService,times(1)).deleteParent(parentId);
    }

    @Test
    void testDeleteParent_Failure() {
        Long parentId = 1L;
        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parent not found");

        when(parentService.deleteParent(parentId)).thenReturn(false);

        ResponseEntity<String> actualResponse = parentController.deleteParent(parentId);
        assertEquals(expectedResponse.getStatusCode(),actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(),actualResponse.getBody());
        verify(parentService,times(1)).deleteParent(parentId);
    }
}