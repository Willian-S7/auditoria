package com.wil.auditoria.dto.request;

import java.time.LocalDate;

import com.wil.auditoria.model.OperacaoAuditoria;
import com.wil.auditoria.model.TipoEntidade;

public record FiltroRegistroRequest(
    TipoEntidade entidade,
    Long registroId,
    OperacaoAuditoria operacao,
    String usuarioResponsavel,
    LocalDate dataInicio,
    LocalDate dataFim
) {

}
