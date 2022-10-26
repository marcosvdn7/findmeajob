package com.entra21.findmeajob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.entra21.findmeajob.models.Usuario;
import com.entra21.findmeajob.services.UtilityService;

@Controller
@RequestMapping()
public class TelaSobreController {
	
	@Autowired
	private UtilityService utility;
	
	@GetMapping("/sobre")
	public ModelAndView sobre() {
		ModelAndView mv = new ModelAndView("usuario/sobre");
		Usuario usuario = utility.getUsuarioLogado();
		String temFoto = utility.temFotoPerfil(usuario);
		
		mv.addObject("usuario", usuario);
		mv.addObject("temFoto", temFoto);
		
		return mv;
	}

}
