package br.com.sistema.biblioteca;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Musiteca implements Serializable{

	private static final long serialVersionUID = 1L;

	@JoinTable(name = "artistasMusiteca")
	@OneToMany(cascade = CascadeType.ALL, targetEntity = Artista.class)
	private Set<Artista> artistasMusiteca;
	
	@JoinTable(name = "artistasFavoritosMusiteca")
	@OneToMany(cascade = CascadeType.ALL, targetEntity = Artista.class)
	private Set<Artista> artistasFavMusiteca;
	
	@ElementCollection(targetClass = Musica.class)
	@JoinTable(name = "playlistsMusiteca")
	private Map<String,List<Musica>> playlists;
	
	@JoinTable(name = "listaMusicasMusiteca")
	@OneToMany(cascade = CascadeType.ALL, targetEntity = Musica.class)
	private Set<Musica> musicasMusiteca;
	
	@OneToOne(mappedBy = "musitecaUsuario")
	private Usuario usuario;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;
	
	public Musiteca(String nomeMusiteca) {
		this.artistasMusiteca = new HashSet<Artista>();
		this.artistasFavMusiteca = new HashSet<Artista>();
		this.playlists = new HashMap<String,List<Musica>>();
		this.musicasMusiteca = new HashSet<Musica>();
	}
	
	protected Musiteca() {
		
	}

	public Set<Artista> getArtistasMusiteca() {
		return artistasMusiteca;
	}

	public void setArtistasMusiteca(Set<Artista> artistasMusiteca) {
		this.artistasMusiteca = artistasMusiteca;
	}
	
	public List<Artista> getListaArtistas() {
		List<Artista> artistas = new ArrayList<Artista>();
		for (Artista artista : artistasMusiteca) {
			artistas.add(artista);
		}
		return artistas;
	}

	public Set<Artista> getArtistasFavMusiteca() {
		return artistasFavMusiteca;
	}

	public void setArtistasFavMusiteca(Set<Artista> artistasFavMusiteca) {
		this.artistasFavMusiteca = artistasFavMusiteca;
	}

	public Map<String,List<Musica>> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(Map<String,List<Musica>> playlists) {
		this.playlists = playlists;
	}
	
	public Map<String,List<Musica>> getListaPlaylists() {
		return playlists;
	}

	public Set<Musica> getMusicasMusiteca() {
		return musicasMusiteca;
	}

	public void setMusicasMusiteca(Set<Musica> musicasMusiteca) {
		this.musicasMusiteca = musicasMusiteca;
	}

	public long getId() {
		return id;
	}
	
	//funcionalidades
	
	public String addFavorito(Artista artista) {
		if (artista != null && !(this.artistasFavMusiteca.contains(artista))) {
			this.artistasFavMusiteca.add(artista);
			return "Artista adicionado aos favoritos.";
		} else if (artista == null) {
			return "Artista não está cadastrado no sistema.";
		} else {
			return "Artista já existe no sistema.";
		}
	}
	
	public String addPlaylist(List<Musica> playlist, String nome) {
		if (this.playlists.containsValue(playlist) == false) {
			this.playlists.put(nome, playlist);
			return "Playlist cadastrada com sucesso.";
		} else {
			return "playlist já existe no sistema.";
		}
	}
	
	public Artista getArtista(String nomeArtista) {
		for (Artista artista : this.artistasMusiteca) {
			if (artista.getNome().equalsIgnoreCase(nomeArtista)) {
				return artista;
			}
		}
		return null;
	}
	
	public String addArtista(Artista artista) {
		if (this.artistasMusiteca.contains(artista)) {
			return "Artista já está cadastrado no sistema.";
		} else {
			this.artistasMusiteca.add(artista);
			return "Artista cadastrado com sucesso.";
		}
	}
	
	public String alterarNota(double nota, Artista artista) {
		System.out.println(artista);
		if (artista != null) {
			if (nota < 11 && nota >= 0) {
				artista.setNota(nota);
				this.alterarNotaArtista(nota, artista);
				this.alterarNotaFavorito(nota, artista);
				return "Nota alterada com sucesso.";
			} else {
				return "Nota deve estar no intervalo entre 0 e 10.";
			}
		}
		return "Artista não encontrado.";
	}


	private void alterarNotaArtista(double novaNota, Artista artistaBusca) {
		if (artistaBusca != null) {
			for (Artista artista : artistasMusiteca) {
				if (artista.getNome().equalsIgnoreCase(artistaBusca.getNome())) {
					artista.setNota(novaNota);
				}
			}
		}
	}
	
	private void alterarNotaFavorito(double novaNota, Artista artistaBuscado) {
		if (artistaBuscado != null) {
			for (Artista favorito : this.artistasFavMusiteca) {
				if (favorito.getNome().equalsIgnoreCase(artistaBuscado.getNome())) {
					favorito.setNota(novaNota);
				}
			}
		}
	}
	
	public String addMusicaArtista(Artista artista, String novaUltimaMusica) {
		if (artista != null) {
			List<Musica> musicasArtista = artista.getListaMusicas();
			for (Musica musica : musicasArtista) {
				System.out.println(musicasArtista);
				if (musica.getNome().equalsIgnoreCase(novaUltimaMusica)) {
					artista.setUltimaMusicaOuvida(musica);
					alterarMusica(artista, musica);
					System.out.println(artista);
					return "Música alterada com sucesso.";
				}
			}
		}
		return "Música não encontrada.";
	}
	
	public void removerFavorito(Artista artista) {
		this.artistasFavMusiteca.remove(artista);
	}

	private void alterarMusica(Artista artistaBusca, Musica musicaBusca) {
		for (Artista artista : this.artistasMusiteca) {
			if (artista.getNome().equalsIgnoreCase(artistaBusca.getNome())) {
				List<Musica> musicasArtista = artistaBusca.getListaMusicas();
				for (Musica musica : musicasArtista) {
					if (musica.getNome().equalsIgnoreCase(musicaBusca.getNome())) {
						artista.setUltimaMusicaOuvida(musica);
					}
				}
			}
		}
	}
	
	public String cadastrarMusica(Musica musica, Album album, Artista nomeArtista) {
		String mensagemArtista = this.addArtista(nomeArtista);
		Artista artista = this.getArtista(nomeArtista.getNome());
		if (!(artista.contemAlbum(album))) {
			artista.addAlbum(album);
			album.addMusica(musica);
			this.adicionarMusica(musica);
			return mensagemArtista + " e música cadastrado com sucesso.";
		} else {
			Album albumExistente = artista.getAlbum(album.getNome());
			if (!(albumExistente.contemMusica(musica))) {
				albumExistente.addMusica(musica);
				this.adicionarMusica(musica);
				return mensagemArtista + " e Música cadastrado com sucesso.";
			} else {
				return mensagemArtista + " e Música já existe no álbum.";
			}
		}
	}
	
	public void removerMusicaDaPlaylist(String nomeMusica, String nomePlaylist) {
		for(Musica musica : playlists.get(nomePlaylist)) {
			if(musica.getNome().equalsIgnoreCase(nomeMusica)) {
				playlists.get(nomePlaylist).remove(musica);
				break;
			}
		}
	}
	
	
	public List<Musica> getPlaylist(String nomePlaylist) {
		return this.playlists.get(nomePlaylist);
	}
	
	
	public String addMusicaPlaylist(String nomeMusica, String nomePlaylist) {
		List<Musica> playlist = this.playlists.get(nomePlaylist);
		Musica musica = this.getMusicasPorNome(nomeMusica);
		
		if (musica == null) {
			return "Música não foi cadastrada.";
		}
		if(!playlist.contains(musica)) {
			playlist.add(musica);
			return "Musica adicionada na playlist";
		}
		
		return "Musica já está na playlist";
	}
	
	public void removerPlaylist(String nomePlaylist) {
		this.playlists.remove(nomePlaylist);
	}
	
	public boolean containsMusicPlaylist(String nomeMusica, String nomePlaylist) {
		List<Musica> playlist = this.getPlaylist(nomePlaylist);
		for (Musica musica : playlist) {
			if(musica.getNome().equalsIgnoreCase(nomeMusica)) {
				return true;
			}
		}
		return false;
	}
	
	public void adicionarMusica(Musica musica) {
		if (musica != null) {
			this.musicasMusiteca.add(musica);
		}
	}
	
	public Musica getMusicasPorNome(String nomeMusica) {
		for (Musica musica : musicasMusiteca) {
			if (musica.getNome().equalsIgnoreCase(nomeMusica)) {
				return musica;
			}
		}
		return null;
	}
	
	public boolean containsPlaylist(String nomePlaylist) {
		return playlists.keySet().contains(nomePlaylist);
	}
	
	public boolean containsMusic(Musica musica) {
		return musicasMusiteca.contains(musica);
	}
	
	public boolean containsArtista(Artista artista) {
		return artistasMusiteca.contains(artista);
	}
	
	public boolean containsFavorito(Artista favorito) {
		return artistasFavMusiteca.contains(favorito);
	}
	
	public List<Artista> getArtistasFavoritos() {
		List<Artista> favoritos = new ArrayList<Artista>();
		for (Artista favorito : artistasFavMusiteca) {
			favoritos.add(favorito);
		}
		return favoritos;
	}
	
	public void removerPlaylists(List<String> listaPlaylist) {
		for (String nomePlaylist : listaPlaylist) {
			this.playlists.remove(nomePlaylist);
		}
	}
	
	public void removerMusicasPlaylist(String nomePlaylist) {
		this.playlists.replace(nomePlaylist, new ArrayList<Musica>());
	}
	
	public List<Artista> buscarArtistas(String nomeArtista) {
		List<Artista> artistasEncontrados = new ArrayList<Artista>();
		String[] busca = nomeArtista.split(" ");
		for (Artista artista : artistasMusiteca) {
			if (busca.length == 1) {
				String[] nome = artista.getNome().split(" ");
				for (int i = 0; i < nome.length; i++) {
					if (nome[i].equalsIgnoreCase(nomeArtista)) {
						artistasEncontrados.add(artista);
						break;
					}
				}
			} else {
				if (artista.getNome().equalsIgnoreCase(nomeArtista)) {
					artistasEncontrados.add(artista);
				}
			}
		}
		return artistasEncontrados;
	}
}