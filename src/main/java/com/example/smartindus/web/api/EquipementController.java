package com.example.smartindus.web.api;

import com.example.smartindus.DTO.Equipement;
import com.example.smartindus.DTO.EquipementMapper;
import com.example.smartindus.domain.EquipementEntity;
import com.example.smartindus.service.interfaces.EquipementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/equipements")
public class EquipementController {

    @Autowired
    private EquipementService equipementService;

    @Autowired
    private EquipementMapper equipementMapper;

    @PostMapping
    public ResponseEntity<Equipement> createEquipement(@RequestBody Equipement equipement) {
        EquipementEntity entity = equipementMapper.toEntity(equipement);
        EquipementEntity savedEntity = equipementService.saveEquipmenet(entity);
        return new ResponseEntity<>(equipementMapper.toDTO(savedEntity), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Equipement>> getAllEquipements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nom") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<EquipementEntity> pageResult = equipementService.findAllEquipmenets(pageable);

        Page<Equipement> dtoPage = pageResult.map(equipementMapper::toDTO);
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipement> getEquipementById(@PathVariable UUID id) {
        Optional<EquipementEntity> equipementOpt = equipementService.findEquipement(id);

        return equipementOpt
                .map(entity -> ResponseEntity.ok(equipementMapper.toDTO(entity)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipement> updateEquipement(
            @PathVariable UUID id,
            @RequestBody Equipement equipement) {

        try {
            EquipementEntity entity = equipementMapper.toEntity(equipement);
            EquipementEntity updatedEntity = equipementService.updateEquipmenet(id, entity);
            return ResponseEntity.ok(equipementMapper.toDTO(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEquipement(@PathVariable UUID id) {
        equipementService.deleteEquipmenet(id);
        return ResponseEntity.ok("L'équipement a été bien supprimé!");
    }
}