package com.example.smartindus.DTO;

import com.example.smartindus.domain.TacheEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TacheMapper {

    @Mapping(source = "userEntity", target = "user_id")
    @Mapping(source = "interventionEntity", target = "intervention_id")
    Tache toDTO(TacheEntity entity);

    @Mapping(source = "user_id", target = "userEntity") // You need to handle this in the service layer
    @Mapping(source = "intervention_id", target = "interventionEntity") // Same for intervention
    TacheEntity toEntity(Tache dto);

}
