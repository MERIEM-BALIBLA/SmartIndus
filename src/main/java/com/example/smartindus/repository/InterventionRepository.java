package com.example.smartindus.repository;

import com.example.smartindus.domain.InterventionEntity;
import com.example.smartindus.domain.enums.Statut_Intervention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface InterventionRepository extends JpaRepository<InterventionEntity, UUID> {

    List<InterventionEntity> findByEquipementEntityId(UUID equipementId);

    List<InterventionEntity> findByStatut(Statut_Intervention statut);

    @Query("SELECT i FROM InterventionEntity i WHERE i.equipementEntity.id = :equipementId " +
            "AND ((i.dateDebut <= :endDate AND (i.dateFin IS NULL OR i.dateFin >= :startDate)) " +
            "OR (i.dateDebut BETWEEN :startDate AND :endDate)) " +
            "AND (i.id != :currentInterventionId OR :currentInterventionId IS NULL)")
    List<InterventionEntity> findOverlappingInterventions(
            @Param("equipementId") UUID equipementId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("currentInterventionId") UUID currentInterventionId);
}
