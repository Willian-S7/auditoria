package com.wil.auditoria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wil.auditoria.dto.request.ProdutoRequest;
import com.wil.auditoria.dto.response.ProdutoResponse;
import com.wil.auditoria.model.Categoria;
import com.wil.auditoria.model.Produto;
import com.wil.auditoria.repository.ProdutoRepository;

@ExtendWith (MockitoExtension.class)
public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private ProdutoService produtoService;

    @Test 
    void criarProdutoTest() {
        ProdutoRequest request = new ProdutoRequest(1L,"telefone", "descricao", new BigDecimal(10.0), 10, 5L);
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("peças");
        categoria.setAtivo(true);

        when (categoriaService.buscarCategoria(request.categoriaId())).thenReturn(categoria);

        when(produtoRepository.save(any(Produto.class))).thenAnswer(usu -> usu.getArgument(0));

        ProdutoResponse response = produtoService.novoProduto(request);

        assertEquals("telefone", response.nome());
    }

    @Test
    void alterarProduto() {
        Long id = 1L;
        ProdutoRequest request = new ProdutoRequest(2L, "lucas", "descri", new BigDecimal(12), 20, 5L);
        Produto produto = new Produto();
        

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("peças");
        categoria.setAtivo(true);

        when (categoriaService.buscarCategoria(request.categoriaId())).thenReturn(categoria);

        when(produtoRepository.findById(id)).thenReturn(Optional.of(produto));

        when(produtoRepository.save(any(Produto.class))).thenAnswer(prod -> prod.getArgument(0));

        ProdutoResponse response = produtoService.alterarProduto(id, request);

        assertEquals("lucas", response.nome());
    }
    
}
