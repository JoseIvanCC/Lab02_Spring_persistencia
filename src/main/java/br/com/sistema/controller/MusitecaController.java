package br.com.sistema.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.sistema.biblioteca.Album;
import br.com.sistema.biblioteca.Artista;
import br.com.sistema.biblioteca.Musica;
import br.com.sistema.biblioteca.Musiteca;
import br.com.sistema.biblioteca.Playlist;
import br.com.sistema.biblioteca.Usuario;
import br.com.sistema.exception.StringInvalidaException;
import br.com.sistema.repository.AlbumRepository;
import br.com.sistema.repository.ArtistaRepository;
import br.com.sistema.repository.MusicaRepository;
import br.com.sistema.repository.MusitecaRepository;
import br.com.sistema.repository.PlaylistRepository;
import br.com.sistema.repository.UsuarioRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/")
public class MusitecaController {
	
	@Autowired
	private MusicaRepository musicaRepository;
	@Autowired
	private AlbumRepository albumRepository;
	@Autowired
	private ArtistaRepository artistaRepository;
	@Autowired
	private MusitecaRepository musitecaRepository;
	@Autowired
	private PlaylistRepository playlistRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public MusitecaController() {
		
	}
	
	@RequestMapping(value = "/addMusicaArtista", method = RequestMethod.GET)
	public String addMusicaArtista(@RequestParam(value = "nome") String nome,
		@RequestParam(value="ultimaMusica") String ultimaMusica,
		@RequestParam(value="emailUsuario") String emailUsuario) {
		Usuario usuario = findUsuario(emailUsuario);
		if (usuario == null) {
			return "Você precisa estar logado para adicionar um usuário.";
		} else {
			List<Artista> artistas = (List<Artista>) this.artistaRepository.findAll();
			Artista artistaFinded = null;
			for (Artista artista : artistas) {
				if (artista.getNome().equalsIgnoreCase(nome)) {
					artistaFinded = artista;
				}
			}
			String mnsg = usuario.addMusicaArtista(artistaFinded, ultimaMusica);
			if (mnsg.equals("Música alterada com sucesso.")) {
				this.artistaRepository.save(artistaFinded);
			}
			return mnsg;
		}
	}
	
	@RequestMapping(value = "/alterarNota", method = RequestMethod.GET)
	public String alterarNota(@RequestParam(value = "nome") String nome, @RequestParam(value = "nota") int nota,
			@RequestParam(value="emailUsuario") String emailUsuario) {
		Usuario usuario = findUsuario(emailUsuario);
		if (usuario == null) {
			return "Você precisa estar logado para adicionar um artista.";
		} else {
			List<Artista> artistas = (List<Artista>) this.artistaRepository.findAll();
			Artista artistaFinded = null;
			for (Artista artista : artistas) {
				if (artista.getNome().equalsIgnoreCase(nome)) {
					artistaFinded = artista;
				}
			}
			String mnsg = usuario.alterarNota(nota, artistaFinded);
			this.artistaRepository.save(artistaFinded);
			return mnsg;
		}
	}
	
	public String addArtista(@RequestParam(value="nome") String nome, @RequestParam(value="foto") String foto,
			@RequestParam(value = "emailUsuario") String emailUsuario) {
		Usuario usuario = findUsuario(emailUsuario);
		if (usuario == null) {
			return "Você precisa estar logado para adicionar um artista.";
		} else {
			Musiteca musiteca = findMusiteca(usuario);
			Artista artista = new Artista(nome, foto);
			String mnsg = usuario.addArtista(artista);
			if (mnsg.equals("Artista cadastrado com sucesso.")) {
				this.artistaRepository.save(artista);
				musiteca.addArtista(artista);
				this.musitecaRepository.save(musiteca);
			}
			return mnsg;
		}
	}


	private Musiteca findMusiteca(Usuario usuario) {
		long idMusiteca = usuario.getMusiteca().getId();
		Musiteca musiteca = this.musitecaRepository.findOne(idMusiteca);
		return musiteca;
	}


	private Usuario findUsuario(String emailUsuario) {
		for (Usuario usuario : (List<Usuario>) usuarioRepository.findAll()) {
			if (usuario.getEmail().equalsIgnoreCase(emailUsuario)) {
				return usuario;
			}
		}
		return null;
	}
		
	private boolean existeEmail(String email) {

		boolean emailExiste = false;
		for (Usuario usuario : (List<Usuario>) usuarioRepository.findAll()) {
			if (usuario.getEmail().equalsIgnoreCase(email)) {
				emailExiste = true;
			}
		}
		return emailExiste;
	}
	
	@RequestMapping(value = "/addFavoritos", method = RequestMethod.GET)
	public String addFavorito(@RequestParam(value="nome") String nome,
			@RequestParam(value= "emailUsuario") String emailUsuario) {
		Usuario usuario = findUsuario(emailUsuario);
		if (usuario == null) {
			return "Você precisa estar logado para adicionar um artista aos favoritos.";
		} else {
			Musiteca musiteca = findMusiteca(usuario);
			Artista artista = usuario.getArtista(nome);
			String mnsg = usuario.addFavoritos(artista);
			if (mnsg.equals("Artista adicionado aos favoritos.")) {
				musiteca.addFavorito(artista);
				this.musitecaRepository.save(musiteca);
			}
			return mnsg;
		}
	}
	
	@RequestMapping(value = "/adicionarMusicasPlaylists", method = RequestMethod.GET)
	public String addMusicasPlaylist(@RequestParam(value="nomeMusica") String nomeMusica,
			@RequestParam(value= "nomePlaylist") String nomePlaylist,
			@RequestParam(value= "emailUsuario") String emailUsuario) {
		Usuario usuario = findUsuario(emailUsuario);
		Musiteca musiteca = findMusiteca(usuario);
		if (usuario == null) {
			return "Você precisa estar logado para adicionar um artista aos favoritos.";
		} else {
			String mnsg = musiteca.addMusicaPlaylist(nomeMusica, nomePlaylist);
			if (mnsg.equals("Música adicionada com sucesso.")) {
				usuario.addMusicaPlaylist(nomeMusica, nomePlaylist);
				this.musitecaRepository.save(musiteca);
			}
			return mnsg;
		}
	}
	
	@RequestMapping(value = "/addMusica", method = RequestMethod.GET)
	public String addMusica(@RequestParam(value="nome") String nome,
			@RequestParam(value= "duracao") String duracao,
			@RequestParam(value = "ano") int ano, @RequestParam(value = "genero") String genero,
			@RequestParam(value = "albumNome") String albumNome,
			@RequestParam(value = "artistaNome") String artistaNome,
			@RequestParam(value = "emailUsuario") String emailUsuario) {
		Usuario usuario = findUsuario(emailUsuario);
		
		if (usuario == null) {
			return "Você precisa estar logado para adicionar uma música.";
		} else {
			Musiteca musiteca = findMusiteca(usuario);
			Musica musica = new Musica(nome, duracao, ano, genero);
			Album album = new Album(albumNome);
			Artista artista = new Artista(artistaNome, "");
			String mnsg = "";
			
			if (usuario.getMusiteca().containsArtista(artista)) {
				mnsg = "Artista já existe no sistema.";
				Artista artistaE = usuario.getArtista(artista.getNome());
				mnsg += adicionarMusica(musica, album, artistaE, usuario, musiteca);
				this.artistaRepository.save(artistaE);
				
			} else {
				usuario.addArtista(artista);
				musiteca.addArtista(artista);
				mnsg = "Artista cadastrado(a) com sucesso";
				mnsg += adicionarMusica(musica, album, artista, usuario, musiteca);
				this.artistaRepository.save(artista);
			}
			this.musitecaRepository.save(musiteca);
			return mnsg;
		}
	}
	
	public String adicionarMusica(Musica musica, Album album, Artista artista, Usuario usuario, Musiteca musiteca) {
		if (artista.contemAlbum(album) == false) {
			artista.addAlbum(album);
			this.albumRepository.save(album);
			boolean musicaNaoExiste = album.addMusica(musica);
			usuario.adicionarMusica(musica);
			if (musicaNaoExiste) {
				this.musicaRepository.save(musica);
				musiteca.getMusicasMusiteca().add(musica);
			}
			return " e Música cadastrado(a) com sucesso!";
		} else {
			Album albumExistente = artista.getAlbum(album.getNome());
			if (albumExistente.contemMusica(musica) == false) {
				albumExistente.addMusica(musica);
				usuario.adicionarMusica(musica);
				this.musicaRepository.save(musica);
				this.albumRepository.save(albumExistente);
				musiteca.getMusicasMusiteca().add(musica);
				return " e Música cadastrado(a) com sucesso!";
			} else {
				return " e Música já existe no album!";
			}
		}
			
	}
	
	@RequestMapping(value = "/buscarMusicaPlaylists", method = RequestMethod.POST)
	public ResponseEntity<List<Musica>> buscarMusicaPlaylists(@RequestParam(value = "nomeMusica") String nomeMusica,
			@RequestParam(value = "emailUsuario") String emailUsuario) {
		Usuario usuario = findUsuario(emailUsuario);
		if (usuario != null) {
			List<Musica> musica = usuario.getMusicasPorNome(nomeMusica);
			return new ResponseEntity<List<Musica>>(musica, HttpStatus.OK);
		} else {
			List<Musica> musica = new ArrayList<>();
			return new ResponseEntity<List<Musica>>(musica, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/carregarArtistaFavoritos", method = RequestMethod.POST)
	public ResponseEntity<List<Artista>> carregarArtistaFavoritos(
			@RequestParam(value = "emailUsuario") String emailUsuario) {

		Usuario usuario = findUsuario(emailUsuario);
		if (usuario != null) {
			List<Artista> artista = usuario.listaFavoritos();
			return new ResponseEntity<List<Artista>>(artista, HttpStatus.OK);
		} else {
			List<Artista> artista = new ArrayList<Artista>();
			return new ResponseEntity<List<Artista>>(artista, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/carregarPlaylists", method = RequestMethod.POST)
	public ResponseEntity<List<Playlist>> carregarPlaylists(@RequestParam(value = "emailUsuario") String emailUsuario) {

		Usuario usuario = findUsuario(emailUsuario);
		if (usuario != null && usuario.getListaPlaylists().size() != 0) {
			List<Playlist> playlists = usuario.getListaPlaylists();
			return new ResponseEntity<List<Playlist>>(playlists, HttpStatus.OK);
		} else {
			List<Playlist> playlists = new ArrayList<Playlist>();
			return new ResponseEntity<List<Playlist>>(playlists, HttpStatus.OK);
		}

	}
	
	@RequestMapping(value = "/carregarArtista", method = RequestMethod.POST)
	public ResponseEntity<List<Artista>> carregarArtista(@RequestParam(value = "emailUsuario") String emailUsuario) {

		Usuario usuario = findUsuario(emailUsuario);
		if (usuario != null && usuario.getListaPlaylists().size() != 0) {
			List<Artista> artista = usuario.getListaArtista();
			return new ResponseEntity<List<Artista>>(artista, HttpStatus.OK);
		} else {
			List<Artista> artista = new ArrayList<Artista>();
			return new ResponseEntity<List<Artista>>(artista, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rmPlaylists", method = RequestMethod.GET)
	public void rmPlaylists(@RequestParam(value = "listaPlaylist") List<String> listaPlaylist,
			@RequestParam(value = "emailUsuario") String emailUsuario) {
		
		Usuario usuario = findUsuario(emailUsuario);
		Musiteca musiteca = findMusiteca(usuario);
		
		for (String nomeP : listaPlaylist) {
			this.playlistRepository.delete(musiteca.getPlaylist(nomeP));
		}
		usuario.removerPlaylist(listaPlaylist);
		musiteca.removerPlaylists(listaPlaylist);
		musitecaRepository.save(musiteca);
	}
	
	@RequestMapping(value = "/rmMusicasPlaylists", method = RequestMethod.GET)
	public void rmMusicasPlaylists(@RequestParam(value = "nomeMusica") String nomeMusica,
			@RequestParam(value = "nomePlaylist") String nomePlaylist,
			@RequestParam(value = "emailUsuario") String emailUsuario) {
		
		Usuario usuario = findUsuario(emailUsuario);
		usuario.removerMusicaPlaylist(nomeMusica, nomePlaylist);
		Musiteca musiteca = findMusiteca(usuario);
		
		for (Playlist p : musiteca.getListaPlaylists()) {
			if (p.getNome().equalsIgnoreCase(nomePlaylist)) {
				p.removerMusica(nomeMusica);
			}
		}
		this.musitecaRepository.save(musiteca);
	}
	
	@RequestMapping(value = "/buscarArtistaFavorito", method = RequestMethod.POST)
	public ResponseEntity<List<Artista>> buscarArtistaFavorito(@RequestParam(value = "nome") String nome,
			@RequestParam(value = "emailUsuario") String emailUsuario) {

		Usuario usuario = findUsuario(emailUsuario);
		if (usuario != null) {
			List<Artista> artista = usuario.buscarArtistas(nome);
			return new ResponseEntity<List<Artista>>(artista, HttpStatus.OK);
		} else {
			List<Artista> artista = new ArrayList<Artista>();
			return new ResponseEntity<List<Artista>>(artista, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/rmFavoritos", method = RequestMethod.GET)
	public void rmFavoritos(@RequestParam(value = "emailUsuario") String emailUsuario,
			@RequestParam(value = "nome") String nome) {
		Usuario usuario = findUsuario(emailUsuario);
		Musiteca musiteca = findMusiteca(usuario);
		Artista artista = usuario.getArtista(nome);
		usuario.removerFavorito(artista);
		musiteca.removerFavorito(artista);
		this.musitecaRepository.save(musiteca);
	}
	
	@RequestMapping(value = "/addPlaylist", method = RequestMethod.GET)
	public String addPlaylist(@RequestParam(value = "nome") String nome,
			@RequestParam(value = "emailUsuario") String emailUsuario) {
		Usuario usuario = findUsuario(emailUsuario);
		if (usuario == null) {
			return "Você precisa estar logado para adicionar uma playlist.";
		} else {
			Musiteca musiteca = findMusiteca(usuario);
			Playlist playlist = new Playlist(nome);
			String mnsg = usuario.addPlaylist(playlist);
			if (mnsg == "Playlist cadastrado(a) com sucesso.") {
				musiteca.addPlaylist(playlist);
				playlistRepository.save(playlist);
				this.musitecaRepository.save(musiteca);
			}
			return mnsg;
		}
	}
	
	@RequestMapping(value = "/loginUsuario", method = RequestMethod.POST)
	public ResponseEntity<Usuario> loginUsuario(@RequestParam(value = "nome") String nome,
			@RequestParam(value = "email") String email, @RequestParam(value = "senha") String senha) {
		Usuario usuario = logar(email, senha);
		if (usuario == null) {
			return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	private Usuario logar(String email, String senha) {
		for (Usuario usuario : (List<Usuario>) usuarioRepository.findAll()) {
			if (usuario.getEmail().equalsIgnoreCase(email)) {
				if (usuario.getSenha().equalsIgnoreCase(senha)) {
					return usuario;
				}
			}
		}
		return null;
	}
	
	@RequestMapping(value = "/cadastrarUsuario", method = RequestMethod.GET)
	public String cadastrarUsuario(@RequestParam(value = "nome") String nome,
			@RequestParam(value = "email") String email, @RequestParam(value = "senha") String senha) {
		if (existeEmail(email)) {
			return "Email já existe no sistema!";
		} else {
			Usuario usuario;
			try {
				usuario = new Usuario(nome, email, senha);
				usuario.setMusiteca(null);
				Musiteca musiteca = usuario.getMusiteca();
				this.usuarioRepository.save(usuario);
				//this.musitecaRepository.save(musiteca);
				return "Usuario cadastrado(a) com sucesso!";
			} catch (StringInvalidaException e) {
				return e.getMessage();
			}

		}

	}
	

}
