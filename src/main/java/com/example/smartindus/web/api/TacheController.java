package com.example.smartindus.web.api;

import com.example.smartindus.domain.TacheEntity;
import com.example.smartindus.service.interfaces.TacheService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@RestController
@RequestMapping("/api/taches")
public class TacheController {
    private final TacheService service;
    
    public TacheController(TacheService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<TacheEntity>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TacheEntity> Taches = service.findAllTaches(pageable);
        return ResponseEntity.ok(Taches);
    }

    @PostMapping("/create")
    public ResponseEntity<TacheEntity> save(@RequestBody TacheEntity TacheEntity){
        TacheEntity savedTacheEntity = service.save(TacheEntity);
        return ResponseEntity.ok(savedTacheEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TacheEntity> update(@RequestBody TacheEntity TacheEntity, @PathVariable UUID id){
        TacheEntity updatedTacheEntity = service.update(id, TacheEntity);
        return ResponseEntity.ok(updatedTacheEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.ok("Tache a été bien supprimer");
    }
}
