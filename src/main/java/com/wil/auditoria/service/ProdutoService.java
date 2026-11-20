package com.wil.auditoria.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.wil.auditoria.dto.request.ProdutoRequest;
import com.wil.auditoria.dto.response.ProdutoResponse;
import com.wil.auditoria.model.Categoria;
import com.wil.auditoria.model.Produto;
import com.wil.auditoria.repository.ProdutoRepository;

import jakarta.transaction.Transactional;

@Service 
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaService categoriaService;
    private final ContextoAuditoriaService contextoAuditoriaService;

    public ProdutoService (ProdutoRepository produtoRepository, CategoriaService categoriaService, ContextoAuditoriaService contextoAuditoriaService) {
        this.produtoRepository = produtoRepository;
        this.categoriaService = categoriaService;
        this.contextoAuditoriaService = contextoAuditoriaService;
    }

    public ProdutoResponse toDTO (Produto produto) {
        ProdutoResponse response = new ProdutoResponse(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getQtdEstoque(), produto.isAtivo(), produto.getCategoria().getId());
        return response;
    }

    @Transactional 
    public ProdutoResponse novoProduto(Long usuarioId, ProdutoRequest request) {
        contextoAuditoriaService.definirUsuarioResponsavel(usuarioId);
        
        Produto produto = new Produto();
        Categoria categoria = categoriaService.buscarCategoria(request.categoriaId());

        produto.setNome(request.nome());
        produto.setDescricao(request.descricao());
        produto.setPreco(request.preco());
        produto.setQtdEstoque(request.qtdEstoque());
        produto.setAtivo(true);
        produto.setCategoria(categoria   
        );
        produtoRepository.save(produto);
        
        return toDTO(produto);
    }

    public List<ProdutoResponse> listarProdutos() {
        return produtoRepository.findAll().stream().map(produto -> toDTO(produto)).toList();
    }

    public Produto buscarProduto(Long id) {
        return produtoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    public ProdutoResponse produtoResponsePorId(Long id) {
        Produto produto = buscarProduto(id);
        return toDTO(produto);
    }

    @Transactional 
    public ProdutoResponse alterarProduto(Long usuarioId, Long id, ProdutoRequest request) {

        contextoAuditoriaService.definirUsuarioResponsavel(usuarioId);
        
        Produto produto = buscarProduto(id);
        Categoria categoria = categoriaService.buscarCategoria(request.categoriaId());

        produto.setNome(request.nome());
        produto.setDescricao(request.descricao());
        produto.setPreco(request.preco());
        produto.setQtdEstoque(request.qtdEstoque());
        produto.setCategoria(categoria);
        produtoRepository.save(produto);
        
        return toDTO(produto);
    }

    @Transactional 
    public ProdutoResponse statusProduto(Long usuarioId, Long id){
        Produto produto = buscarProduto(id);
        if (produto.isAtivo()){
            produto.setAtivo(false);
        } else {
        produto.setAtivo(true);
        }
        produtoRepository.save(produto);
        contextoAuditoriaService.definirUsuarioResponsavel(id);
        return toDTO(produto);
        
    }

    @Transactional
    public void deletarProduto(Long usuarioId, Long id){
        Produto produto = buscarProduto(id);
        produtoRepository.delete(produto);
        contextoAuditoriaService.definirUsuarioResponsavel(id);
    }
}
