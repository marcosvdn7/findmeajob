package com.entra21.findmeajob.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entra21.findmeajob.models.Post;
import com.entra21.findmeajob.models.Usuario;

public interface PostRepository extends JpaRepository<Post, Long> {
	
	Optional<Post> findById(Long id);

	List<Post> findByUsuario(Usuario usuario);
	
}
