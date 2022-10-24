package com.entra21.findmeajob.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.entra21.findmeajob.models.Endereco;
import com.entra21.findmeajob.models.Post;
import com.entra21.findmeajob.models.Usuario;
import com.entra21.findmeajob.models.enums.PermissaoUsuario;

public class DetalheUsuario implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	
	public DetalheUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<PermissaoUsuario> permissoes = usuario.getPermissoesUsuario();
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		for (PermissaoUsuario permissao : permissoes) {
			SimpleGrantedAuthority sga = new SimpleGrantedAuthority(permissao.name());
			authorities.add(sga);
		}
		
		return authorities;
	}

	@Override
	public String getPassword() {
		return usuario.getSenha();
	}

	@Override
	public String getUsername() {
		return usuario.getNomeCompleto();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public String getNome() {
		return usuario.getNomeCompleto();
	}
	
	public Integer getIdUsuarioLogado() {
		return usuario.getUserId();
	}
	
	public String getFotoPerfil() {
		return this.usuario.getFotoPerfil();
	}
	
	public Endereco getEndereco() {
		return this.usuario.getEndereco();
	}
	
	public String getCidade() {
		return this.usuario.getEndereco().getCidade();
	}
	
	public List<Post> getPublicacoes(){
		return this.usuario.getPosts();
	}
	
	public String getEmail() {
		return this.usuario.getEmail();
	}

}
