package com.example.smartindus.web.api;

import com.example.smartindus.DTO.Equipe;
import com.example.smartindus.DTO.EquipeDetailDTO;
import com.example.smartindus.service.interfaces.EquipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Page<Equipe>> getAllEquipes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(equipeService.getAllEquipes(page, size));
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

//    @GetMapping("/with-details")
//    public ResponseEntity<List<EquipeDetailDTO>> getEquipesWithDetails() {
//        List<EquipeDetailDTO> equipes = equipeService.getEquipesWithDetails();
//        return ResponseEntity.ok(equipes);
//    }

    @GetMapping("/with-details")
    public ResponseEntity<Page<EquipeDetailDTO>> getEquipesWithDetails(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<EquipeDetailDTO> equipesPage = equipeService.getEquipesWithDetails(pageable);
        return ResponseEntity.ok(equipesPage);
    }


}
