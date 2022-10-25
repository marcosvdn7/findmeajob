package com.entra21.findmeajob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.entra21.findmeajob.models.Usuario;
import com.entra21.findmeajob.services.PostService;
import com.entra21.findmeajob.services.UtilityService;

@Controller
@RequestMapping("/home")
public class HomeController {
		
	@Autowired
	private PostService postService;
	
	@Autowired
	private UtilityService utility;
	
	@GetMapping
	public ModelAndView home(){
		ModelAndView mv = new ModelAndView("usuario/home");
		Usuario usuario = utility.getUsuarioLogado();
		String temFoto = utility.temFotoPerfil(usuario);
		
		mv.addObject("posts", postService.listarTodos());
		mv.addObject("usuario", usuario);
		mv.addObject("temFoto", temFoto);
		mv.addObject("publicacoesProximas", utility.getPostsProximos(usuario));
		mv.addObject("publicacoesRecentes", postService.publicacoesRecentes());
		
		return mv;
	}
}
