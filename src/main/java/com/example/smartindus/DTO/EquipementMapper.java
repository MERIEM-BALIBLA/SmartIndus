package com.example.smartindus.DTO;

import com.example.smartindus.domain.EquipementEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EquipementMapper {

    Equipement toDTO(EquipementEntity equipementEntity);
    EquipementEntity toEntity(Equipement dto);
}