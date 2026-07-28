package com.suaessencia.backend.controller;

import com.suaessencia.backend.config.AdminUserStore;
import com.suaessencia.backend.dto.AuthDTO;
import com.suaessencia.backend.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AdminUserStore adminUserStore;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * POST /api/auth/login
     * Body: { "username": "admin", "password": "admin123" }
     * Retorna: { "token": "...", "username": "admin", "expiresIn": 86400000 }
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthDTO.LoginRequest req) {

        // Verifica se o usuário existe
        var encodedPw = adminUserStore.getEncodedPassword(req.getUsername());
        if (encodedPw.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Credenciais inválidas"));
        }

        // Verifica a senha com BCrypt
        if (!passwordEncoder.matches(req.getPassword(), encodedPw.get())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Credenciais inválidas"));
        }

        String token = jwtUtil.gerarToken(req.getUsername());
        return ResponseEntity.ok(new AuthDTO.LoginResponse(
                token,
                req.getUsername(),
                jwtUtil.getExpirationMs()
        ));
    }
}
