package com.example.smartindus.repository;

import com.example.smartindus.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> getUserByFirstName(String username);
    Optional<User> getUserByLastName(String username);
    Optional<User> getUserById(UUID id);
    Optional<User> findByEmail(String email);
    List<User> findByCin(String cin);

}
