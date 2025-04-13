package com.example.smartindus.service.implementation;

import com.example.smartindus.DTO.Equipe;
import com.example.smartindus.DTO.EquipeMapper;
import com.example.smartindus.domain.EquipeEntity;
import com.example.smartindus.domain.UserEntity;
import com.example.smartindus.repository.EquipeRepository;
import com.example.smartindus.repository.UserRepository;
import com.example.smartindus.service.interfaces.EquipeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EquipeServiceImpl implements EquipeService {
    @Autowired
    private EquipeRepository equipeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EquipeMapper equipeMapper;

    @Override
    public List<Equipe> getAllEquipes() {
        return equipeRepository.findAll()
                .stream()
                .map(equipeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Equipe getEquipeById(UUID id) {
        EquipeEntity equipe = equipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Équipe avec l'ID " + id + " non trouvée."));
        return equipeMapper.toDto(equipe);
    }

    @Transactional
    @Override
    public Equipe createEquipe(Equipe equipeDto) {
        EquipeEntity equipe = equipeMapper.toEntity(equipeDto);

        // Vérification des utilisateurs existants
        UserEntity responsable = userRepository.findById(equipeDto.getResponsableId())
                .orElseThrow(() -> new RuntimeException("Responsable non trouvé."));
        UserEntity operateur = userRepository.findById(equipeDto.getOperateurId())
                .orElseThrow(() -> new RuntimeException("Opérateur non trouvé."));

        List<UserEntity> techniciens = equipeDto.getTechnicienIds().stream()
                .map(id -> userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Technicien avec ID " + id + " non trouvé.")))
                .collect(Collectors.toList());

        equipe.setResponsable(responsable);
        equipe.setOperateur(operateur);
        equipe.setTechniciens(techniciens);

        equipe = equipeRepository.save(equipe);
        return equipeMapper.toDto(equipe);
    }

    @Transactional
    @Override
    public Equipe updateEquipe(UUID id, Equipe equipeDto) {
        EquipeEntity existingEquipe = equipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Équipe avec l'ID " + id + " non trouvée."));

        UserEntity responsable = userRepository.findById(equipeDto.getResponsableId())
                .orElseThrow(() -> new RuntimeException("Responsable non trouvé."));
        UserEntity operateur = userRepository.findById(equipeDto.getOperateurId())
                .orElseThrow(() -> new RuntimeException("Opérateur non trouvé."));

        List<UserEntity> techniciens = equipeDto.getTechnicienIds().stream()
                .map(uid -> userRepository.findById(uid)
                        .orElseThrow(() -> new RuntimeException("Technicien avec ID " + uid + " non trouvé.")))
                .collect(Collectors.toList());

        existingEquipe.setResponsable(responsable);
        existingEquipe.setOperateur(operateur);
        existingEquipe.setTechniciens(techniciens);

        equipeRepository.save(existingEquipe);
        return equipeMapper.toDto(existingEquipe);
    }

    @Transactional
    @Override
    public void deleteEquipe(UUID id) {
        if (!equipeRepository.existsById(id)) {
            throw new RuntimeException("Équipe avec l'ID " + id + " non trouvée.");
        }
        equipeRepository.deleteById(id);
    }
}
