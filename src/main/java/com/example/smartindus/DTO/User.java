package com.example.smartindus.DTO;

import com.example.smartindus.domain.enums.Role;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String cin;
    private String phone;
    private Role role;
}
