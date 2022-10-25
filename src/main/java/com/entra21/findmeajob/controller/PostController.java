package com.entra21.findmeajob.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
	public String criarPublicacao(Post post, @PathVariable Integer idUsuario,
								  @RequestParam("idCategorias") ArrayList<Long> idCategorias,
								  RedirectAttributes attributes) {
		ps.publicar(post, idUsuario, idCategorias);
		
		return "redirect:/publicacoes/"+post.getIdPublicacao()+"";
	}
	
	//MOSTRA UMA LISTA COM TODAS AS PUBLICAÇÕES CADASTRADAS
	//NO BANCO DE DADOS
	@GetMapping(value = "/listaPosts")
	public ModelAndView listarPost() {
		ModelAndView mv = new ModelAndView("post/listaPublicacoes");
		Usuario usuario = utility.getUsuarioLogado();
		String temFoto = utility.temFotoPerfil(usuario);
		List<Post> posts = ps.publicacoesRecentes();
//		Collections.sort(posts, Collections.reverseOrder());
		
		mv.addObject("fotosUsuarioPost", utility.usuarioPostTemFoto(posts));
		mv.addObject("usuario", usuario);
		mv.addObject("temFoto", temFoto);
		mv.addObject("posts", posts);
		
		return mv;	
	}
	
	//EXCLUI UMA PUBLICAÇÃO
	@PostMapping(value = "/deletarPost")
	public String deletarPost(Long id) {
		ps.deletar(id);
		
		return "redirect:/listaPosts";
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
	
	@GetMapping(value = "/editarPublicacao/{idUser}/{idPost}")
	public ModelAndView editarPublicacao(@PathVariable Integer idUser, @PathVariable Long idPost, Post post) {
		ModelAndView mv = new ModelAndView("post/editarPublicacao");
		Usuario usuario = utility.getUsuarioLogado();
		String temFoto = utility.temFotoPerfil(usuario);
		List<Categoria> categorias = cr.findAll();
		
		mv.addObject("usuario", usuario);
		mv.addObject("temFoto", temFoto);
		mv.addObject("categorias", categorias);
		mv.addObject("post", post = ps.findById(idPost));
		
		return mv;
		}
	
	@PostMapping(value = "/editarPublicacao/{idUser}/{idPost}")
	public String editarPost(@PathVariable Integer idUser, @PathVariable Long idPost, Post post, 
							 @RequestParam("idCategorias") ArrayList<Long> idCategorias) {
		ps.editar(idPost, idUser, post, idCategorias);
		
		return "redirect:/listaPosts";
	}
	
	@GetMapping(value = "categorias/{idCategoria}")
	public ModelAndView listarPorCategoria(@PathVariable Long idCategoria) {
		ModelAndView mv = new ModelAndView("post/publicacoes");
		List<Post> posts = ps.listarPorCategoria(idCategoria);
		mv.addObject("posts", posts);
		return mv;
	}
	
	@GetMapping(value = "/perfil/{idUsuario}")
	public ResponseEntity<List<Post>> postsUsuario(@PathVariable Integer idUsuario){
		List<Post> posts = ps.listarPorUsuario(idUsuario);
		
		return ResponseEntity.ok().body(posts);
	}
	
}