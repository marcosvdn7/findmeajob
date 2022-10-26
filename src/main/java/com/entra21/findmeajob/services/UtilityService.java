package com.entra21.findmeajob.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.entra21.findmeajob.models.Post;
import com.entra21.findmeajob.models.Usuario;
import com.entra21.findmeajob.security.DetalheUsuario;

@Service
public class UtilityService {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PostService postService;
	
	public Usuario getUsuarioLogado() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		DetalheUsuario detalheUsuario = (DetalheUsuario) principal;
		Usuario usuario = usuarioService.findById(detalheUsuario.getIdUsuarioLogado());
		
		return usuario;
	}
	
	public String temFotoPerfil(Usuario usuario) {
		String fotoPerfil = usuario.getFotoPerfil();
		String temFoto = "nao";
		
		if (fotoPerfil != null) {
			temFoto = "sim";
		}
		return temFoto;
	}
	
	public List<Post> getPostsProximos(Usuario usuario){
		List<Post> publicacoesProximas = postService.buscarPorEndereco(usuario.getEndereco().getId());
		
		return publicacoesProximas;
	}
	
	public List<Post> usuarioPostTemFoto(List<Post> posts){
		List<String> temFotos = new ArrayList<String>();
		for (Post post : posts) {
			temFotos.add(temFotoPerfil(post.getUsuario()));
		}
		
		return posts;
	}
	

}
