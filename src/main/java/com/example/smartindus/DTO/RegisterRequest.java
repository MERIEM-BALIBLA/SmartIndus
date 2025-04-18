package com.example.smartindus.DTO;

import com.example.smartindus.domain.enums.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}