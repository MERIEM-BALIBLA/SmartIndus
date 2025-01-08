package com.example.smartindus.web.api;

import com.example.smartindus.domain.Utilisateur;
import com.example.smartindus.service.interfaces.UtilisateurService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/utilisateur")
public class UtilisateurController {

    private final UtilisateurService service;

    public UtilisateurController(UtilisateurService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public ResponseEntity<Page<Utilisateur>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Utilisateur> utilisateurs = service.findAll(pageable);
        return ResponseEntity.ok(utilisateurs);
    }

    @PostMapping("/create")
    public ResponseEntity<Utilisateur> save(@RequestBody Utilisateur utilisateur){
        Utilisateur savedUtilisateur = service.save(utilisateur);
        return ResponseEntity.ok(savedUtilisateur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> update(@RequestBody Utilisateur utilisateur, @PathVariable UUID id){
        Utilisateur updatedUtilisateur = service.update(id, utilisateur);
        return ResponseEntity.ok(updatedUtilisateur);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.ok("Utilisateur a été bien supprimer");
    }
}
