package com.example.smartindus.DTO;

import com.example.smartindus.domain.EquipementEntity;
import com.example.smartindus.domain.InterventionEntity;
import com.example.smartindus.repository.EquipementRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class InterventionMapper {

    @Autowired
    protected EquipementRepository equipementRepository;

    @Mapping(source = "equipementEntity", target = "equipementId", qualifiedByName = "equipementToId")
    public abstract Intervention toDTO(InterventionEntity entity);

    @Mapping(source = "equipementId", target = "equipementEntity", qualifiedByName = "idToEquipement")
    public abstract InterventionEntity toEntity(Intervention dto);

    @Named("equipementToId")
    UUID equipementToId(EquipementEntity equipement) {
        return equipement != null ? equipement.getId() : null;
    }

    @Named("idToEquipement")
    EquipementEntity idToEquipement(UUID id) {
        return id != null ? equipementRepository.findById(id).orElse(null) : null;
    }
}