package com.example.smartindus.service.interfaces;

import com.example.smartindus.domain.EquipementEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface EquipementService {
    EquipementEntity saveEquipmenet(EquipementEntity equipementEntity);

    Page<EquipementEntity> findAllEquipmenets(Pageable pageable);

    Optional<EquipementEntity> findEquipement(UUID id);

    EquipementEntity updateEquipmenet(UUID id, EquipementEntity equipementEntity);

    void deleteEquipmenet(UUID id);
}
