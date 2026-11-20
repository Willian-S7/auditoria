package com.wil.auditoria.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wil.auditoria.dto.request.FiltroRegistroRequest;
import com.wil.auditoria.dto.response.RegistroAuditoriaDTO;
import com.wil.auditoria.service.RegistroAuditoriaService;

@RestController 
@RequestMapping ("/registro-auditoria")
public class RegistroAuditoriaController {

    private final RegistroAuditoriaService registroAuditoriaService;

    public RegistroAuditoriaController (RegistroAuditoriaService registroAuditoriaService) {
        this.registroAuditoriaService = registroAuditoriaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroAuditoriaDTO> buscarRegistroPorId(@PathVariable Long id) {
        RegistroAuditoriaDTO registro = registroAuditoriaService.buscarRegistro(id);
        return ResponseEntity.ok(registro);
    }

    @GetMapping
    public ResponseEntity<List<RegistroAuditoriaDTO>> filtroRegistros(@ModelAttribute FiltroRegistroRequest request) {
        List<RegistroAuditoriaDTO> registros = registroAuditoriaService.filtroRegistros(request);
        return ResponseEntity.ok(registros);
    }
}
