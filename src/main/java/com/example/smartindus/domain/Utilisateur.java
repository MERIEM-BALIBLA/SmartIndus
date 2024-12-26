package com.example.smartindus.domain;

import com.example.smartindus.domain.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nom;
    private String prenom;
    private String email;
    private String cin;
    private String telephone;
    private Role role;
}
