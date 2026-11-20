package com.wil.auditoria.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.wil.auditoria.model.Usuario;
import com.wil.auditoria.repository.UsuarioRepository;

import jakarta.persistence.EntityManager;

@Service 
public class ContextoAuditoriaService {

    private final UsuarioRepository usuarioRepository;
    private final EntityManager entityManager;

    public ContextoAuditoriaService (UsuarioRepository usuarioRepository, EntityManager entityManager) {
        this.usuarioRepository = usuarioRepository;
        this.entityManager = entityManager;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void definirUsuarioResponsavel(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado"));

        if (!usuario.isAtivo()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuario inativo");
        }

        entityManager.createNativeQuery("SELECT set_config('app.usuario', :login, true)").setParameter("login", usuario.getLogin()).getSingleResult();
    }
}
