package com.example.smartindus.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipeDetailDTO {
    private UUID id;
    private User responsable;
    private User operateur;
    private List<User> techniciens;
}