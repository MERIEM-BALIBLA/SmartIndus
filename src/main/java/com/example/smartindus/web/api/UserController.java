package com.example.smartindus.web.api;

import com.example.smartindus.DTO.User;
import com.example.smartindus.service.interfaces.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('TECHNICIEN_DE_MAINTENANCE')")
    public ResponseEntity<Page<User>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> Users = service.findAllUsers(pageable);
        return ResponseEntity.ok(Users);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUserEntity = service.createUser(user);
        return ResponseEntity.ok(savedUserEntity);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        service.deleteUser(id);
        return ResponseEntity.ok("L'utilisateur a été bien supprimé!");
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User user) {
        User savedUserEntity = service.updateUser(id, user);
        return ResponseEntity.ok(savedUserEntity);
    }
}
