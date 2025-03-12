package com.example.smartindus.DTO;

import com.example.smartindus.domain.enums.Tache_Statut;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Tache {
    private UUID id;
    private String titre;
    private String description;
    private LocalDateTime depart;
    private LocalDateTime fin;
    private String user_id;
    private String intervention_id;
    private Tache_Statut status;
}
