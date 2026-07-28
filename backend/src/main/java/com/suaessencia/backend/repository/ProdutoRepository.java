package com.suaessencia.backend.repository;

import com.suaessencia.backend.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Todos os produtos disponíveis (catálogo público)
    List<Produto> findByDisponivelTrueOrderByDestaqueDescCriadoEmDesc();

    // Por categoria
    List<Produto> findByCategoriaAndDisponivelTrueOrderByNomeAsc(String categoria);

    // Destaques
    List<Produto> findByDestaqueAndDisponivelTrue(boolean destaque);

    // Promoções
    List<Produto> findByPromocaoAndDisponivelTrue(boolean promocao);

    // Busca por nome ou descrição (para o admin)
    @Query("""
        SELECT p FROM Produto p
        WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :termo, '%'))
           OR LOWER(p.descricao) LIKE LOWER(CONCAT('%', :termo, '%'))
        ORDER BY p.criadoEm DESC
    """)
    List<Produto> buscarPorTermo(@Param("termo") String termo);
}
