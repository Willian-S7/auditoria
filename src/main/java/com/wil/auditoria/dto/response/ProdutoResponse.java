package com.wil.auditoria.dto.response;

import java.math.BigDecimal;

public record ProdutoResponse(
    Long id,
    String nome,
    String descricao,
    BigDecimal preco,
    int qtdEstoque,
    boolean ativo,
    Long categoriaId
) {

}
