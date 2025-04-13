package com.example.smartindus.web.api;

import com.example.smartindus.DTO.Equipe;
import com.example.smartindus.service.interfaces.EquipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/equipes")
public class EquipeController {
    @Autowired
    private EquipeService equipeService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Equipe>> getAllEquipes() {
        return ResponseEntity.ok(equipeService.getAllEquipes());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Equipe> getEquipeById(@PathVariable UUID id) {
        return ResponseEntity.ok(equipeService.getEquipeById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Equipe> createEquipe(@Valid @RequestBody Equipe equipe) {
        return ResponseEntity.ok(equipeService.createEquipe(equipe));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Equipe> updateEquipe(@PathVariable UUID id, @Valid @RequestBody Equipe equipe) {
        return ResponseEntity.ok(equipeService.updateEquipe(id, equipe));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEquipe(@PathVariable UUID id) {
        equipeService.deleteEquipe(id);
        return ResponseEntity.noContent().build();
    }
}
