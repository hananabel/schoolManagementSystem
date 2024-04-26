package com.example.schoolManagementApplication.controllers;

import com.example.schoolManagementApplication.models.Parent;
import com.example.schoolManagementApplication.services.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/parent")
public class ParentController { //Controller layer for Parent

    private final ParentService parentService;

    @Autowired
    // Constructor to initialize ParentController with ParentService
    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @PostMapping
    public ResponseEntity<String> addParent(@RequestBody Parent parent) { //Call the addParent method of Service Layer

        if (parentService.addParent(parent)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Parent added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Parent already exists");
        }
    }

    @GetMapping
    public ResponseEntity<List<Parent>> getAllParents() { //Call the getAllParents method of the Service Layer
        List<Parent> parents = parentService.getAllParents();
        return ResponseEntity.ok(parents);
    }

    @PutMapping(path="{parentId}")
    public ResponseEntity<String> updateParent(@PathVariable("parentId") Long parentId,
                                               @RequestParam(required = false) String name,
                                               @RequestParam(required = false) String email) { //Call the updateParent method of the Service Layer
        Integer updateSuccess = parentService.updateParent(parentId, name, email);
        // Handle appropriate Http Status Code
        if (updateSuccess == 1) {
            return ResponseEntity.ok("Parent updated successfully");
        } else {
            if(updateSuccess == -1){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email taken");
            }

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parent not found");
    }

    @DeleteMapping("/{parentId}")
    public ResponseEntity<String> deleteParent(@PathVariable("parentId") Long parentId) { //Call the deleteParent method of the Service Layer
        if (parentService.deleteParent(parentId)) {
            return ResponseEntity.ok("Parent deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parent not found");
        }
    }
}
