package com.example.smartindus.DTO;

import com.example.smartindus.domain.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toDTO(UserEntity entity);
    UserEntity toEntity(User dto);
}
