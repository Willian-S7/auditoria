package com.wil.auditoria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wil.auditoria.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
