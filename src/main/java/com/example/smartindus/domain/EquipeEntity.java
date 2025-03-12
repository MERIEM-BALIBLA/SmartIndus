package com.example.smartindus.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class EquipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany
    private List<UserEntity> techniciens;

    @ManyToOne
    private UserEntity responsable;

    @ManyToOne
    private UserEntity operateur;
}
