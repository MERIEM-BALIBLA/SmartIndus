package com.example.smartindus.web.api;

import com.example.smartindus.domain.Equipe;
import com.example.smartindus.service.interfaces.EquipeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/equipe")
public class EquipeController {

    private final EquipeService service;

    public EquipeController(EquipeService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public ResponseEntity<Page<Equipe>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Equipe> equipes = service.findAll(pageable);
        return ResponseEntity.ok(equipes);
    }

    @PostMapping("/create")
    public ResponseEntity<Equipe> save(@RequestBody Equipe equipe){
        Equipe savedEquipe = service.save(equipe);
        return ResponseEntity.ok(savedEquipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipe> update(@RequestBody Equipe equipe, @PathVariable UUID id){
        Equipe updatedEquipe = service.update(id, equipe);
        return ResponseEntity.ok(updatedEquipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.ok("Equipe a été bien supprimer");
    }
}
