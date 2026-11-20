package com.wil.auditoria.specifications;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.data.jpa.domain.Specification;

import com.wil.auditoria.model.OperacaoAuditoria;
import com.wil.auditoria.model.RegistroAuditoria;
import com.wil.auditoria.model.TipoEntidade;

public class RegistroSpecification {

    public static Specification<RegistroAuditoria> porEntidade(TipoEntidade entidade) {
        return (root, query, cb) -> cb.equal(root.get("entidade"), entidade);
    }

    public static Specification<RegistroAuditoria> porRegistroId(Long registroId) {
        return (root, query, cb) -> cb.equal(root.get("registroId"), registroId);
    }

    public static Specification<RegistroAuditoria> porOperacao(OperacaoAuditoria operacao) {
        return (root, query, cb) -> cb.equal(root.get("operacao"), operacao);
    }

    public static Specification<RegistroAuditoria> porUsuario(String usuarioResponsavel) {
        return (root, query, cb) -> cb.equal(root.get("usuarioResponsavel"), usuarioResponsavel);
    }

    public static Specification<RegistroAuditoria> apartirDe(LocalDate dataInicio) {
        Instant data = dataInicio.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("dataAlteracao"), data);
    }

    public static Specification<RegistroAuditoria> ateData(LocalDate dataFim) {
        Instant data = dataFim.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
        return (root, query, cb) -> cb.lessThan(root.get("dataAlteracao"), data);
    }    
}
