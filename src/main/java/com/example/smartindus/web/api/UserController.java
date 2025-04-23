package com.example.smartindus.web.api;

import com.example.smartindus.DTO.User;
import com.example.smartindus.domain.UserEntity;
import com.example.smartindus.domain.enums.Role;
import com.example.smartindus.service.interfaces.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<User>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> Users = service.findAllUsers(pageable);
        return ResponseEntity.ok(Users);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUserEntity = service.createUser(user);
        return ResponseEntity.ok(savedUserEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        service.deleteUser(id);
        return ResponseEntity.ok("L'utilisateur a été bien supprimé!");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User user) {
        User savedUserEntity = service.updateUser(id, user);
        return ResponseEntity.ok(savedUserEntity);
    }

    @PutMapping("/{id}/role")
    public ResponseEntity updateUserRole(@PathVariable UUID id, @RequestBody Map<String, String> requestBody) {
        try {
            String roleRequest = requestBody.get("role");
            User updatedUser = service.updateUserRole(id, roleRequest);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable UUID id) {
        User user = service.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/by-role")
    public ResponseEntity<List<User>> getUsersByRole(@RequestParam Role role) {
        List<User> users = service.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }

}
