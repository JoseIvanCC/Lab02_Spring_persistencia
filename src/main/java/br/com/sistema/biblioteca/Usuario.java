package br.com.sistema.biblioteca;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import br.com.sistema.exception.StringInvalidaException;

@Entity
public class Usuario implements Serializable{
	private static final long serialVersionUID = -892684307592807505L;
	
	/*private String nome;
	private HashMap<String, Album> albuns;
	private HashSet<Playlist> playlists;
	private HashSet<Artista> artistasFavoritos;*/
	
	/*public Usuario(String nome) {
		this.nome = nome;
		this.albuns = new HashMap<String, Album>();
		this.playlists = new HashSet<Playlist>();
		this.artistasFavoritos = new HashSet<Artista>();
	}*/
	
	@Column(name = "nome")
	String nome;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "senha")
	private String senha;
	
	@OneToOne(mappedBy = "usuario")
	private Musiteca musitecaUsuario;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	public Usuario(String nome, String email, String senha) throws StringInvalidaException {
		if (!(Pattern.matches("[a-zA-Z0-9._]+@+[a-zA-Z0-9.]+.{2,7}", email))) {
			throw new StringInvalidaException("Email não é valido!");
		}
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.musitecaUsuario= new Musiteca();
	}
	
	public Usuario() {
		
	}
	
	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		Usuario other = (Usuario) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	public Musiteca getMusiteca() {
		return musitecaUsuario;
	}

	public void setMusiteca(Musiteca musiteca) {
		this.musitecaUsuario = musiteca;
	}
	
	public String toString(){
		return "Usuario (Nome: "+getNome()+", Email: "+getEmail()+", Senha: "+getSenha()+")";
	}
	
	public String addPlaylist(Playlist playlist) {
		return this.musitecaUsuario.addPlaylist(playlist);
	}
	
	public String addArtista(Artista artista) {
		return this.musitecaUsuario.addArtista(artista);
	}
	
	public String addMusic(Musica musica, Album album, Artista artista) {
		return this.musitecaUsuario.cadastrarMusica(musica, album, artista);
	}
	
	public String addFavoritos(Artista favorito) {
		return this.musitecaUsuario.addFavorito(favorito);
	}
	
	public List<Artista> listaArtista() {
		return this.musitecaUsuario.getListaArtistas();
	}
	
	public List<Artista> listaFavoritos() {
		return this.musitecaUsuario.getArtistasFavoritos();
	}
	
	public Artista getArtista(String nomeArtista) {
		return this.musitecaUsuario.getArtista(nomeArtista);
	}
	
	public String addMusicaArtista(Artista artistaBuscado, String novaMusica) {
		return this.musitecaUsuario.addMusicaArtista(artistaBuscado, novaMusica);
	}
	
	public String alterarNota(int nota, Artista artista) {
		return this.musitecaUsuario.alterarNota(nota, artista);
	}
	
	public void removerPlaylist(List<String> playlists) {
		this.musitecaUsuario.removerPlaylists(playlists);
	}
	
	public void removerFavorito(Artista artista) {
		this.musitecaUsuario.removerFavorito(artista);
	}
	
	public List<Artista> buscarArtistas(String nome) {
		return this.musitecaUsuario.buscarArtistas(nome);
	}
	
	public List<Musica> getMusicasPorNome(String nomeMusica) {
		return this.musitecaUsuario.getMusicasPorNome(nomeMusica);
	}
	
	public String addMusicaPlaylist(String nomeMusica, String nomePlaylist) {
		return this.musitecaUsuario.addMusicaPlaylist(nomeMusica, nomePlaylist);
	}
	
	public void removerMusicaPlaylist(String nomeMusica, String nomePlaylist) {
		this.musitecaUsuario.removerMusicaDaPlaylist(nomeMusica, nomePlaylist);
	}
	
	public void adicionarMusica(Musica musica) {
		this.musitecaUsuario.adicionarMusica(musica);
	}
	
	public Set<Artista> getConjuntoArtistasFavoritos() {
		return this.musitecaUsuario.getArtistasFavMusiteca();
	}
	
	public List<Playlist> getListaPlaylists() {
		return this.musitecaUsuario.getListaPlaylists();
	}
	
	public List<Artista> getListaArtista() {
		return this.musitecaUsuario.getListaArtistas();
	}
	
}
