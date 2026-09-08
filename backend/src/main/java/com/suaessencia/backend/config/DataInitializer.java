package com.suaessencia.backend.config;

import com.suaessencia.backend.model.Produto;
import com.suaessencia.backend.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Executa na inicialização para:
 * 1. Criar o usuário admin (armazenado em memória — substitua por banco se quiser multiusuário)
 * 2. Inserir produtos de exemplo se o banco estiver vazio
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final ProdutoRepository produtoRepo;
    private final AdminUserStore adminUserStore;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        // Registrar credenciais do admin
        adminUserStore.register(adminUsername, passwordEncoder.encode(adminPassword));
        log.info("✅ Admin registrado: {}", adminUsername);

    }
}
