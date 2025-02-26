package com.example.smartindus.web.api;

import com.example.smartindus.domain.User;
import com.example.smartindus.service.interfaces.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/User")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public ResponseEntity<Page<User>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> Users = service.findAll(pageable);
        return ResponseEntity.ok(Users);
    }

    @PostMapping("/create")
    public ResponseEntity<User> save(@RequestBody User User){
        User savedUser = service.save(User);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@RequestBody User User, @PathVariable UUID id){
        User updatedUser = service.update(id, User);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.ok("User a été bien supprimer");
    }
}
