package com.example.smartindus.domain;

import com.example.smartindus.domain.enums.Statut_Intervention;
import com.example.smartindus.domain.enums.Type_Intervention;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class InterventionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Type_Intervention type;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    @OneToOne
    private EquipementEntity equipementEntity;
    private Statut_Intervention statut;
}
