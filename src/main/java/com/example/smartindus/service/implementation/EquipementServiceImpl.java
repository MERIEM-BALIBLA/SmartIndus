package com.example.smartindus.service.implementation;

import com.example.smartindus.domain.EquipementEntity;
import com.example.smartindus.domain.enums.Equipement_Etat;
import com.example.smartindus.repository.EquipementRepository;
import com.example.smartindus.service.interfaces.EquipementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class EquipementServiceImpl implements EquipementService {
    @Autowired
    private EquipementRepository repository;

    @Override
    public EquipementEntity saveEquipmenet(EquipementEntity equipementEntity) {
        // Validation du nom
        validateNom(equipementEntity.getNom());

        // Vérification de l'unicité du nom
        if (existsByNom(equipementEntity.getNom())) {
            throw new IllegalArgumentException("Un équipement avec le nom '" + equipementEntity.getNom() + "' existe déjà");
        }

        // Validation du type d'équipement
        if (equipementEntity.getType() == null) {
            throw new IllegalArgumentException("Le type d'équipement ne peut pas être nul");
        }

        // Si la date d'installation n'est pas fournie, utiliser la date actuelle
        if (equipementEntity.getDateInstallation() == null) {
            equipementEntity.setDateInstallation(LocalDateTime.now());
        } else {
            // Valider que la date n'est pas dans le futur
            validateDate(equipementEntity.getDateInstallation());
        }

        // Si l'état de l'équipement n'est pas fourni, définir comme BON_ETAT par défaut
        if (equipementEntity.getEtatEquipement() == null) {
            equipementEntity.setEtatEquipement(Equipement_Etat.BON_ETAT);
        }

        return repository.save(equipementEntity);
    }

    @Override
    public Page<EquipementEntity> findAllEquipmenets(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<EquipementEntity> findEquipement(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID de l'équipement ne peut pas être nul");
        }
        return repository.findById(id);
    }

    @Override
    public EquipementEntity updateEquipmenet(UUID id, EquipementEntity equipementEntity) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID de l'équipement ne peut pas être nul");
        }

        Optional<EquipementEntity> existingEquipement = findEquipement(id);
        if (existingEquipement.isPresent()) {
            EquipementEntity updatedEquipementEntity = existingEquipement.get();

            // Validation des champs avant mise à jour
            if (equipementEntity.getNom() != null) {
                validateNom(equipementEntity.getNom());

                // Vérifier si le nouveau nom est différent de l'ancien et s'il est déjà utilisé
                if (!updatedEquipementEntity.getNom().equals(equipementEntity.getNom()) &&
                        existsByNom(equipementEntity.getNom())) {
                    throw new IllegalArgumentException("Un équipement avec le nom '" + equipementEntity.getNom() + "' existe déjà");
                }

                updatedEquipementEntity.setNom(equipementEntity.getNom());
            }

            if (equipementEntity.getType() != null) {
                updatedEquipementEntity.setType(equipementEntity.getType());
            }

            if (equipementEntity.getDateInstallation() != null) {
                validateDate(equipementEntity.getDateInstallation());
                updatedEquipementEntity.setDateInstallation(equipementEntity.getDateInstallation());
            }

            if (equipementEntity.getEtatEquipement() != null) {
                updatedEquipementEntity.setEtatEquipement(equipementEntity.getEtatEquipement());
            }

            return repository.save(updatedEquipementEntity);
        } else {
            throw new RuntimeException("Equipement avec l'ID " + id + " n'existe pas.");
        }
    }

    @Override
    public void deleteEquipmenet(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID de l'équipement ne peut pas être nul");
        }

        Optional<EquipementEntity> existingEquipement = findEquipement(id);
        if (existingEquipement.isPresent()) {
            EquipementEntity equipementEntity = existingEquipement.get();
            repository.delete(equipementEntity);
        } else {
            throw new RuntimeException("Equipement avec l'ID " + id + " n'existe pas.");
        }
    }

    // Méthodes de validation privées

    private void validateNom(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'équipement ne peut pas être vide");
        }

        if (nom.length() < 3 || nom.length() > 100) {
            throw new IllegalArgumentException("Le nom de l'équipement doit contenir entre 3 et 100 caractères");
        }
    }

    private void validateDate(LocalDateTime date) {
        if (date.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("La date d'installation ne peut pas être dans le futur");
        }
    }

    // Méthode pour vérifier si un équipement avec le même nom existe déjà
    private boolean existsByNom(String nom) {
        return repository.existsByNom(nom);
    }
}