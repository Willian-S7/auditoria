package com.wil.auditoria.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wil.auditoria.dto.request.ProdutoRequest;
import com.wil.auditoria.dto.response.ProdutoResponse;
import com.wil.auditoria.service.ProdutoService;

@RestController 
@RequestMapping ("/produto")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController (ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> novoProduto (@RequestBody ProdutoRequest request) {
        ProdutoResponse produto = produtoService.novoProduto(request);
        return ResponseEntity.ok(produto);
    }

    @GetMapping 
    public ResponseEntity<List<ProdutoResponse>> listarProdutos() {
        List<ProdutoResponse> produtos = produtoService.listarProdutos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscarProduto(@PathVariable Long id) {
        ProdutoResponse produto = produtoService.produtoResponsePorId(id);
        return ResponseEntity.ok(produto);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ProdutoResponse> alterarProduto (@PathVariable Long id, @RequestBody ProdutoRequest request) {
        ProdutoResponse produto = produtoService.alterarProduto(id, request);
        return ResponseEntity.ok(produto);
    }

    @PatchMapping ("/{id}")
    public ResponseEntity<ProdutoResponse> AlterarStatusProduto(Long id) {
        ProdutoResponse produto = produtoService.statusProduto(id);
        return ResponseEntity.ok(produto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }
}
