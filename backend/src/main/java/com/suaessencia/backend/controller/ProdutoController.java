package com.suaessencia.backend.controller;

import com.suaessencia.backend.dto.ProdutoDTO;
import com.suaessencia.backend.model.Produto;
import com.suaessencia.backend.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService service;

    // ── ROTAS PÚBLICAS ────────────────────────────────────────────

    /**
     * GET /api/produtos
     * Lista todos os produtos disponíveis (para o catálogo público)
     */
    @GetMapping
    public ResponseEntity<List<Produto>> listar() {
        return ResponseEntity.ok(service.listarDisponiveis());
    }

    /**
     * GET /api/produtos/{id}
     * Detalhe de um produto específico
     */
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // ── ROTAS ADMIN (requerem JWT) ────────────────────────────────

    /**
     * GET /api/produtos/admin/todos
     * Lista TODOS os produtos, incluindo indisponíveis (para o dashboard)
     */
    @GetMapping("/admin/todos")
    public ResponseEntity<List<Produto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    /**
     * GET /api/produtos/admin/buscar?termo=oud
     * Busca por nome ou descrição (dashboard)
     */
    @GetMapping("/admin/buscar")
    public ResponseEntity<List<Produto>> buscarPorTermo(@RequestParam String termo) {
        return ResponseEntity.ok(service.buscarPorTermo(termo));
    }

    /**
     * POST /api/produtos
     * Cria um novo produto
     */
    @PostMapping
    public ResponseEntity<Produto> criar(@Valid @RequestBody ProdutoDTO dto) {
        Produto criado = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    /**
     * PUT /api/produtos/{id}
     * Edita um produto existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    /**
     * PATCH /api/produtos/{id}/disponibilidade
     * Alterna disponível/indisponível sem precisar editar tudo
     */
    @PatchMapping("/{id}/disponibilidade")
    public ResponseEntity<Produto> alternarDisponibilidade(@PathVariable Long id) {
        return ResponseEntity.ok(service.alternarDisponibilidade(id));
    }

    /**
     * DELETE /api/produtos/{id}
     * Remove um produto
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.ok(Map.of("message", "Produto removido com sucesso"));
    }
}
