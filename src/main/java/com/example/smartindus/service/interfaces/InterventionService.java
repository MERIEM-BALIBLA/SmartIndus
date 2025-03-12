package com.example.smartindus.service.interfaces;

import com.example.smartindus.domain.InterventionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface InterventionService {

    InterventionEntity saveIntervention(InterventionEntity interventionEntity);

    Page<InterventionEntity> findAllInterventions(Pageable pageable);

    Optional<InterventionEntity> findInterventionById(UUID id);

    InterventionEntity updateIntervention(UUID id, InterventionEntity interventionEntity);

    void deleteIntervention(UUID id);

    List<InterventionEntity> findByEquipementId(UUID equipementId);

    List<InterventionEntity> findCurrentInterventions();
}
