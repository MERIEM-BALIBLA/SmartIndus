package com.example.smartindus.service.implementation;

import com.example.smartindus.domain.EquipementEntity;
import com.example.smartindus.domain.InterventionEntity;
import com.example.smartindus.domain.enums.Equipement_Etat;
import com.example.smartindus.domain.enums.Statut_Intervention;
import com.example.smartindus.repository.EquipementRepository;
import com.example.smartindus.repository.InterventionRepository;
import com.example.smartindus.service.interfaces.InterventionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InterventionServiceImpl implements InterventionService {

    @Autowired
    private InterventionRepository interventionRepository;

    @Autowired
    private EquipementRepository equipementRepository;

    @Override
    @Transactional
    public InterventionEntity saveIntervention(InterventionEntity intervention) {
        validateIntervention(intervention);
        checkEquipementAvailability(intervention);
        updateEquipementStatus(intervention);
        if (intervention.getStatut() == null) {
            intervention.setStatut(Statut_Intervention.EN_COURS);
        }
        return interventionRepository.save(intervention);
    }

    @Override
    public Page<InterventionEntity> findAllInterventions(Pageable pageable) {
        return interventionRepository.findAll(pageable);
    }

    @Override
    public Optional<InterventionEntity> findInterventionById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID de l'intervention ne peut pas être nul");
        }
        return interventionRepository.findById(id);
    }

    @Override
    @Transactional
    public InterventionEntity updateIntervention(UUID id, InterventionEntity updatedIntervention) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID de l'intervention ne peut pas être nul");
        }

        Optional<InterventionEntity> existingInterventionOpt = interventionRepository.findById(id);
        if (!existingInterventionOpt.isPresent()) {
            throw new RuntimeException("Intervention avec l'ID " + id + " n'existe pas");
        }

        InterventionEntity existingIntervention = existingInterventionOpt.get();

        boolean equipementChanged = updatedIntervention.getEquipement() != null &&
                (existingIntervention.getEquipement() == null ||
                        !existingIntervention.getEquipement().getId().equals(
                                updatedIntervention.getEquipement().getId()));

        if (equipementChanged) {
            checkEquipementAvailability(updatedIntervention);
        }
        if (updatedIntervention.getType() != null) {
            existingIntervention.setType(updatedIntervention.getType());
        }
        if (updatedIntervention.getDateDebut() != null) {
            existingIntervention.setDateDebut(updatedIntervention.getDateDebut());
        }
        if (updatedIntervention.getDateFin() != null) {
            existingIntervention.setDateFin(updatedIntervention.getDateFin());
        }
        if (updatedIntervention.getStatut() != null) {
            boolean statusChangedToCompleted =
                    updatedIntervention.getStatut() == Statut_Intervention.TERMINEE &&
                            existingIntervention.getStatut() != Statut_Intervention.TERMINEE;
            existingIntervention.setStatut(updatedIntervention.getStatut());
            if (statusChangedToCompleted) {
                completeIntervention(existingIntervention);
            }
        }
        validateIntervention(existingIntervention);
        return interventionRepository.save(existingIntervention);
    }

    @Override
    @Transactional
    public void deleteIntervention(UUID id) {
        Optional<InterventionEntity> interventionOpt = findInterventionById(id);
        if (!interventionOpt.isPresent()) {
            throw new RuntimeException("Intervention avec l'ID " + id + " n'existe pas");
        }
        InterventionEntity intervention = interventionOpt.get();
        if (intervention.getStatut() == Statut_Intervention.EN_COURS &&
                intervention.getEquipement() != null) {
            EquipementEntity equipement = intervention.getEquipement();
            equipement.setEtatEquipement(Equipement_Etat.BON_ETAT);
            equipementRepository.save(equipement);
        }
        interventionRepository.delete(intervention);
    }

    @Override
    public List<InterventionEntity> findByEquipementId(UUID equipementId) {
        if (equipementId == null) {
            throw new IllegalArgumentException("L'ID de l'équipement ne peut pas être nul");
        }
        return interventionRepository.findByEquipementId(equipementId);
    }

    @Override
    public List<InterventionEntity> findCurrentInterventions() {
        LocalDateTime now = LocalDateTime.now();
        return interventionRepository.findByStatut(Statut_Intervention.EN_COURS);
    }

    private void validateIntervention(InterventionEntity intervention) {
        if (intervention.getType() == null) {
            throw new IllegalArgumentException("Le type d'intervention est obligatoire");
        }
        if (intervention.getDateDebut() == null) {
            throw new IllegalArgumentException("La date de début est obligatoire");
        }
        if (intervention.getId() == null && intervention.getDateDebut().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La date de début ne peut pas être dans le passé");
        }
        if (intervention.getDateFin() != null &&
                intervention.getDateFin().isBefore(intervention.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin doit être après la date de début");
        }
        if (intervention.getEquipement() == null) {
            throw new IllegalArgumentException("L'équipement est obligatoire pour une intervention");
        }
    }

    private void checkEquipementAvailability(InterventionEntity intervention) {
        if (intervention.getEquipement() == null) {
            return;
        }
        UUID equipementId = intervention.getEquipement().getId();
        LocalDateTime startDate = intervention.getDateDebut();
        LocalDateTime endDate = intervention.getDateFin();
        UUID currentInterventionId = intervention.getId();
        List<InterventionEntity> overlappingInterventions =
                interventionRepository.findOverlappingInterventions(
                        equipementId, startDate, endDate, currentInterventionId);
        if (!overlappingInterventions.isEmpty()) {
            throw new IllegalArgumentException(
                    "L'équipement est déjà programmé pour une autre intervention pendant cette période");
        }
    }

    private void updateEquipementStatus(InterventionEntity intervention) {
        if (intervention.getEquipement() == null) {
            return;
        }
        if ((intervention.getDateDebut().isBefore(LocalDateTime.now().plusMinutes(15)) ||
                intervention.getStatut() == Statut_Intervention.EN_COURS) &&
                intervention.getStatut() != Statut_Intervention.TERMINEE) {
            EquipementEntity equipement = intervention.getEquipement();
            equipement.setEtatEquipement(Equipement_Etat.EN_MATENANCE);
            equipementRepository.save(equipement);
            if (intervention.getStatut() != Statut_Intervention.EN_COURS) {
                intervention.setStatut(Statut_Intervention.EN_COURS);
            }
        }
    }

    private void completeIntervention(InterventionEntity intervention) {
        if (intervention.getEquipement() == null) {
            return;
        }
        EquipementEntity equipement = intervention.getEquipement();
        if (intervention.getDateFin() == null) {
            intervention.setDateFin(LocalDateTime.now());
        }
        equipement.setEtatEquipement(Equipement_Etat.BON_ETAT);
        equipementRepository.save(equipement);
    }
}