package com.suaessencia.backend.service;

import com.suaessencia.backend.dto.ProdutoDTO;
import com.suaessencia.backend.model.Produto;
import com.suaessencia.backend.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repo;

    // ── LEITURA (público) ─────────────────────────────────────────

    public List<Produto> listarDisponiveis() {
        return repo.findByDisponivelTrueOrderByDestaqueDescCriadoEmDesc();
    }

    public Produto buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + id));
    }

    // ── LEITURA (admin) ───────────────────────────────────────────

    public List<Produto> listarTodos() {
        return repo.findAll();
    }

    public List<Produto> buscarPorTermo(String termo) {
        return repo.buscarPorTermo(termo);
    }

    // ── ESCRITA (admin) ───────────────────────────────────────────

    @Transactional
    public Produto criar(ProdutoDTO dto) {
        Produto p = dtoToEntity(new Produto(), dto);
        return repo.save(p);
    }

    @Transactional
    public Produto atualizar(Long id, ProdutoDTO dto) {
        Produto p = buscarPorId(id);
        dtoToEntity(p, dto);
        return repo.save(p);
    }

    @Transactional
    public void deletar(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Produto não encontrado: " + id);
        }
        repo.deleteById(id);
    }

    @Transactional
    public Produto alternarDisponibilidade(Long id) {
        Produto p = buscarPorId(id);
        p.setDisponivel(!p.getDisponivel());
        return repo.save(p);
    }

    // ── MAPEAMENTO ────────────────────────────────────────────────

    private Produto dtoToEntity(Produto p, ProdutoDTO dto) {
        p.setNome(dto.getNome());
        p.setCategoria(dto.getCategoria());
        p.setPreco(dto.getPreco());
        p.setPrecoAnt(dto.getPrecoAnt());
        p.setDescricao(dto.getDescricao());
        p.setImagemUrl(dto.getImagemUrl());
        p.setImagemUrl2(dto.getImagemUrl2());
        p.setImagemUrl3(dto.getImagemUrl3());
        p.setGenero(dto.getGenero());
        p.setDestaque(dto.getDestaque() != null ? dto.getDestaque() : false);
        p.setPromocao(dto.getPromocao() != null ? dto.getPromocao() : false);
        p.setDisponivel(dto.getDisponivel() != null ? dto.getDisponivel() : true);
        return p;
    }
}
