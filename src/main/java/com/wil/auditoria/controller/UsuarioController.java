package com.wil.auditoria.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wil.auditoria.dto.request.UsuarioRequest;
import com.wil.auditoria.dto.response.UsuarioResponse;
import com.wil.auditoria.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController (UsuarioService usuarioService) {
        
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> novoUsuario(@RequestBody @Valid UsuarioRequest request) {
        UsuarioResponse usuario = usuarioService.novoUsuario(request);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listUsuarios() {
        List<UsuarioResponse> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarUsuarios(@PathVariable Long id) {
        UsuarioResponse usuario = usuarioService.usuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> alterarUsuario(@PathVariable Long id,  @RequestBody UsuarioRequest request) {
        UsuarioResponse usuario = usuarioService.alterarUsuario(id, request);
        return ResponseEntity.ok(usuario);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResponse> alterarStatusUsuario(@PathVariable Long id) {
        UsuarioResponse usuario = usuarioService.statusUsuario(id);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
