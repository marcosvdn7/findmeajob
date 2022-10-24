package com.entra21.findmeajob.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entra21.findmeajob.models.Categoria;
import com.entra21.findmeajob.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository cr;
	
	public Categoria findById(Long idCategoria) {
		Optional<Categoria> obj = cr.findById(idCategoria);
		
		return obj.get();
	}
	
	public List<Categoria> listar(){
		return cr.findAll();
	}

}
