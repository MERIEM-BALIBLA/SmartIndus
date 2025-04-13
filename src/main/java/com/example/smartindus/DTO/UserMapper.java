package com.example.smartindus.DTO;

import com.example.smartindus.domain.UserEntity;
import com.example.smartindus.domain.enums.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toDTO(UserEntity entity);
    UserEntity toEntity(User dto);

    // Méthode explicite pour convertir le Role
    default String roleToString(Role role) {
        return role != null ? role.name() : null;
    }
}