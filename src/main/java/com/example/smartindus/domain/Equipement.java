package com.example.smartindus.domain;

import com.example.smartindus.domain.enums.Equipement_Etat;
import com.example.smartindus.domain.enums.Equipement_Type;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Equipement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nom;
    private Equipement_Type type;
    private LocalDateTime dateInstallation;
    private Equipement_Etat etat;
}
