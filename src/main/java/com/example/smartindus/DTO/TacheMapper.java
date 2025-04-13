package com.example.smartindus.DTO;

import com.example.smartindus.DTO.Tache;
import com.example.smartindus.domain.InterventionEntity;
import com.example.smartindus.domain.TacheEntity;
import com.example.smartindus.domain.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface TacheMapper {

    @Mapping(source = "user_id", target = "user", qualifiedByName = "stringToUserEntity")
    @Mapping(source = "intervention_id", target = "intervention", qualifiedByName = "stringToInterventionEntity")
    TacheEntity toEntity(Tache dto);

    @Mapping(source = "user", target = "user_id", qualifiedByName = "userEntityToString")
    @Mapping(source = "intervention", target = "intervention_id", qualifiedByName = "interventionEntityToString")
    Tache toDto(TacheEntity entity);

    @Named("stringToUserEntity")
    default UserEntity stringToUserEntity(String userId) {
        if (userId == null) {
            return null;
        }
        UserEntity user = new UserEntity();
        user.setId(UUID.fromString(userId)); // Assurez-vous que UserEntity a un setter pour `id`
        return user;
    }

    @Named("userEntityToString")
    default String userEntityToString(UserEntity user) {
        return (user != null) ? user.getId().toString() : null;
    }

    @Named("stringToInterventionEntity")
    default InterventionEntity stringToInterventionEntity(String interventionId) {
        if (interventionId == null) {
            return null;
        }
        InterventionEntity intervention = new InterventionEntity();
        intervention.setId(UUID.fromString(interventionId)); // Assurez-vous que InterventionEntity a un setter pour `id`
        return intervention;
    }

    @Named("interventionEntityToString")
    default String interventionEntityToString(InterventionEntity intervention) {
        return (intervention != null) ? intervention.getId().toString() : null;
    }
}
