package com.wil.auditoria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wil.auditoria.dto.request.UsuarioRequest;
import com.wil.auditoria.dto.response.UsuarioResponse;
import com.wil.auditoria.model.Usuario;
import com.wil.auditoria.repository.UsuarioRepository;

@ExtendWith (MockitoExtension.class)
public class UsuarioServiceTest {
    @Mock 
    private UsuarioRepository usuarioRepository;
    @InjectMocks
    private UsuarioService usuarioService;

    @Test 
    void criarUsuarioTest (){
        UsuarioRequest request = new UsuarioRequest(1L,"João","2L");
        
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(usu -> usu.getArgument(0));

        UsuarioResponse usuario = usuarioService.novoUsuario(request);

        assertEquals("João", usuario.nome());
    }

    @Test
    void alterarUsuarioTest() {
        Long id = 1L;
        UsuarioRequest request = new UsuarioRequest(1L, "Pedro", "2L");
        Usuario usuario = new Usuario();

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(usu -> usu.getArgument(0));

        UsuarioResponse response = usuarioService.alterarUsuario(id, request);

        assertEquals("Pedro", response.nome());
    }

    @Test
    void alterarStatusTest(){
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setAtivo(true);
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(usu -> usu.getArgument(0));
        
        UsuarioResponse response = usuarioService.statusUsuario(id);

        assertFalse(response.ativo());
    }

}
