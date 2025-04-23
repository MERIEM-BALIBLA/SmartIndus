package com.example.smartindus.service.implementation;

import com.example.smartindus.DTO.Equipe;
import com.example.smartindus.DTO.EquipeDetailDTO;
import com.example.smartindus.DTO.EquipeMapper;
import com.example.smartindus.DTO.User;
import com.example.smartindus.domain.EquipeEntity;
import com.example.smartindus.domain.UserEntity;
import com.example.smartindus.repository.EquipeRepository;
import com.example.smartindus.repository.UserRepository;
import com.example.smartindus.service.interfaces.EquipeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Page<Equipe> getAllEquipes(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EquipeEntity> equipesPage = equipeRepository.findAll(pageable);

        return equipesPage.map(equipeMapper::toDto);
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
        UserEntity responsable = userRepository.findById(equipeDto.getResponsableId())
                .orElseThrow(() -> new RuntimeException("Responsable non trouvé."));
        UserEntity operateur = userRepository.findById(equipeDto.getOperateurId())
                .orElseThrow(() -> new RuntimeException("Opérateur non trouvé."));
        List<UserEntity> techniciens = new ArrayList<>();
        if (equipeDto.getTechnicienIds() != null) {
            techniciens = equipeDto.getTechnicienIds().stream()
                    .map(id -> userRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Technicien avec ID " + id + " non trouvé.")))
                    .collect(Collectors.toList());
        }
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

    @Override
    public Page<EquipeDetailDTO> getEquipesWithDetails(Pageable pageable) {
        Page<EquipeEntity> equipesPage = equipeRepository.findAll(pageable);

        return equipesPage.map(equipe -> {
            EquipeDetailDTO detailDTO = new EquipeDetailDTO();
            detailDTO.setId(equipe.getId());

            // Mapper responsable
            if (equipe.getResponsable() != null) {
                detailDTO.setResponsable(mapUserToDTO(equipe.getResponsable()));
            }

            // Mapper operateur
            if (equipe.getOperateur() != null) {
                detailDTO.setOperateur(mapUserToDTO(equipe.getOperateur()));
            }

            // Mapper techniciens
            List<User> techniciens = equipe.getTechniciens().stream()
                    .map(this::mapUserToDTO)
                    .collect(Collectors.toList());
            detailDTO.setTechniciens(techniciens);

            return detailDTO;
        });
    }

//    @Override
//    public Page<EquipeDetailDTO> getEquipesWithDetails(Pageable pageable) {
//        Page<EquipeEntity> equipesPage = equipeRepository.findAll(pageable);
//
//        return equipesPage.map(equipe -> {
//            EquipeDetailDTO detailDTO = new EquipeDetailDTO();
//            detailDTO.setId(equipe.getId());
//
//            // Mapper responsable, operateur, techniciens...
//
//            return detailDTO;
//        });
//    }

    @Override
    public List<EquipeDetailDTO> getEquipesWithDetails() {
        List<EquipeEntity> equipes = equipeRepository.findAll();
        return equipes.stream().map(equipe -> {
            EquipeDetailDTO detailDTO = new EquipeDetailDTO();
            detailDTO.setId(equipe.getId());

            // Mapper le responsable
            if (equipe.getResponsable() != null) {
                detailDTO.setResponsable(mapUserToDTO(equipe.getResponsable()));
            }

            // Mapper l'opérateur
            if (equipe.getOperateur() != null) {
                detailDTO.setOperateur(mapUserToDTO(equipe.getOperateur()));
            }

            // Mapper les techniciens
            if (equipe.getTechniciens() != null && !equipe.getTechniciens().isEmpty()) {
                List<User> technicienDTOs = equipe.getTechniciens().stream()
                        .map(this::mapUserToDTO)
                        .collect(Collectors.toList());
                detailDTO.setTechniciens(technicienDTOs);
            } else {
                detailDTO.setTechniciens(new ArrayList<>());
            }

            return detailDTO;
        }).collect(Collectors.toList());
    }

    /**
     * Convertit une entité User en DTO
     * @param user L'entité utilisateur à convertir
     * @return L'utilisateur converti en DTO
     */
    private User mapUserToDTO(UserEntity user) {
        if (user == null) return null;

        User userDTO = new User();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setRole(user.getRole());
        // Ajoutez d'autres champs selon vos besoins

        return userDTO;
    }

}
