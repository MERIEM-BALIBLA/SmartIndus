package com.example.smartindus.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Equipe {
    private UUID id;

    @NotNull(message = "Le responsable est obligatoire")
    private UUID responsableId;

    @NotNull(message = "L'opérateur est obligatoire")
    private UUID operateurId;

    @Size(min = 1, message = "L'équipe doit avoir au moins un technicien")
    private List<UUID> technicienIds;
}
