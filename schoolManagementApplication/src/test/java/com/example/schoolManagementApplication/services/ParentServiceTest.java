package com.example.schoolManagementApplication.services;
import com.example.schoolManagementApplication.models.Parent;
import com.example.schoolManagementApplication.repositories.ParentRepository;
import com.example.schoolManagementApplication.repositories.StudentRepository;
import com.example.schoolManagementApplication.services.ParentService;
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



class ParentServiceTest {

    @Mock
    private ParentRepository parentRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private ParentService parentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddParent_Success() {
        Parent parent = new Parent();
        parent.setEmail("parent@example.com");

        when(parentRepository.findParentByEmail(parent.getEmail())).thenReturn(Optional.empty());
        when(parentRepository.save(parent)).thenReturn(parent);

        boolean result = parentService.addParent(parent);
        assertTrue(result);
        verify(parentRepository, times(1)).save(parent);
    }

    @Test
    void testAddParent_Failure() {
        Parent existingParent = new Parent();
        existingParent.setId(1L);
        existingParent.setEmail("existingparent@example.com");

        Parent newParent = new Parent();
        newParent.setEmail("existingparent@example.com");

        when(parentRepository.findParentByEmail(newParent.getEmail())).thenReturn(Optional.of(existingParent));

        boolean result = parentService.addParent(newParent);
        assertFalse(result);
        verify(parentRepository, never()).save(newParent);
    }

    @Test
    void testGetAllParents() {
        List<Parent> parentList = new ArrayList<>();
        parentList.add(new Parent());
        parentList.add(new Parent());

        when(parentRepository.findAll()).thenReturn(parentList);

        List<Parent> result = parentService.getAllParents();
        assertEquals(2, result.size());
        verify(parentRepository, times(1)).findAll();
    }

    @Test
    void testDeleteParent_Success() {
        Long parentId = 1L;
        when(parentRepository.existsById(parentId)).thenReturn(true);

        boolean result = parentService.deleteParent(parentId);
        assertTrue(result);
        verify(parentRepository, times(1)).deleteById(parentId);
    }

    @Test
    void testDeleteParent_NotFound() {
        Long parentId = 1L;
        when(parentRepository.existsById(parentId)).thenReturn(false);

        boolean result = parentService.deleteParent(parentId);
        assertFalse(result);
        verify(parentRepository, never()).deleteById(parentId);
    }

    @Test
    void testUpdateParent_Success() {
        Long parentId = 1L;
        String parentName = "Updated Name";
        String parentEmail = "updated@example.com";

        Parent parent = new Parent();
        parent.setId(parentId);

        when(parentRepository.findById(parentId)).thenReturn(Optional.of(parent));
        when(parentRepository.findParentByEmail(parentEmail)).thenReturn(Optional.empty());

        Integer result = parentService.updateParent(parentId, parentName, parentEmail);
        assertEquals(1, result);
        assertEquals(parentName, parent.getName());
        assertEquals(parentEmail, parent.getEmail());
    }

    @Test
    void testUpdateParent_NotFound() {
        Long parentId = 1L;
        String parentName = "Updated Name";
        String parentEmail = "updated@example.com";

        when(parentRepository.findById(parentId)).thenReturn(Optional.empty());

        Integer result = parentService.updateParent(parentId, parentName, parentEmail);
        assertEquals(0, result);

        verify(parentRepository,times(1)).findById(parentId);
    }

    @Test
    void testUpdateParent_EmailTaken() {
        Long parentId = 1L;
        String parentName = "Updated Name";
        String parentEmail = "updated@example.com";

        Parent parent = new Parent();
        parent.setId(parentId);

        when(parentRepository.findById(parentId)).thenReturn(Optional.of(parent));
        when(parentRepository.findParentByEmail(parentEmail)).thenReturn(Optional.of(new Parent()));

        Integer result = parentService.updateParent(parentId, parentName, parentEmail);
        assertEquals(-1, result);
        assertEquals(parentName, parent.getName());
        assertNotEquals(parentEmail, parent.getEmail());
    }
}
