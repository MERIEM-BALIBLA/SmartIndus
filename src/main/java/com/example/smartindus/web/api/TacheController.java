package com.example.smartindus.web.api;

import com.example.smartindus.domain.Tache;
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
@RequestMapping("/api/Tache")
public class TacheController {
    private final TacheService service;
    
    public TacheController(TacheService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public ResponseEntity<Page<Tache>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Tache> Taches = service.findAll(pageable);
        return ResponseEntity.ok(Taches);
    }

    @PostMapping("/create")
    public ResponseEntity<Tache> save(@RequestBody Tache Tache){
        Tache savedTache = service.save(Tache);
        return ResponseEntity.ok(savedTache);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tache> update(@RequestBody Tache Tache, @PathVariable UUID id){
        Tache updatedTache = service.update(id, Tache);
        return ResponseEntity.ok(updatedTache);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.ok("Tache a été bien supprimer");
    }
}
