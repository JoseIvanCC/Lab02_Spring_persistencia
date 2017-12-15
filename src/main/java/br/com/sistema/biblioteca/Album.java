package br.com.sistema.biblioteca;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
public class Album implements Serializable{
	
	private static final long serialVersionUID = -4903220190762272317L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;
	@Column(name = "nome")
	private String nome;
	@JoinTable(name ="musicasAlbum")
	@OneToMany(cascade = CascadeType.ALL, targetEntity=Musica.class)
	private List<Musica> musicasAlbum;
	
	
	private int ano;
	private int duracao;
	private ArrayList<Musica> musicas;
	
	public Album(String nomeAlbum) {
		this.nome = nomeAlbum;
		this.musicasAlbum = new ArrayList<Musica>();
	}
	
	public Album() {

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getDuracao() {
		return duracao;
	}

	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}

	public ArrayList<Musica> getMusicas() {
		return musicas;
	}

	public void setMusicas(ArrayList<Musica> musicas) {
		this.musicas = musicas;
	}
	
	public boolean addMusica(Musica musica) {
		if (musica != null && !this.musicasAlbum.contains(musica)) {
			return this.musicasAlbum.add(musica);
		}
		return false;
	}
	
	private String nomeMusicas() {
		String nome = "";
		for (Musica musica : musicas) {
			nome += musica.getNome() + " ";
		}
		return nome;
	}
	
	public boolean contemMusica(Musica musica) {
		return this.musicasAlbum.contains(musica);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ano;
		result = prime * result + duracao;
		result = prime * result + ((musicas == null) ? 0 : musicas.hashCode());
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
		Album other = (Album) obj;
		if (ano != other.ano)
			return false;
		if (duracao != other.duracao)
			return false;
		if (musicas == null) {
			if (other.musicas != null)
				return false;
		} else if (!musicas.equals(other.musicas))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	

	public String toString() {
		return "Album (Nome: " + getNome() + ", Musicas: " + nomeMusicas() + ")";

	}
	
}
