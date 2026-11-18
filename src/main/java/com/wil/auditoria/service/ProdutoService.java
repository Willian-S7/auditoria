package com.wil.auditoria.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wil.auditoria.dto.request.ProdutoRequest;
import com.wil.auditoria.dto.response.ProdutoResponse;
import com.wil.auditoria.model.Categoria;
import com.wil.auditoria.model.Produto;
import com.wil.auditoria.repository.ProdutoRepository;

@Service 
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaService categoriaService;

    public ProdutoService (ProdutoRepository produtoRepository, CategoriaService categoriaService) {
        this.produtoRepository = produtoRepository;
        this.categoriaService = categoriaService;
    }

    public ProdutoResponse toDTO (Produto produto) {
        ProdutoResponse response = new ProdutoResponse(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getQtdEstoque(), produto.isAtivo(), produto.getCategoria().getId());
        return response;
    }

    public ProdutoResponse novoProduto(ProdutoRequest request) {
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

}
