package com.example.smartindus.DTO;

import com.example.smartindus.domain.enums.Equipement_Etat;
import com.example.smartindus.domain.enums.Equipement_Type;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Equipement {
    private UUID id;
    private String nom;
    private Equipement_Type type;
    private LocalDateTime dateInstallation;
    private Equipement_Etat etatEquipement;
}
