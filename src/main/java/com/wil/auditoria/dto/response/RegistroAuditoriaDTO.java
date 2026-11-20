package com.wil.auditoria.dto.response;

import java.time.Instant;
import java.util.Map;

import com.wil.auditoria.model.OperacaoAuditoria;
import com.wil.auditoria.model.TipoEntidade;

public record RegistroAuditoriaDTO(
    Long id,
    TipoEntidade entidade,
    Long registroId,
    OperacaoAuditoria operacao,
    String usuarioResponsavel,
    Instant dataAlteracao,
    Map<String, Object> dadosAntes,
    Map<String, Object> dadosDepois
) {

}
