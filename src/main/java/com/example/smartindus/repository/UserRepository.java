package com.example.smartindus.repository;

import com.example.smartindus.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> getUserByFirstName(String username);
    Optional<UserEntity> getUserByLastName(String username);
    Optional<UserEntity> getUserById(UUID id);
    Optional<UserEntity> findByEmail(String email);
    List<UserEntity> findByCin(String cin);

}
