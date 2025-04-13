package com.example.smartindus.web.api;

import com.example.smartindus.DTO.Tache;
import com.example.smartindus.service.interfaces.TacheService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.UUID;

@RestController
@RequestMapping("/api/taches")
@RequiredArgsConstructor
@Validated
public class TacheController {
    private final TacheService tacheService;

    @GetMapping
    @PreAuthorize("hasRole('OPERATEUR_DE_PRODUCTION') or hasRole('TECHNICIEN_DE_MAINTENANCE') or hasRole('ADMIN')")
    public ResponseEntity<Page<Tache>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Tache> taches = tacheService.findAllTaches(pageable);
        return ResponseEntity.ok(taches);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('OPERATEUR_DE_PRODUCTION') or hasRole('TECHNICIEN_DE_MAINTENANCE') or hasRole('ADMIN')")
    public ResponseEntity<Tache> getTacheById(@PathVariable UUID id) {
        return ResponseEntity.ok(tacheService.getTacheById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('OPERATEUR_DE_PRODUCTION') or hasRole('TECHNICIEN_DE_MAINTENANCE') or hasRole('ADMIN')")
    public ResponseEntity<Tache> createTache(@Valid @RequestBody Tache tache) {
        return new ResponseEntity<>(tacheService.createTache(tache), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OPERATEUR_DE_PRODUCTION') or hasRole('TECHNICIEN_DE_MAINTENANCE') or hasRole('ADMIN')")
    public ResponseEntity<Tache> updateTache(@PathVariable UUID id, @Valid @RequestBody Tache tache) {
        return ResponseEntity.ok(tacheService.updateTache(id, tache));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTache(@PathVariable UUID id) {
        tacheService.deleteTache(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{tacheId}/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TECHNICIEN_DE_MAINTENANCE')")
    public ResponseEntity<Tache> updateUserInTache(@PathVariable UUID tacheId, @PathVariable String userId) {
        Tache updatedTache = tacheService.updateUserInTache(tacheId, userId);
        return ResponseEntity.ok(updatedTache);
    }

    @PutMapping("/{tacheId}/intervention/{interventionId}")
    @PreAuthorize("hasRole('OPERATEUR_DE_PRODUCTION') or hasRole('TECHNICIEN_DE_MAINTENANCE') or hasRole('ADMIN')")
    public ResponseEntity<Tache> updateInterventionInTache(@PathVariable UUID tacheId, @PathVariable String interventionId) {
        Tache updatedTache = tacheService.updateInterventionInTache(tacheId, interventionId);
        return ResponseEntity.ok(updatedTache);
    }


}