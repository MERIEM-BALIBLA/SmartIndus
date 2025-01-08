package com.example.smartindus.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Rapport {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private LocalDateTime dateGeneration;
    private LocalDateTime dateIntervention;
    private double tauxResolution;


}
