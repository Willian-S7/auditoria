package com.wil.auditoria.dto.response;

public record UsuarioResponse(
    Long id,
    String nome,
    String login,
    boolean ativo
) {

}
