package com.entra21.findmeajob.models;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.entra21.findmeajob.models.enums.PermissaoUsuario;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Integer userId;

	@Column(name = "NOME_COMPLETO")
	private String nomeCompleto;

	@Column(name = "EMAIL", unique = true)
	private String email;

	@Column(name = "DATA_NASCIMENTO")
	private Date dataNascimento;

	@Column(name = "SENHA")
	private String senha;

	@Column(name = "DATA_CADASTRO")
	private Instant dataCadastro;

	@Column(name = "FOTO_PERFIL")
	private String fotoPerfil;

	@Enumerated(EnumType.STRING)
	@Column(name = "PERMISSAO_USUARIO")
	private PermissaoUsuario permissaoUsuario;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_endereco")
	private Endereco endereco;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<Post> posts = new ArrayList<>();

	public Usuario() {
		this.permissaoUsuario = PermissaoUsuario.USUARIO;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Instant getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Instant dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getPermissaoUsuario() {
		return permissaoUsuario.name();
	}

	public void setPermissaoUsuario(PermissaoUsuario permissaoUsuario) {
		this.permissaoUsuario = permissaoUsuario;
	}

	public List<PermissaoUsuario> getPermissoesUsuario() {
		List<PermissaoUsuario> permissoes = new ArrayList<>();
		permissoes.add(permissaoUsuario);

		return permissoes;
	}

	public String getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(email, other.email);
	}

	@Override
	public String toString() {
		return "Usuario [userId=" + userId + ", nomeCompleto=" + nomeCompleto + ", email=" + email + ", dataNascimento="
				+ dataNascimento + ", senha=" + senha + ", dataCadastro=" + dataCadastro + "]";
	}

}
