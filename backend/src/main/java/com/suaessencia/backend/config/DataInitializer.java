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

        // Inserir dados de exemplo apenas se o banco estiver vazio
        if (produtoRepo.count() == 0) {
            carregarDadosExemplo();
            log.info("✅ Produtos de exemplo inseridos.");
        }
    }

    private void carregarDadosExemplo() {
        produtoRepo.save(Produto.builder()
            .nome("Oud Al Layl").categoria("Árabe")
            .preco(new BigDecimal("189.90")).precoAnt(null)
            .descricao("Notas de oud, âmbar e sândalo envelhecido. Uma fragrância intensa e duradoura que evoca o exotismo do Oriente.")
            .imagemUrl("https://images.unsplash.com/photo-1541643600914-78b084683702?w=500&q=80")
            .destaque(true).promocao(false).disponivel(true).build());

        produtoRepo.save(Produto.builder()
            .nome("Rose Mystique").categoria("Importados")
            .preco(new BigDecimal("245.00")).precoAnt(new BigDecimal("290.00"))
            .descricao("Rosas búlgaras, jasmim e musk branco. Refinado e elegante, perfeito para ocasiões especiais.")
            .imagemUrl("https://images.unsplash.com/photo-1592945403244-b3fbafd7f539?w=500&q=80")
            .destaque(true).promocao(true).disponivel(true).build());

        produtoRepo.save(Produto.builder()
            .nome("Velvet Noir").categoria("Importados")
            .preco(new BigDecimal("320.00"))
            .descricao("Bergamota italiana, madeira de cedro e baunilha de Madagascar. Sofisticação em cada gota.")
            .imagemUrl("https://images.unsplash.com/photo-1556228578-8c89e6adf883?w=500&q=80")
            .destaque(false).promocao(false).disponivel(true).build());

        produtoRepo.save(Produto.builder()
            .nome("Splash Floral").categoria("Body Splash")
            .preco(new BigDecimal("49.90")).precoAnt(new BigDecimal("65.00"))
            .descricao("Leveza floral com notas de peônia e framboesa. Refrescante para o dia a dia.")
            .imagemUrl("https://images.unsplash.com/photo-1587017539504-67cfbddac569?w=500&q=80")
            .destaque(false).promocao(true).disponivel(true).build());

        produtoRepo.save(Produto.builder()
            .nome("Ambar Royal").categoria("Árabe")
            .preco(new BigDecimal("210.00"))
            .descricao("Âmbar dourado, almíscar e especiarias orientais. Envolvente e sensual.")
            .imagemUrl("https://images.unsplash.com/photo-1523293182086-7651a899d37f?w=500&q=80")
            .destaque(true).promocao(false).disponivel(true).build());
    }
}
