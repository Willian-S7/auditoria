package com.wil.auditoria.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.wil.auditoria.dto.request.UsuarioRequest;
import com.wil.auditoria.dto.response.UsuarioResponse;
import com.wil.auditoria.model.Usuario;
import com.wil.auditoria.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service 
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ContextoAuditoriaService contextoAuditoriaService;

    public UsuarioService (UsuarioRepository usuarioRepository, ContextoAuditoriaService contextoAuditoriaService) {
        this.usuarioRepository = usuarioRepository;
        this.contextoAuditoriaService = contextoAuditoriaService;
    }

    public UsuarioResponse toDTO (Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse(
            usuario.getId(), usuario.getNome(), usuario.getLogin(), usuario.isAtivo()
        );
        return response;
    }

    @Transactional 
    public UsuarioResponse novoUsuario (Long usuarioId, UsuarioRequest request) {

        contextoAuditoriaService.definirUsuarioResponsavel(usuarioId);
        
        Usuario usuario = new Usuario();
        usuario.setNome(request.nome());
        usuario.setLogin(request.login());
        usuario.setAtivo(true);
        usuarioRepository.save(usuario);
        return toDTO(usuario);
    }

    public List<UsuarioResponse> listarUsuarios() {
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

    @Transactional 
    public UsuarioResponse alterarUsuario (Long usuarioId, Long id, UsuarioRequest request) {

        contextoAuditoriaService.definirUsuarioResponsavel(usuarioId);

        Usuario usuario = buscarUsuario(id);
        usuario.setNome(request.nome());
        usuario.setLogin(request.login());
        usuarioRepository.save(usuario);
        return toDTO(usuario);
    }

    @Transactional
    public UsuarioResponse statusUsuario(Long usuarioId, Long id) {
        contextoAuditoriaService.definirUsuarioResponsavel(id);
        
        Usuario usuario = buscarUsuario(id);

        usuario.setAtivo(!usuario.isAtivo());
        usuarioRepository.save(usuario);
        return toDTO(usuario);
    }

    @Transactional
    public void deleteUsuario (Long usuarioId, Long id) {

        contextoAuditoriaService.definirUsuarioResponsavel(usuarioId); 

        Usuario usuario = buscarUsuario(id);
        usuarioRepository.delete(usuario);
        
    }
}
