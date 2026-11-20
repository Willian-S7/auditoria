package com.wil.auditoria.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.wil.auditoria.dto.request.FiltroRegistroRequest;
import com.wil.auditoria.dto.response.RegistroAuditoriaDTO;
import com.wil.auditoria.model.RegistroAuditoria;
import com.wil.auditoria.repository.RegistroAuditoriaRepository;
import com.wil.auditoria.specifications.RegistroSpecification;

@Service 
public class RegistroAuditoriaService {

    private final RegistroAuditoriaRepository registroAuditoriaRepository;

    public RegistroAuditoriaService (RegistroAuditoriaRepository registroAuditoriaRepository) {
        this.registroAuditoriaRepository = registroAuditoriaRepository;
    }

    public RegistroAuditoriaDTO toDTO(RegistroAuditoria reg) {
        RegistroAuditoriaDTO dto =  new RegistroAuditoriaDTO(
            reg.getId(), reg.getEntidade(), 
            reg.getRegistroId(), reg.getOperacao(), 
            reg.getUsuarioResponsavel(), reg.getDataAlteracao(), 
            reg.getDadosAntes(), reg.getDadosDepois()
        );
        return dto;
    }

    public RegistroAuditoriaDTO buscarRegistro(Long id) {
        return registroAuditoriaRepository.findById(id).map(reg -> toDTO(reg)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro não encontrado"));
    }

    public List<RegistroAuditoriaDTO> filtroRegistros(FiltroRegistroRequest filtro) {
        Specification<RegistroAuditoria> specification = Specification.unrestricted();

        if (filtro.entidade() != null) {
            specification = specification.and(RegistroSpecification.porEntidade(filtro.entidade()));
        }

        if (filtro.registroId() != null) {
            specification = specification.and(RegistroSpecification.porRegistroId(filtro.registroId()));
        }

        if (filtro.operacao() != null) {
            specification = specification.and(RegistroSpecification.porOperacao(filtro.operacao()));
        }

        if (filtro.usuarioResponsavel() != null) {
            specification = specification.and(RegistroSpecification.porUsuario(filtro.usuarioResponsavel()));
        }

        if (filtro.dataInicio() != null && filtro.dataFim() != null && filtro.dataInicio().isAfter(filtro.dataFim())) {
            throw new IllegalArgumentException("A data inicial não pode ser posterior à data final");
        }

        if (filtro.dataInicio() != null) {
            specification = specification.and(RegistroSpecification.apartirDe(filtro.dataInicio()));
        }

        if (filtro.dataFim() != null) {
            specification = specification.and(RegistroSpecification.ateData(filtro.dataFim()));
        }

        List<RegistroAuditoria> registros = registroAuditoriaRepository.findAll(specification);

        return registros.stream().map(reg -> toDTO(reg)).toList();
    }
}
