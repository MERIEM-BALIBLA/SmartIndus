package com.example.smartindus.service.implementation;

import com.example.smartindus.DTO.User;
import com.example.smartindus.DTO.UserMapper;
import com.example.smartindus.domain.UserEntity;
import com.example.smartindus.exception.BusinessException;
import com.example.smartindus.repository.UserRepository;
import com.example.smartindus.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private UserMapper userMapper;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private static final Pattern PHONE_PATTERN = Pattern.compile("^0[5-7][0-9]{8}$");

    private static final Pattern CIN_PATTERN = Pattern.compile("^[A-Z]{1,2}[0-9]{5,6}$");

    @Override
    public User createUser(User user) {
        validateUser(user);

        if (emailExists(user.getEmail())) {
            throw new BusinessException("L'email " + user.getEmail() + " est déjà utilisé.", "EMAIL_ALREADY_EXISTS");
        }

        if (cinExists(user.getCin())) {
            throw new IllegalArgumentException("Le CIN " + user.getCin() + " est déjà utilisé.");
        }

        UserEntity userEntity = userMapper.toEntity(user);
        return userMapper.toDTO(repository.save(userEntity));
    }

    @Override
    public Page<User> findAllUsers(Pageable pageable) {
        return repository.findAll(pageable).map(userMapper::toDTO);
    }

    @Override
    public Optional<User> findUser(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null.");
        }
        return repository.findById(id).map(userMapper::toDTO);
    }

    @Override
    public User updateUser(UUID id, User user) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null.");
        }

        validateUser(user);

        Optional<UserEntity> existingUser = repository.findById(id);
        if (existingUser.isPresent()) {
            UserEntity currentUser = existingUser.get();

            if (!currentUser.getEmail().equals(user.getEmail()) && emailExists(user.getEmail())) {
                throw new IllegalArgumentException("L'email " + user.getEmail() + " est déjà utilisé.");
            }

            if (!currentUser.getCin().equals(user.getCin()) && cinExists(user.getCin())) {
                throw new IllegalArgumentException("Le CIN " + user.getCin() + " est déjà utilisé.");
            }

            UserEntity updatedUser = existingUser.get();

            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setCin(user.getCin());
            updatedUser.setRole(user.getRole());
            updatedUser.setPhone(user.getPhone());

            return userMapper.toDTO(repository.save(updatedUser));
        } else {
            throw new RuntimeException("User avec l'ID " + id + " n'existe pas.");
        }
    }

    @Override
    public void deleteUser(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null.");
        }

        if (!repository.existsById(id)) {
            throw new RuntimeException("User avec l'ID " + id + " n'existe pas.");
        }

        repository.deleteById(id);
    }

    /**
     * Valide les données d'un utilisateur
     * @param user l'utilisateur à valider
     * @throws IllegalArgumentException si les données sont invalides
     */
    private void validateUser(User user) {

        if (user == null) {
            throw new IllegalArgumentException("L'utilisateur ne peut pas être null.");
        }

        // Validation des champs obligatoires
        if (!StringUtils.hasText(user.getFirstName())) {
            throw new IllegalArgumentException("Le prénom est obligatoire.");
        }

        if (!StringUtils.hasText(user.getLastName())) {
            throw new IllegalArgumentException("Le nom est obligatoire.");
        }

        if (!StringUtils.hasText(user.getEmail())) {
            throw new IllegalArgumentException("L'email est obligatoire.");
        }

        if (!StringUtils.hasText(user.getCin())) {
            throw new IllegalArgumentException("Le CIN est obligatoire.");
        }

        if (user.getRole() == null) {
            throw new IllegalArgumentException("Le rôle est obligatoire.");
        }

        if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw new IllegalArgumentException("Le format de l'email est invalide.");
        }

        if (StringUtils.hasText(user.getPhone()) && !PHONE_PATTERN.matcher(user.getPhone()).matches()) {
            throw new IllegalArgumentException("Le format du numéro de téléphone est invalide (format marocain attendu).");
        }

        if (!CIN_PATTERN.matcher(user.getCin()).matches()) {
            throw new IllegalArgumentException("Le format du CIN est invalide.");
        }
    }

    /**
     * Vérifie si un email existe déjà
     * @param email l'email à vérifier
     * @return true si l'email existe déjà, false sinon
     */
    private boolean emailExists(String email) {
        return repository.findAll().stream()
                .anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }

    /**
     * Vérifie si un CIN existe déjà
     * @param cin le CIN à vérifier
     * @return true si le CIN existe déjà, false sinon
     */
    private boolean cinExists(String cin) {
        return repository.findAll().stream()
                .anyMatch(user -> user.getCin().equalsIgnoreCase(cin));
    }
}