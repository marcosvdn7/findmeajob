package com.entra21.findmeajob.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entra21.findmeajob.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

	Optional<Usuario> findById(Integer userId);
	
	Usuario findByEmail(String email);
}
