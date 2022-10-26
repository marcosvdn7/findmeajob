package com.entra21.findmeajob.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.entra21.findmeajob.models.Categoria;
import com.entra21.findmeajob.models.Post;
import com.entra21.findmeajob.models.Usuario;
import com.entra21.findmeajob.repository.CategoriaRepository;
import com.entra21.findmeajob.repository.PostRepository;
import com.entra21.findmeajob.services.PostService;
import com.entra21.findmeajob.services.UtilityService;

@Controller
@RequestMapping("/publicacoes")
public class PostController {
	
	@Autowired
	private UtilityService utility;
	
	@Autowired
	private PostService ps;
	
	@Autowired
	private CategoriaRepository cr;
	
	@Autowired
	private PostRepository pr;
	
	//REDIRECIONA PARA A PAGINA DE PUBLICAÇÂO DE POST
	@GetMapping(value = "/{idUsuario}/publicarPost")
	public ModelAndView publicarPost(@PathVariable Integer idUsuario) {
		ModelAndView mv = new ModelAndView("post/telaCriarPost");
		ArrayList<Categoria> categorias = (ArrayList<Categoria>)cr.findAll();
		Usuario usuario = utility.getUsuarioLogado();
		String temFoto = utility.temFotoPerfil(usuario);
		
		mv.addObject("usuario", usuario);
		mv.addObject("temFoto", temFoto);
		mv.addObject("categorias", categorias);
		return mv;
	}
	
	//FAZ A PUBLICAÇÂO
	@PostMapping(value = "/{idUsuario}/publicarPost")
	public String criarPublicacao(@Valid Post post, @PathVariable Integer idUsuario,
								  @RequestParam("idCategorias") ArrayList<Long> idCategorias,
								  RedirectAttributes attributes) {
		
		if (ps.publicar(post, idUsuario, idCategorias) == false) {
			attributes.addFlashAttribute("mensagem", "Escolha ao menos uma categoria");
			return "redirect:/{idUsuario}/publicarPost";
		}
		
		return "redirect:/publicacoes/"+post.getIdPublicacao()+"";
	}
	
	//MOSTRA UMA LISTA COM TODAS AS PUBLICAÇÕES CADASTRADAS
	//NO BANCO DE DADOS
	@GetMapping(value = "/listaPosts")
	public ModelAndView listaPost(Model model) {
		ModelAndView mv = new ModelAndView("post/listaPublicacoes");
		Usuario usuario = utility.getUsuarioLogado();
		String temFoto = utility.temFotoPerfil(usuario);
		List<Post> posts = ps.publicacoesRecentes();
		
		mv.addObject("usuario", usuario);
		mv.addObject("temFoto", temFoto);
		mv.addObject("posts", posts);
		
		return mv;	
	}
	
	//EXCLUI UMA PUBLICAÇÃO
	@GetMapping(value = "/deletarPost/{idPublicacao}")
	public String deletarPost(@PathVariable Long idPublicacao, Model model) {
		ps.deletar(idPublicacao);
		
		return "redirect:/publicacoes/listaPosts";
	}
	
	//MOSTRA UMA PUBLICAÇÃO
	@GetMapping(value = "/{idPublicacao}")
	public ModelAndView mostrarPost(@PathVariable Long idPublicacao){
		ModelAndView mv = new ModelAndView("post/verPost");
		Usuario usuario = utility.getUsuarioLogado();
		String temFoto = utility.temFotoPerfil(usuario);
		Post post = ps.findById(idPublicacao);
		
		mv.addObject("usuario", usuario);
		mv.addObject("temFoto", temFoto);
		mv.addObject("post", post);
		
		return mv;
	}
	
	@GetMapping(value = "/editarPublicacao/{idPost}")
	public ModelAndView editarPublicacao(@PathVariable Long idPost, Post post) {
		ModelAndView mv = new ModelAndView("post/telaEditarPost");
		Usuario usuario = utility.getUsuarioLogado();
		String temFoto = utility.temFotoPerfil(usuario);
		List<Categoria> categorias = cr.findAll();
		
		mv.addObject("usuario", usuario);
		mv.addObject("temFoto", temFoto);
		mv.addObject("categorias", categorias);
		mv.addObject("post", post = ps.findById(idPost));
		
		return mv;
		}
	
	  @PostMapping("/editar/{id}")
	    public String editarUsuario(@PathVariable("id") long id,Post post) {
	        pr.save(post);
	        return "redirect:/"+post.getIdPublicacao()+"";
	    }

	
	
//	@PostMapping(value = "/editarPublicacao/{idPublicacao}")
//	public String editarPost(@PathVariable Long idPublicacao, Post post) {
//		ps.editar(idPublicacao,post);
//		
//		return "redirect:/listaPosts";
//	}
	
	@GetMapping(value = "categorias/{idCategoria}")
	public ModelAndView listarPorCategoria(@PathVariable Long idCategoria) {
		ModelAndView mv = new ModelAndView("post/listaPublicacoesCategorias");
		Usuario usuario = utility.getUsuarioLogado();
		String temFoto = utility.temFotoPerfil(usuario);
		List<Post> posts = ps.listarPorCategoria(idCategoria);
		
		mv.addObject("usuario", usuario);
		mv.addObject("temFoto", temFoto);
		mv.addObject("posts", posts);
		return mv;
	}
	
	@GetMapping(value = "/perfil/{idUsuario}")
	public ResponseEntity<List<Post>> postsUsuario(@PathVariable Integer idUsuario){
		List<Post> posts = ps.listarPorUsuario(idUsuario);
		
		return ResponseEntity.ok().body(posts);
	}
	
}