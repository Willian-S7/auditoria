package com.wil.auditoria.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CategoriaRequest(
    @NotNull @Positive
    Long id,
    @NotBlank 
    String nome
) {

}
