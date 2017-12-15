package br.com.sistema.biblioteca;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
public class Artista implements Serializable{
	
	private static final long serialVersionUID = -6694357995414174679L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(unique = true)
	private String nome;
	
	@Column(length = 10000)
	private String linkImagem;
	
	@JoinTable(name ="albunsArtista")
	@OneToMany(cascade = CascadeType.ALL, targetEntity=Album.class)
	@Column
	private Set<Album> albuns;
	
	@Column(name = "ultimaMusicaOuvida")
	private Musica ultimaMusicaOuvida;
	
	@Column(name = "nota")
	private double nota;
	
	public Artista(String nome, String linkImagem) {
		this.nome = nome;
		this.nota = -1;
		this.albuns = new HashSet<Album>();
		this.ultimaMusicaOuvida = null;
		if (linkImagem == "") {
			this.linkImagem = "http://www.buritama.sp.leg.br/imagens/parlamentares-2013-2016/sem-foto.jpg/image_preview";
		} else {
			this.linkImagem = linkImagem;
		}
	}
	
	public Artista() {

	}
	
	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLinkImagem() {
		return linkImagem;
	}

	public void setLinkImagem(String linkImagem) {
		this.linkImagem = linkImagem;
	}

	public Set<Album> getAlbuns() {
		return albuns;
	}

	public void setAlbuns(HashSet<Album> albuns) {
		this.albuns = albuns;
	}

	public Musica getUltimaMusicaOuvida() {
		return ultimaMusicaOuvida;
	}

	public void setUltimaMusicaOuvida(Musica ultimaMusicaOuvida) {
		this.ultimaMusicaOuvida = ultimaMusicaOuvida;
	}
	
	public List<Musica> getListaMusicas() {
		List<Musica> listaMusicas = new ArrayList<>();
		for (Album album : this.albuns) {
			for (Musica musica : album.getMusicas()) {
				listaMusicas.add(musica);
			}
		}
		return listaMusicas;
	}
	
	public Album getAlbum(String nomeAlbum) {
		for (Album album : this.albuns) {
			if (album.getNome().equalsIgnoreCase(nomeAlbum)) {
				return album;
			}
		}
		return null;
	}
	
	public void addAlbum(Album album) {
		if(album != null) {
			this.albuns.add(album);
		}
			
	}
	
	public void addUltimaMusicaOuvida(Musica musica) {
		if (musica != null) {
			this.ultimaMusicaOuvida = musica;
		}
	}
	
	private String nomeUltimaMusica() {
		if (this.ultimaMusicaOuvida == null) {
			return "?";
		} else {
			return this.ultimaMusicaOuvida.getNome();
		}
	}
	
	public boolean contemAlbum(Album album) {
		return this.albuns.contains(album);
	}
	
	private String descricaoAlbuns() {
		if (albuns.size() == 0) {
			return "?";
		} else {
			String nomesAlbuns = "";
			for (Album album : this.albuns) {
				nomesAlbuns += album.getNome() + " ";
			}
			return nomesAlbuns;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Artista other = (Artista) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	public String toString() {
		String nomeMusica = nomeUltimaMusica();
		String nomesAlbuns = descricaoAlbuns();
		return "Artista (Nome: " + getNome() + ", Imagem: " + getLinkImagem() + ", Nota: " + getNota()
				+ ", Ultima Música tocada: " + nomeMusica + ", Albuns: " + nomesAlbuns + ")";

	}
	
	public long getId() {
		return id;
	}
	
}
