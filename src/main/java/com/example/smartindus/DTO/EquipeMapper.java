package com.example.smartindus.DTO;

import com.example.smartindus.domain.EquipeEntity;
import com.example.smartindus.domain.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EquipeMapper {
    EquipeMapper INSTANCE = Mappers.getMapper(EquipeMapper.class);

    @Mapping(source = "responsable.id", target = "responsableId")
    @Mapping(source = "operateur.id", target = "operateurId")
    @Mapping(source = "techniciens", target = "technicienIds")
    Equipe toDto(EquipeEntity entity);

    @Mapping(target = "id", ignore = true) // ID généré par la base
    EquipeEntity toEntity(Equipe dto);

    // Méthode de conversion personnalisée pour transformer List<UserEntity> en List<UUID>
    default List<UUID> mapTechniciensToIds(List<UserEntity> techniciens) {
        return techniciens.stream()
                .map(UserEntity::getId)
                .collect(Collectors.toList());
    }
}
