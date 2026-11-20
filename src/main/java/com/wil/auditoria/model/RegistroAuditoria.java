package com.wil.auditoria.model;

import java.time.Instant;
import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table (name = "registro_auditoria")
public class RegistroAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated (EnumType.STRING)
    private TipoEntidade entidade;

    @Column (name = "registro_id")
    private Long registroId;

    @Enumerated (EnumType.STRING)
    private OperacaoAuditoria operacao;

    private String usuarioResponsavel;

    private Instant dataAlteracao;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_antes", columnDefinition = "jsonb")
    private Map<String, Object> dadosAntes;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_depois", columnDefinition = "jsonb")
    private Map<String, Object> dadosDepois;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoEntidade getEntidade() {
        return entidade;
    }

    public void setEntidade(TipoEntidade entidade) {
        this.entidade = entidade;
    }

    public Long getRegistroId() {
        return registroId;
    }

    public void setRegistroId(Long registroId) {
        this.registroId = registroId;
    }

    public OperacaoAuditoria getOperacao() {
        return operacao;
    }

    public void setOperacao(OperacaoAuditoria operacao) {
        this.operacao = operacao;
    }

    public String getUsuarioResponsavel() {
        return usuarioResponsavel;
    }

    public void setUsuarioResponsavel(String usuarioResponsavel) {
        this.usuarioResponsavel = usuarioResponsavel;
    }

    public Instant getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Instant dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Map<String, Object> getDadosAntes() {
        return dadosAntes;
    }

    public void setDadosAntes(Map<String, Object> dadosAntes) {
        this.dadosAntes = dadosAntes;
    }

    public Map<String, Object> getDadosDepois() {
        return dadosDepois;
    }

    public void setDadosDepois(Map<String, Object> dadosDepois) {
        this.dadosDepois = dadosDepois;
    }

    public RegistroAuditoria() {
    }
}
