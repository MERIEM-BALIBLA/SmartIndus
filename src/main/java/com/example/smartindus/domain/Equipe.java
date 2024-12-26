package com.example.smartindus.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany
    private List<Utilisateur> techniciens;

    @ManyToOne
    private Utilisateur responsable;

    @ManyToOne
    private Utilisateur operateur;
}
