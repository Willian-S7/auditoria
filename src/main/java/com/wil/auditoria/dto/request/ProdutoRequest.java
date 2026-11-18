package com.wil.auditoria.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProdutoRequest(
    @NotNull @Positive 
    Long id,
    @NotBlank 
    String nome,
    @NotBlank 
    String descricao,
    @NotNull  
    BigDecimal preco,
    @Positive @NotBlank 
    int qtdEstoque,
    @Positive @NotNull
    Long categoriaId
) {

}
