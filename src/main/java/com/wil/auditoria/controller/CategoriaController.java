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

import com.wil.auditoria.dto.request.CategoriaRequest;
import com.wil.auditoria.dto.response.CategoriaResponse;
import com.wil.auditoria.service.CategoriaService;

@RestController 
@RequestMapping ("/categoria")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController (CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> novaCategoria(@RequestBody CategoriaRequest request) {
        CategoriaResponse categoria = categoriaService.novaCategoria(request);
        return ResponseEntity.ok(categoria);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listarCategorias(){
        List<CategoriaResponse> categorias = categoriaService.listarCategorias();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> buscarCategoria(@PathVariable Long id) {
        CategoriaResponse categoria = categoriaService.CategoriaResponsePorId(id);
        return ResponseEntity.ok(categoria);
    }

    @PatchMapping ("/{id}")
    public ResponseEntity<CategoriaResponse> alterarStatusCategoria(@PathVariable Long id) {
        CategoriaResponse categoria = categoriaService.statusCategoria(id);
        return ResponseEntity.ok(categoria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria (@PathVariable Long id) {
        categoriaService.deleteCategoria(id);
        return ResponseEntity.noContent().build();
    }

}
