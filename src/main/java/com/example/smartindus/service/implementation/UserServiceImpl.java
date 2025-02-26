package com.example.smartindus.service.implementation;

import com.example.smartindus.domain.User;
import com.example.smartindus.repository.UserRepository;
import com.example.smartindus.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User save(User User) {
        return repository.save(User);
    }

    @Override
    public Page<User> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    @Override
    public Optional<User> findUser(UUID id) {
        return repository.findById(id);
    }

    @Override
    public User update(UUID id, User User){
        Optional<User> existingUser = findUser(id);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();

            updatedUser.setFirstName(User.getFirstName());
            updatedUser.setEmail(User.getEmail());
            updatedUser.setLastName(User.getLastName());
            updatedUser.setCin(User.getCin());
            updatedUser.setRole(User.getRole());
            updatedUser.setPhone(User.getPhone());

            return repository.save(updatedUser);
        } else {
            throw new RuntimeException("User avec l'ID " + id + " n'existe pas.");
        }
    }

    @Override
    public void delete(UUID id){
        Optional<User> existingUser = findUser(id);
        if(existingUser.isPresent()){
            User User = existingUser.get();
            repository.delete(User);
        }
    }
}
