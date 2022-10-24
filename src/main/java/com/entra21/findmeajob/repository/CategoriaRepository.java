package com.entra21.findmeajob.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entra21.findmeajob.models.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	
	Optional<Categoria> findById(Long id);

}
