package com.example.smartindus.web.api;

import com.example.smartindus.domain.Equipement;
import com.example.smartindus.service.interfaces.EquipementService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/equipement")
public class EquipementController {

    private final EquipementService service;

    public EquipementController(EquipementService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public ResponseEntity<Page<Equipement>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Equipement> equipements = service.findAll(pageable);
        return ResponseEntity.ok(equipements);
    }

    @PostMapping("/create")
    public ResponseEntity<Equipement> save(@RequestBody Equipement equipement){
        Equipement savedEquipement = service.save(equipement);
        return ResponseEntity.ok(savedEquipement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipement> update(@RequestBody Equipement equipement, @PathVariable UUID id){
        Equipement updatedEquipement = service.update(id, equipement);
        return ResponseEntity.ok(updatedEquipement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.ok("Equipement a été bien supprimer");
    }
}
