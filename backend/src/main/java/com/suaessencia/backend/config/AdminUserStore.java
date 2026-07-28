package com.suaessencia.backend.config;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Armazena credenciais de admin em memória.
 * Para um sistema real com múltiplos usuários,
 * substitua por uma entidade JPA + tabela "usuarios".
 */
@Component
public class AdminUserStore {

    // username → senha criptografada com BCrypt
    private final Map<String, String> users = new HashMap<>();

    public void register(String username, String encodedPassword) {
        users.put(username, encodedPassword);
    }

    public Optional<String> getEncodedPassword(String username) {
        return Optional.ofNullable(users.get(username));
    }

    public boolean exists(String username) {
        return users.containsKey(username);
    }
}
