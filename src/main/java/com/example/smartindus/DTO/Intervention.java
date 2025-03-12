package com.example.smartindus.DTO;

import com.example.smartindus.domain.enums.Statut_Intervention;
import com.example.smartindus.domain.enums.Type_Intervention;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Intervention {
    private UUID id;
    private Type_Intervention type;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private UUID equipementId;
    private Statut_Intervention statut;
}