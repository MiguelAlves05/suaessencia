package com.suaessencia.backend.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * DTO usado tanto para criação quanto para edição de produto.
 * Separa a camada de transporte da entidade JPA.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 150)
    private String nome;

    @NotBlank(message = "Categoria é obrigatória")
    private String categoria;

    private String genero;

    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    private BigDecimal preco;

    private BigDecimal precoAnt;

    @Size(max = 1000)
    private String descricao;

    private String imagemUrl;

    private String imagemUrl2;

    private String imagemUrl3;

    @Builder.Default
    private Boolean destaque = false;

    @Builder.Default
    private Boolean promocao = false;

    @Builder.Default
    private Boolean disponivel = true;
}
