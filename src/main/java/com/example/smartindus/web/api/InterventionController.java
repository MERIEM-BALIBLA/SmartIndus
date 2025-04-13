package com.example.smartindus.web.api;

import com.example.smartindus.DTO.Intervention;
import com.example.smartindus.DTO.InterventionMapper;
import com.example.smartindus.domain.InterventionEntity;
import com.example.smartindus.service.interfaces.InterventionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/interventions")
public class InterventionController {

    @Autowired
    private InterventionService interventionService;

    @Autowired
    private InterventionMapper interventionMapper;

    @PostMapping
    @PreAuthorize("hasRole('RESPONSABLE_DE_MAINTENANCE') or hasRole('ADMIN')")
    public ResponseEntity<Intervention> createIntervention(@RequestBody Intervention intervention) {
        try {
            InterventionEntity entity = interventionMapper.toEntity(intervention);
            InterventionEntity savedEntity = interventionService.saveIntervention(entity);
            return new ResponseEntity<>(interventionMapper.toDTO(savedEntity), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erreur lors de la création de l'intervention: " + e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('RESPONSABLE_DE_MAINTENANCE') or hasRole('ADMIN')")
    public ResponseEntity<Page<Intervention>> getAllInterventions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateDebut") String sortBy) {

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
            Page<InterventionEntity> pageResult = interventionService.findAllInterventions(pageable);
            Page<Intervention> dtoPage = pageResult.map(interventionMapper::toDTO);
            return ResponseEntity.ok(dtoPage);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erreur lors de la récupération des interventions: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('RESPONSABLE_DE_MAINTENANCE') or hasRole('ADMIN')")
    public ResponseEntity<Intervention> getInterventionById(@PathVariable UUID id) {
        try {
            Optional<InterventionEntity> interventionOpt = interventionService.findInterventionById(id);

            return interventionOpt
                    .map(entity -> ResponseEntity.ok(interventionMapper.toDTO(entity)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erreur lors de la récupération de l'intervention: " + e.getMessage());
        }
    }

    @GetMapping("/equipement/{equipementId}")
    @PreAuthorize("hasRole('RESPONSABLE_DE_MAINTENANCE') or hasRole('ADMIN')")
    public ResponseEntity<List<Intervention>> getInterventionsByEquipementId(
            @PathVariable UUID equipementId) {
        try {
            List<InterventionEntity> interventions = interventionService.findByEquipementId(equipementId);
            List<Intervention> dtos = interventions.stream()
                    .map(interventionMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erreur lors de la récupération des interventions: " + e.getMessage());
        }
    }

    @GetMapping("/current")
    @PreAuthorize("hasRole('RESPONSABLE_DE_MAINTENANCE') or hasRole('ADMIN')")
    public ResponseEntity<List<Intervention>> getCurrentInterventions() {
        try {
            List<InterventionEntity> interventions = interventionService.findCurrentInterventions();
            List<Intervention> dtos = interventions.stream()
                    .map(interventionMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erreur lors de la récupération des interventions en cours: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('RESPONSABLE_DE_MAINTENANCE') or hasRole('ADMIN')")
    public ResponseEntity<Intervention> updateIntervention(
            @PathVariable UUID id,
            @RequestBody Intervention intervention) {

        try {
            InterventionEntity entity = interventionMapper.toEntity(intervention);
            InterventionEntity updatedEntity = interventionService.updateIntervention(id, entity);
            return ResponseEntity.ok(interventionMapper.toDTO(updatedEntity));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erreur lors de la mise à jour de l'intervention: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('RESPONSABLE_DE_MAINTENANCE') or hasRole('ADMIN')")
    public ResponseEntity<String> deleteIntervention(@PathVariable UUID id) {
        try {
            interventionService.deleteIntervention(id);
            return ResponseEntity.ok("l'Intervention a ete bien supprimé!!");
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erreur lors de la suppression de l'intervention: " + e.getMessage());
        }
    }
}