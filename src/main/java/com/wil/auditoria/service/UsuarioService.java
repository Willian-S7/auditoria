package com.wil.auditoria.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.wil.auditoria.dto.request.UsuarioRequest;
import com.wil.auditoria.dto.response.UsuarioResponse;
import com.wil.auditoria.model.Usuario;
import com.wil.auditoria.repository.UsuarioRepository;

@Service 
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService (UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponse toDTO (Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse(
            usuario.getId(), usuario.getNome(), usuario.getLogin(), usuario.isAtivo()
        );
        return response;
    }

    public UsuarioResponse novoUsuario (UsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNome(request.nome());
        usuario.setLogin(request.login());
        usuario.setAtivo(true);
        usuarioRepository.save(usuario);
        return toDTO(usuario);
    }

    public List<UsuarioResponse> listarUsuários() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(usuario -> toDTO(usuario)).toList();
    }

    public Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    public UsuarioResponse usuarioPorId(Long id) {
        Usuario usuario = buscarUsuario(id);
        return toDTO(usuario);
    }

    public UsuarioResponse alterarUsuario (Long id, UsuarioRequest request) {
        Usuario usuario = buscarUsuario(id);
        usuario.setNome(request.nome());
        usuario.setLogin(request.login());
        usuarioRepository.save(usuario);
        return toDTO(usuario);
    }

    public UsuarioResponse statusUsuario(Long id) {
        Usuario usuario = buscarUsuario(id);
        if (usuario.isAtivo()) {
            usuario.setAtivo(false);
        }
        usuario.setAtivo(true);
        usuarioRepository.save(usuario);
        return toDTO(usuario);
    }

    public void deleteUsuario (Long id) {
        Usuario usuario = buscarUsuario(id);
        usuarioRepository.delete(usuario);
    }
}
