package com.example.smartindus.service.interfaces;

import com.example.smartindus.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface UserService {
    User save(User User);

    Page<User> findAll(Pageable pageable);

    Optional<User> findUser(UUID id);

    User update(UUID id, User User);

    void delete(UUID id);
}
