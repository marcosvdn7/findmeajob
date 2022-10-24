package com.entra21.findmeajob.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.entra21.findmeajob.models.Categoria;
import com.entra21.findmeajob.services.CategoriaService;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private CategoriaService cs;
	
	@GetMapping(value = "/categorias/{idCategoria}")
	public ResponseEntity<Categoria> findbyId(@PathVariable Long idCategoria) {
		Categoria categoria = cs.findById(idCategoria);
		
		return ResponseEntity.ok().body(categoria);
	}
	
	@GetMapping(value = "/listarCategorias")
	public ModelAndView listarCategorias(){
		ModelAndView mv = new ModelAndView("usuario/testeListaCategorias");
		List<Categoria> categorias = cs.listar();
		mv.addObject("categorias", categorias);
		
		return mv;
	}
	
	

}
