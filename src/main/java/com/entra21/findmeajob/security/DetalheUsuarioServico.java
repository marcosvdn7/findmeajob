package com.entra21.findmeajob.security;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.entra21.findmeajob.models.Usuario;
import com.entra21.findmeajob.repository.UsuarioRepository;

@Service
@Transactional
public class DetalheUsuarioServico implements UserDetailsService {
	
	private UsuarioRepository usuarioRepository;
	
	public DetalheUsuarioServico(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) {
		Usuario usuario = usuarioRepository.findByEmail(email);
		if (usuario != null) {
			DetalheUsuario detalheUsuario = new DetalheUsuario(usuario);
			return detalheUsuario;
		} else {
			throw new UsernameNotFoundException("Email nao cadastrado");
		}
		
	}

	

}
