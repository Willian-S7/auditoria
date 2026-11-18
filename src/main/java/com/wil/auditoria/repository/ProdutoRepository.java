package com.wil.auditoria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wil.auditoria.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
