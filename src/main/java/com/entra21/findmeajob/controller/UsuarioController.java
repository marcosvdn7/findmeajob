package com.entra21.findmeajob.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.entra21.findmeajob.models.Post;
import com.entra21.findmeajob.models.Usuario;
import com.entra21.findmeajob.services.PostService;
import com.entra21.findmeajob.services.UsuarioService;
import com.entra21.findmeajob.services.UtilityService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService us;
	
	@Autowired 
	private PostService postService;
	
	@Autowired
	private UtilityService utility;

	@GetMapping("/signUp")
	public String signUp() {
		return "usuario/cadastro";
	}

	@PostMapping("/signUp")
	public String signUp(Usuario usuario, Model model) {
		if (us.findByEmail(usuario.getEmail()) != null) {
			model.addAttribute("emailCadastrado", "O email informado ja está cadastrado!");
			return "redirect:/usuario/cadastro";
		}

		us.cadastrar(usuario);

		return "redirect:/usuarios/login";
	}

	@GetMapping("/login")
	public String login() {
		return "usuario/login";
	}

	@GetMapping(value = "/listaUsuarios")
	public ResponseEntity<List<Usuario>> listaUsuarios() {
		List<Usuario> usuarios = us.listaUsuarios();
		return ResponseEntity.ok().body(usuarios);
	}

	@RequestMapping(value = "/deletarUsuario")
	public String deletarUsuario(Integer id) {
		us.deletar(id);

		return "redirect:/usuario/home";
	}

	@GetMapping(value = "/perfilUsuario/{idUsuario}")
	public ModelAndView perfilUsuario(@PathVariable Integer idUsuario) {
		ModelAndView mv = new ModelAndView("usuario/telaVerPerfilUsuario");
		Usuario usuario = us.findById(idUsuario);
		mv.addObject("usuario", usuario);
		return mv;
	}
	
	@GetMapping(value = "/perfilUsuario/meuPerfil")
	public ModelAndView perfilUsuarioLogado() {
		ModelAndView mv = new ModelAndView("usuario/perfilLogado");
		Usuario usuario = utility.getUsuarioLogado();
		List<Post> posts = postService.listarPorUsuario(usuario.getUserId());
		String temFoto = utility.temFotoPerfil(usuario);
		
		mv.addObject("posts", posts);
		mv.addObject("usuario", usuario);
		mv.addObject("temFoto", temFoto);
		return mv;
	}

	@GetMapping(value = "/editarPerfil/{idUsuario}")
	public ModelAndView editarPerfil(@PathVariable Integer idUsuario) {
		ModelAndView mv = new ModelAndView("usuario/editarPerfil");
		Usuario usuario = utility.getUsuarioLogado();
		String temFoto = utility.temFotoPerfil(usuario);
		
		mv.addObject("usuario", usuario);
		mv.addObject("temFoto", temFoto);
		mv.addObject("usuario", us.findById(idUsuario));

		return mv;
	}

	@PostMapping(value = "/editarPerfil/{idUsuario}")
	public String editarUsuario(@PathVariable Integer idUsuario, Usuario usuario) {
		us.editar(idUsuario, usuario);
		return "redirect:/listaUsuarios";
	}

	@GetMapping("/index")
	public String index(@CurrentSecurityContext(expression = "authentication.name") String email) {

		// Em seguida, iniciamos um usuario para buscar o email do mesmo no banco
		Usuario usuario = us.findByEmail(email);

		// String vazia para inserir a url que ele será levado, baseado no seu papel
		String redirectURL = "";
		if (us.temAutorizacao(usuario, "USUARIO")) {
			redirectURL = "/home";
		} else if (us.temAutorizacao(usuario, "ADMIN")) {
			redirectURL = "admin/listaUsuariosAdmin";
		}
		return redirectURL;
	}

}
