package com.example.smartindus.domain;

import com.example.smartindus.domain.enums.Tache_Statut;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String titre;
    private String description;
    private LocalDateTime depart;
    private LocalDateTime fin;
    @ManyToOne
    private Utilisateur utilisateur;
    @ManyToOne
    private Equipement equipement;
    @ManyToOne
    private Intervention intervention;
    private Tache_Statut status;
}
