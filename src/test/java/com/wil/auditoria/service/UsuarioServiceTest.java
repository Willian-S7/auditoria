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

    @Mock
    private ContextoAuditoriaService contextoAuditoriaService;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test 
    void criarUsuarioTest (){
        Long usuarioId = 6L;
        UsuarioRequest request = new UsuarioRequest(1L,"João","2L");
        
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(usu -> usu.getArgument(0));

        UsuarioResponse usuario = usuarioService.novoUsuario(usuarioId, request);

        assertEquals("João", usuario.nome());
    }

    @Test
    void alterarUsuarioTest() {
        Long usuarioId = 6L;
        Long id = 1L;
        UsuarioRequest request = new UsuarioRequest(1L, "Pedro", "2L");
        Usuario usuario = new Usuario();

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(usu -> usu.getArgument(0));

        UsuarioResponse response = usuarioService.alterarUsuario(usuarioId, id, request);

        assertEquals("Pedro", response.nome());
    }

    @Test
    void alterarStatusTest(){
        Long usuarioId = 6L;
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setAtivo(true);
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(usu -> usu.getArgument(0));
        
        UsuarioResponse response = usuarioService.statusUsuario(usuarioId, id);

        assertFalse(response.ativo());
    }

}
