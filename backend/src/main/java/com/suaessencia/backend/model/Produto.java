package com.suaessencia.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 150, message = "Nome deve ter no máximo 150 caracteres")
    @Column(nullable = false, length = 150)
    private String nome;

    @NotBlank(message = "Categoria é obrigatória")
    @Column(nullable = false, length = 50)
    private String categoria;

    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    // Preço antigo para exibir promoção (pode ser nulo)
    @Column(name = "preco_ant", precision = 10, scale = 2)
    private BigDecimal precoAnt;

    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    @Column(length = 1000)
    private String descricao;

    // URL da imagem (Cloudinary, Railway Volume, etc.)
    @Column(name = "imagem_url", length = 500)
    private String imagemUrl;

    @Column(name = "imagem_url2", length = 500)
    private String imagemUrl2;

    @Column(name = "imagem_url3", length = 500)
    private String imagemUrl3;

    @Column(nullable = false)
    @Builder.Default
    private Boolean destaque = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean promocao = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean disponivel = true;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @Column(length = 20)
    private String genero;

    @PrePersist
    protected void onCreate() {
        criadoEm = LocalDateTime.now();
        atualizadoEm = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        atualizadoEm = LocalDateTime.now();
    }
}
