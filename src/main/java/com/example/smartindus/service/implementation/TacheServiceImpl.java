package com.example.smartindus.service.implementation;

import com.example.smartindus.DTO.Tache;
import com.example.smartindus.DTO.TacheMapper;
import com.example.smartindus.domain.InterventionEntity;
import com.example.smartindus.domain.TacheEntity;
import com.example.smartindus.domain.UserEntity;
import com.example.smartindus.repository.InterventionRepository;
import com.example.smartindus.repository.TacheRepository;
import com.example.smartindus.repository.UserRepository;
import com.example.smartindus.service.interfaces.TacheService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TacheServiceImpl implements TacheService {

    @Autowired
    private TacheRepository tacheRepository;
    @Autowired
    private TacheMapper tacheMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InterventionRepository interventionRepository;

    @Override
    public Page<Tache> findAllTaches(Pageable pageable) {
        return tacheRepository.findAll(pageable).map(tacheMapper::toDto);
    }


    @Override
    public Tache getTacheById(UUID id) {
        TacheEntity tacheEntity = tacheRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipement avec l'ID " + id + " n'existe pas."));
        return tacheMapper.toDto(tacheEntity);
    }

    @Transactional
    @Override
    public Tache createTache(Tache tache) {

        TacheEntity tacheEntity = tacheMapper.toEntity(tache);

        Optional<UserEntity> user = userRepository.findById(UUID.fromString(tache.getUser_id()));
        if (user.isPresent()) {
            tacheEntity.setUser(user.get());
        }
        tacheEntity = tacheRepository.save(tacheEntity);

        return tacheMapper.toDto(tacheEntity);
    }


    @Transactional
    @Override
    public Tache updateTache(UUID id, Tache tache) {
        // Vérifier si la tâche existe
        TacheEntity existingTache = tacheRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tâche avec l'ID " + id + " n'existe pas."));

        // Mettre à jour les champs modifiables
        existingTache.setTitre(tache.getTitre());
        existingTache.setDescription(tache.getDescription());
        existingTache.setDepart(tache.getDepart());
        existingTache.setFin(tache.getFin());
        existingTache.setStatus(tache.getStatus());

        // Mettre à jour l'utilisateur si fourni
        if (tache.getUser_id() != null) {
            Optional<UserEntity> user = userRepository.findById(UUID.fromString(tache.getUser_id()));
            existingTache.setUser(user.orElseThrow(() -> new IllegalArgumentException("Utilisateur avec l'ID " + tache.getUser_id() + " non trouvé.")));
        }

        // Mettre à jour l'intervention si fournie
        if (tache.getIntervention_id() != null) {
            Optional<InterventionEntity> intervention = interventionRepository.findById(UUID.fromString(tache.getIntervention_id()));
            existingTache.setIntervention(intervention.orElseThrow(() -> new IllegalArgumentException("Intervention avec l'ID " + tache.getIntervention_id() + " non trouvée.")));
        }

        // Sauvegarder et retourner la mise à jour
        existingTache = tacheRepository.save(existingTache);
        return tacheMapper.toDto(existingTache);
    }


    @Transactional
    @Override
    public void deleteTache(UUID id) {
        // Vérifier si la tâche existe avant de la supprimer
        TacheEntity tache = tacheRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tâche avec l'ID " + id + " n'existe pas."));
        tacheRepository.delete(tache);
    }

    @Transactional
    @Override
    public Tache updateUserInTache(UUID tacheId, String userId) {
        // 🔹 Vérifier si la tâche existe
        TacheEntity existingTache = tacheRepository.findById(tacheId)
                .orElseThrow(() -> new RuntimeException("Tâche avec l'ID " + tacheId + " n'existe pas."));

        // 🔹 Vérifier si l'utilisateur existe
        UserEntity user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur avec l'ID " + userId + " non trouvé."));

        // 🔹 Mettre à jour l'utilisateur de la tâche
        existingTache.setUser(user);

        // ✅ Sauvegarder avec `saveAndFlush()` pour s'assurer que l'entité est bien mise à jour
        tacheRepository.saveAndFlush(existingTache);

        return tacheMapper.toDto(existingTache);
    }


    @Transactional
    @Override
    public Tache updateInterventionInTache(UUID tacheId, String interventionId) {
        // Vérifier si la tâche existe
        TacheEntity existingTache = tacheRepository.findById(tacheId)
                .orElseThrow(() -> new RuntimeException("Tâche avec l'ID " + tacheId + " n'existe pas."));

        // Vérifier si l'intervention existe
        InterventionEntity intervention = interventionRepository.findById(UUID.fromString(interventionId))
                .orElseThrow(() -> new IllegalArgumentException("Intervention avec l'ID " + interventionId + " non trouvée."));

        // Mettre à jour l'intervention de la tâche
        existingTache.setIntervention(intervention);

        // Sauvegarder la mise à jour et retourner la tâche mise à jour
        existingTache = tacheRepository.save(existingTache);
        return tacheMapper.toDto(existingTache);
    }

//    @Override
//    public List<Tache> getTachesByUserId(String userId) {
//        return tacheMapper.toDtoList(tacheRepository.findByUserId(userId));
//    }
//
//    @Override
//    public List<Tache> getTachesByInterventionId(String interventionId) {
//        return tacheMapper.toDtoList(tacheRepository.findByInterventionId(interventionId));
//    }
}
