package com.entra21.findmeajob.models;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_PUBLICACAO")
	private Long idPublicacao;

	@Column(name = "TITULO")
	private String titulo;

	@Column(name = "DATA_PUBLICACAO")
	private Instant dataPublicacao;

	@Column(name = "CONTEUDO")
	private String conteudo;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@ManyToMany
	@JoinTable(name = "post_categoria", 
			   joinColumns = @JoinColumn(name = "id_post"), 
			   inverseJoinColumns = @JoinColumn(name = "id_categoria"))
	private List<Categoria> categorias = new ArrayList<>();

	public Post() {

	}
	
	public Post(Long idPublicacao, String titulo, Instant dataPublicacao, String conteudo, Usuario usuario,
			List<Categoria> categorias) {
		super();
		this.idPublicacao = idPublicacao;
		this.titulo = titulo;
		this.dataPublicacao = dataPublicacao;
		this.conteudo = conteudo;
		this.usuario = usuario;
		this.categorias = categorias;
	}

	public Long getIdPublicacao() {
		return idPublicacao;
	}

	public void setIdPublicacao(Long idPublicacao) {
		this.idPublicacao = idPublicacao;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Instant getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(Instant dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(idPublicacao);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		return Objects.equals(idPublicacao, other.idPublicacao);
	}
	
	@Override
	public String toString() {
		return "Publicacao [idPublicacao=" + idPublicacao + ", titulo=" + titulo + ", dataPublicacao=" + dataPublicacao
				+ ", conteudo=" + conteudo + "]";
	}

}
