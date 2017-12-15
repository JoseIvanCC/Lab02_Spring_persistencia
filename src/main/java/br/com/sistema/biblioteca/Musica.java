package br.com.sistema.biblioteca;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Musica implements Serializable{
	

	private static final long serialVersionUID = 2559743162659965528L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idMusica")
	private long id;
	@Column(name = "nome")
	private String nome;
	@Column
	private String artista;
	@Column
	private String album;
	@Column(name = "duracao")
	private String duracao;
	@Column(name = "ano")
	private int ano;
	@Column(name = "genero")
	private String genero;
	//@ManyToMany(mappedBy = "listaMusicasPlaylist")
	//private List<Playlist> listaPlaylistsMusica;
	
	public Musica(String nomeMusica, String duracaoMusica, int anoMusica, String generoMusica) {
		this.nome = nomeMusica;
		this.duracao = duracaoMusica;
		this.ano = anoMusica;
		if (generoMusica.trim().isEmpty()) {
			this.genero = "?";
		} else {
			this.genero = generoMusica;
		}
		//this.listaPlaylistsMusica = new ArrayList<Playlist>();
	}
	
	protected Musica() {
		
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getArtista() {
		return artista;
	}
	public void setArtista(String artista) {
		this.artista = artista;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}


	public String getDuracao() {
		return duracao;
	}


	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}


	public int getAno() {
		return ano;
	}


	public void setAno(int ano) {
		this.ano = ano;
	}


	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	
	public String toString() {
		return "Música (Nome: " + getNome() + ", Duração: " + getDuracao() + ", Ano: " + getAno() + ", Genero: "
				+ getGenero() + ")";

	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Musica other = (Musica) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
	
}
