package com.entra21.findmeajob.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.entra21.findmeajob.models.Endereco;
import com.entra21.findmeajob.models.Usuario;
import com.entra21.findmeajob.repository.EnderecoRepository;
import com.entra21.findmeajob.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository ur;
	
	@Autowired
	private EnderecoRepository er;
	
	@Autowired
	private BCryptPasswordEncoder criptografia;

	public Usuario findById(Integer id) {
		Optional<Usuario> obj = ur.findById(id);

		return obj.get();
	}

	public Usuario findByEmail(String email) {
		return ur.findByEmail(email);
	}

	public Usuario cadastrar(Usuario usuario) {
		usuario.setDataCadastro(Instant.now());
		String senhaCriptografada = criptografia.encode(usuario.getSenha());
		usuario.setSenha(senhaCriptografada);
		Optional<Endereco> endereco = er.findById(1L);
		usuario.setEndereco(endereco.get());
		
		return ur.save(usuario);
	}

	// lista todos os usuarios cadastrados no banco de dados
	public List<Usuario> listaUsuarios() {
		return ur.findAll();
	}

	public void deletar(Integer id) {
		Optional<Usuario> usuario = ur.findById(id);
		ur.delete(usuario.get());
	}

	/*
	 * Recebe como parametro o id do usuario que esta sendo editado, para fazer a
	 * busca no banco de dados, e um objeto Usuario com os dados editados. Dentro do
	 * metodo, usamos outro metodos que acabmos de criar para pegar os dados
	 * editados do usuario passado como parametro e inserir no usuario que buscamos
	 * do banco de dados.
	 */

	public Usuario editar(Integer id, Usuario usuarioEditado) {
		Optional<Usuario> optUsuario = ur.findById(id);
		atualizarDados(optUsuario.get(), usuarioEditado);

		return ur.save(optUsuario.get());
	}

	public Usuario encontrarPorEmail(String email) {
		return ur.findByEmail(email);
	}

	public boolean temAutorizacao(Usuario usuario, String permissaoUsuario) {
		if (usuario.getPermissaoUsuario().equals(permissaoUsuario)) {
			return true;
		}
		return false;
	}

	private void atualizarDados(Usuario usuario, Usuario usuarioEditado) {
		usuario.setNomeCompleto(usuarioEditado.getNomeCompleto());
		usuario.setDataNascimento(usuarioEditado.getDataNascimento());
	}
}
