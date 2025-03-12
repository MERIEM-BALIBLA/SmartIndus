package com.example.smartindus.web.api;

import com.example.smartindus.domain.EquipeEntity;
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
    public ResponseEntity<Page<EquipeEntity>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EquipeEntity> equipes = service.findAll(pageable);
        return ResponseEntity.ok(equipes);
    }

    @PostMapping("/create")
    public ResponseEntity<EquipeEntity> save(@RequestBody EquipeEntity equipeEntity){
        EquipeEntity savedEquipeEntity = service.save(equipeEntity);
        return ResponseEntity.ok(savedEquipeEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipeEntity> update(@RequestBody EquipeEntity equipeEntity, @PathVariable UUID id){
        EquipeEntity updatedEquipeEntity = service.update(id, equipeEntity);
        return ResponseEntity.ok(updatedEquipeEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.ok("Equipe a été bien supprimer");
    }
}
