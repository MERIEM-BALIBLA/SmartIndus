package com.example.smartindus.service.interfaces;

import com.example.smartindus.DTO.User;
import com.example.smartindus.domain.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    User createUser(User User);

    Page<User> findAllUsers(Pageable pageable);

    Optional<User> findUser(UUID id);

    User updateUser(UUID id, User User);

    void deleteUser(UUID id);
}
