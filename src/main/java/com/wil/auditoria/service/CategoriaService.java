package com.wil.auditoria.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.wil.auditoria.dto.request.CategoriaRequest;
import com.wil.auditoria.dto.response.CategoriaResponse;
import com.wil.auditoria.model.Categoria;
import com.wil.auditoria.repository.CategoriaRepository;

@Service 
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    
    public CategoriaService (CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public CategoriaResponse toDTO(Categoria categoria) {
        CategoriaResponse response = new CategoriaResponse(
            categoria.getId(), categoria.getNome(), categoria.isAtivo()
        );
        return response;
    }

    public CategoriaResponse novaCategoria(CategoriaRequest request) {
        Categoria categoria = new Categoria();
        categoria.setNome(request.nome());
        categoria.setAtivo(true);
        categoriaRepository.save(categoria);
        return toDTO(categoria);
    }

    public List<CategoriaResponse> listarCategorias() {
        return categoriaRepository.findAll().stream()
            .map(categoria -> toDTO(categoria)).toList();
    }

    public Categoria buscarCategoria(Long id) {
        return categoriaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encotrada"));
    }

    public CategoriaResponse CategoriaResponsePorId(Long id) {
        Categoria categoria = buscarCategoria(id);
        return toDTO(categoria);
    }

    public CategoriaResponse statusCategoria(Long id) {
        Categoria categoria = buscarCategoria(id);
        if (categoria.isAtivo()) {
            categoria.setAtivo(false);
        } else {
        categoria.setAtivo(true);
        }
        categoriaRepository.save(categoria);
        return toDTO(categoria);
    }

    public void deleteCategoria (Long id) {
        Categoria categoria = buscarCategoria(id);
        categoriaRepository.delete(categoria);
    }
}
