package com.example.smartindus.web.api;

import com.example.smartindus.domain.Intervention;
import com.example.smartindus.service.interfaces.InterventionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/intervention")
public class InterventionController {
    private final InterventionService service;

    public InterventionController(InterventionService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public ResponseEntity<Page<Intervention>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Intervention> Interventions = service.findAll(pageable);
        return ResponseEntity.ok(Interventions);
    }

    @PostMapping("/create")
    public ResponseEntity<Intervention> save(@RequestBody Intervention Intervention){
        Intervention savedIntervention = service.save(Intervention);
        return ResponseEntity.ok(savedIntervention);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Intervention> update(@RequestBody Intervention Intervention, @PathVariable UUID id){
        Intervention updatedIntervention = service.update(id, Intervention);
        return ResponseEntity.ok(updatedIntervention);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.ok("Intervention a été bien supprimer");
    }
}
