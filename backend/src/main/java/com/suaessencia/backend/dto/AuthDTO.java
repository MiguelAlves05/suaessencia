package com.suaessencia.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class AuthDTO {

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class LoginRequest {
        @NotBlank(message = "Username é obrigatório")
        private String username;

        @NotBlank(message = "Senha é obrigatória")
        private String password;
    }

    @Getter @AllArgsConstructor
    public static class LoginResponse {
        private String token;
        private String username;
        private long expiresIn;
    }
}
