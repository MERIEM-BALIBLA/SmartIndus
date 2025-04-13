package com.example.smartindus.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthRequest {
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;
}