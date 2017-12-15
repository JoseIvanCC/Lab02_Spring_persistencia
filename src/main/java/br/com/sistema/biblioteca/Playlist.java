package br.com.sistema.biblioteca;

import java.io.Serializable;
import javax.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Playlist implements Serializable{
	
	private static final long serialVersionUID = -2982396057959779107L;
	@Column(name = "nomePlaylist")
	String nome;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;
	
	
	@ManyToMany(cascade = CascadeType.ALL, targetEntity=Musica.class)
	@JoinTable(
	        name = "musica_e_playlist", 
	        joinColumns = { @JoinColumn(name = "playlist_id", referencedColumnName = "id") }, 
	        inverseJoinColumns = { @JoinColumn(name = "musica_id", referencedColumnName = "idMusica") }
	    )
	private List<Musica> listaMusicasPlaylist;
	
	public Playlist(String nome) {
		this.nome = nome;
		this.listaMusicasPlaylist = new ArrayList<Musica>();
	}
	
	public Playlist() {

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Musica> getmusicas() {
		return this.listaMusicasPlaylist;
	}
	
	public void setMusicas(List<Musica> musicas) {
		this.listaMusicasPlaylist = musicas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((listaMusicasPlaylist == null) ? 0 : listaMusicasPlaylist.hashCode());
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
		Playlist other = (Playlist) obj;
		if (listaMusicasPlaylist == null) {
			if (other.listaMusicasPlaylist != null)
				return false;
		} else if (!listaMusicasPlaylist.equals(other.listaMusicasPlaylist))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
	public String musicas(){
		String musicas ="";
		for (Musica musica : listaMusicasPlaylist) {
			musicas+=musica.getNome()+" ";
		} return musicas;
		
	}
	
	public boolean addMusica(Musica musica) {
		if (musica != null && (!this.listaMusicasPlaylist.contains(musica))) {
			this.listaMusicasPlaylist.add(musica);
			return true;
		}
		return false;
	}
	
	public boolean removerMusica(String nomeMusica) {
		for (Musica musica : listaMusicasPlaylist) {
			if (musica.getNome().equalsIgnoreCase(nomeMusica)) {
				return this.listaMusicasPlaylist.remove(musica);
			}
		}
		return false;
	}
	

	public boolean contemMusica(String nomeMusica) {
		for (Musica musica : listaMusicasPlaylist) {
			if (musica.getNome().equalsIgnoreCase(nomeMusica)) {
				return true;
			}
		}
		return false;
	}
	
	public void removerMusicas() {
		this.listaMusicasPlaylist = new ArrayList<Musica>();
	}
	
	public Long getId() {
		return id;
	}
	
	public String toString() {
		return "Playlist (Nome: " + getNome() + ")";
	}
}
