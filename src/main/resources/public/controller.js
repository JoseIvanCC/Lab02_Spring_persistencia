angular.module("Scgfy", []);
angular.module("Scgfy").controller("ScgfyCtrl", function ($scope, $http) {
            $scope.deslogar = "Você quer sair da sua conta? Clique em OK para continuar.";
            $scope.mensagemDeExclusaoPlaylist = "Você está apagando playlist(s). Clique em OK para continuar!";
            $scope.mensagemDeExclusaoMusicaPlaylist = "Você está apagando uma música de uma playlist. Clique em OK para continuar!";
            $scope.mensagemDeExclusaoFavoritos = "Você está apagando um artista da sua lista de favoritos. Clique em OK para continuar!";
            $scope.listaUsuarios = [];
            $scope.listaBusca = [];
	          $scope.listaBuscaPlaylist = [];
            $scope.listaplaylists = [];
            $scope.app = "Scgfy";
            $scope.artistas = [];
            $scope.albuns = [];
            $scope.musicas = [];
            $scope.playlists = [];
            $scope.favoritos = [];
            $scope.selected;


$scope.select = function(selected) {
    $scope.selected = selected;
}

$scope.listaplaylists = [];
	var httpConfigs={

		headers:{
			'Accept':'text/plain, application/json, */*'
		}
	}

//logado
	$scope.logado = false;
	$scope.usuario;

$scope.sair=function(){
		var decisao = confirm($scope.deslogar);
		if (decisao){
			$scope.usuario = undefined;
		    $scope.logado = false;
		    $scope.artistas = [];
		    $scope.musicas = [];
		}
	}

$scope.acessarMusiteca=function(usr){
var baseURL = 'http://silab3ufcg.herokuapp.com/loginUsuario?';
		baseURL = baseURL+"email="+usr.email;
		baseURL = baseURL+"&senha="+usr.senha;
		$http.post(baseURL, httpConfigs).then(function(response){
			$scope.usuario = response.data;
			$scope.logado = true;
			$scope.getArtistas();
			$scope.loadMusicas();
			alert("Seja bem vindo!");
        }).catch(function(error){
        	console.log(error);
            alert("Email ou senha incorretos.")});
            delete $scope.usr;
		$scope.formularioLogin.$setPristine();
	};

  $scope.loadMusicas = function(){
  	var baseURL = 'http://silab3ufcg.herokuapp.com/carregarMusicas?';
  	baseURL = baseURL + "emailUsuario=" + $scope.usuario.email;
  	$http.get(baseURL, httpConfigs).then(function(response){$scope.musicas = response.data;}).catch(function (error) {});

  }
  $scope.criarConta = function(usuario){
		var baseURL = 'http://silab3ufcg.herokuapp.com/cadastrarUsuario?';
		baseURL = baseURL+"nome="+usuario.nome;
		baseURL = baseURL+"&email="+usuario.email;
		baseURL = baseURL+"&senha="+usuario.senha;
		$http.get(baseURL, httpConfigs).then(function(response){alert(response.data)}).catch(function(error){alert(error.data)});
		delete $scope.usuario;
		$scope.formularioCadastro.$setPristine();
	};

  $scope.carregarPlaylists = function() {
    var baseURL = 'http://silab3ufcg.herokuapp.com/carregarPlaylists?';
    if ($scope.logado == true) {
      baseURL = baseURL+"emailUsuario="+$scope.usuario.email
    } else {
      baseURL = baseURL+"emailUsuario="+"?";
    }
    $http.get(baseURL, httpConfigs).then(function(response){alert(response.data); $scope.listaplaylists = response.data}).catch(function(error){alert(error.data)});
  }

  $scope.getArtistas = function() {
    var baseURL = 'http://silab3ufcg.herokuapp.com/carregarArtista?';
    if ($scope.logado == true) {
      baseURL = baseURL+"emailUsuario="+$scope.usuario.email;
    } else {
      baseURL = baseURL+"emailUsuario="+"?";
    }
    $http.get(baseURL, httpConfigs).then(function(response){$scope.artistas = response.data}).catch(function(error){alert("Não foi possível carregar Artistas.")});//esse alert aparece para o usuário e pode ser muito chato, não faz nada
  //$scope.listaArtistas= response.data isso aqui poderia ser um return, return response.data;
  }

$scope.getPlaylists = function() {
    var baseURL = 'http://silab3ufcg.herokuapp.com/carregarPlaylists?';
    if ($scope.logado == true) {
      baseURL = baseURL+"emailUsuario="+$scope.usuario.email;
    } else {
      baseURL = baseURL+"emailUsuario="+"?";
    }
    $http.push(baseURL, httpConfigs).then(function(response){alert(response.data); return response.data}).catch(function(error){alert(error.data)});//esse alert aparece para o usuário e pode ser muito chato, não faz nada
  //$scope.listaArtistas= response.data isso aqui poderia ser um return, return response.data;
}

  $scope.adicionaMusica = function(musica, album) {
    var baseURL = 'http://silab3ufcg.herokuapp.com/addMusic?';
    baseURL = baseURL+"nome="+musica.nome;
    baseURL = baseURL+"&duracao="+musica.duracao;
    baseURL = baseURL+"&ano="+parseInt(musica.ano);
    baseURL = baseURL+"&albumNome="+album.nome;
    baseURL = baseURL+"&artistaNome="+musica.artista;
    if ($scope.logado == true) {
      baseURL = baseURL+"&emailUsuario="+$scope.usuario.email;
  } else {
      baseURL = baseURL+"$emailUsuario="+"?";
  }
    $http.post(baseURL,httpConfigs).then(function(response){console.log(response.data)}).catch(function(error){error.data});
    delete $scope.album;
    delete $scope.musica;
    delete $scope.artista;
    //$scope.formAddMusica.$setPristine();
  }

  $scope.adicionarArtista = function(nome, foto) {
      var baseURL = 'http://silab3ufcg.herokuapp.com/addArtista?';
      baseURL = baseURL+"nome="+nome;
      baseURL = baseURL+"&foto="+foto;
      if ($scope.logado == true) {
          baseURL = baseURL+"&emailUsuario="+$scope.usuario.email
    } else {
          baseURL = baseURL+"&emailUsuario="+"?";
    }
      $http.post(baseURL, httpConfigs).then(function(response){}).catch(function(error){});
      $scope.getArtistas();
      delete $scope.artista;
      //$scope.formAddArtista.$setPristine();
  }

  $scope.adicionarPlaylist = function(nomePlaylist) {
    var baseURL = 'http://silab3ufcg.herokuapp.com/addPlaylist?';
    baseURL = baseURL+"nome="+nomePlaylist;
    if ($scope.logado == true) {
      baseURL = baseURL+"&emailUsuario="+$scope.usuario.email;
    } else {
      baseURL = baseURL+"&emailUsuario="+"?";
    }
    $http.get(baseURL, httpConfigs).then(function(response){alert(response.data)}).catch(function(error){alert(error.data)});
    delete $scope.playlist;
  }

  $scope.retornaUsuario = function(email){
		for (var i = 0; i < $scope.listaUsuarios.length; i++) {
			if($scope.listaUsuarios[i].emailUsuario.toUpperCase()==email.toUpperCase()){
				return $scope.listaUsuarios[i];
			}
		}
	};

  $scope.excluiPlaylist = function(playlist) {
      var decisao = confirm($scope.mensagemDeExclusaoPlaylist);
      if (decisao) {
        $scope.usuario.musitecaUsuario.removerPlaylist(playlist.nome);
      }
      delete $scope.playlist;
  }

  $scope.isPlaylistSelecionado = function(playlists){
		return $scope.usuario.musitecaUsuario.getListaPlaylists().some(function(playlist){
			return playlist.selecionado;
		});
	};

  $scope.excluiMusicaDaPlaylist = function(playlist, musica) {
    var decisao = confirm($scope.mensagemDeExclusaoMusicaPlaylist);
    if (decisao) {
      $scope.usuario.musitecaUsuario.removerMusicaDaPlaylist(musica.nome, playlist.nome);
    }
    delete $scope.musica;
    delete $scope.playlist;
  }

  $scope.buscarMusicaPlaylists = function(nomeMusica, playlist) {
    $scope.removerBuscaPlaylist();
    $scope.listaBuscaPlaylist = $scope.usuario.musitecaUsuario.getMusicasPorNome(nomeMusica);
    delete $scope.maisMusica;
		delete $scope.playlist;
  }

  $scope.adicionarMusicaNaPlaylist = function(nomeMusica, playlist) {
    $scope.usuario.musitecaUsuario.addMusicaPlaylist(nomeMusica, playlist.nome);
    delete $scope.playlist;
    delete $scope.maisMusica;
  }

  $scope.adicionaAosFavoritos = function(busca){
		$scope.usuario.musitecaUsuario.addFavorito(busca);
		delete $scope.busca;
	};

  $scope.removerBusca=function(){
  		$scope.listaBusca=[];
  	};

  	$scope.removerBuscaPlaylist=function(){
  		$scope.listaBuscaPlaylist=[];
  	};

    /*$scope.excluirFavorito = function(artista) {
      var decisao = confirm($scope.mensagemDeExclusaoFavoritos);
      if (decisao) {
        $scope.usuario.musitecaUsuario.removerFavorito(artista);
      }
    }*/

    $scope.alterarNota = function(nota, artista) {
      var novaNota = parseInt(nota);
      var mensagem = $scope.usuario.musitecaUsuario.alterarNota(novaNota, artista);
      window.alert(mensagem);
    }

    $scope.buscarArtistas = function(buscando) {
      $scope.usuario.musitecaUsuario.buscarArtistas(buscando);
    }

    $scope.adicionarMusicaArtista = function(artista, novaUltimaMusica){
		    var mensagem = $scope.usuario.musitecaUsuario.adicionarMusicaArtista(artista,novaUltimaMusica);
		    window.alert(mensagem);
	 };

//A PARTIR DAQUI
//Criacao do tipo Playlist
    function Playlist(nome){
        this.nome = nome;
        this.musicasPlaylist = [];

        this.adicionaMusica = function(musica){
            this.musicasPlaylist.push(musica);
        }
    //retorna array de musicas da playlist
		this.retornaMusicas = function(){
			return this.musicasPlaylist;
		}
	}

    $scope.adicionaMusicaNaPlaylist = function(musica, nomePlaylist){
            if(!$scope.existePlaylist(nomePlaylist)){
                alert("Não existe essa playlist no sistema");
            }else{
                for(var i = 0; i < $scope.playlists.length; i++){
                    if($scope.playlists[i].nome == nomePlaylist){
                        if(existeMusica($scope.playlists[i].retornaMusicas(), musica.nome)){
                            alert("A playlist já tem uma música com o mesmo nome!");
                        }else{
                            $scope.playlists[i].adicionaMusica(musica);
                            confirm("Deseja adicionar essa música na playlist? Clique em OK para continuar.");
                        }
                    }
                }
            }
        }

//Adiciona playlist no sistema
    $scope.adicionaPlaylist = function(nome){
        if(!$scope.existePlaylist(nome)){
            $scope.playlists.push(new Playlist(nome));
        }else{
            alert("Já existe uma playlist com esse mesmo nome no sistema!")
        }
    }

    //Verifica se uma playlist com o mesmo nome existe no sistema
    $scope.existePlaylist = function(nome){
        for(i = 0; i < $scope.playlists.length; i++){
            if($scope.playlists[i].nome == nome){
                return true;
            }
        }
        return false;
    }

$scope.excluirMusicaDaPlaylist = function (playlist, music) {
  for (i = 0; i < playlist.retornaMusicas().length; i++) {
    if(playlist.retornaMusicas()[i].nome === music.nome) {
      playlist.retornaMusicas().splice(i,1);
      confirm("Deseja excluir essa música da playlist? Clique em OK para continuar.");
    }
  }
};

$scope.excluirPlaylist = function (playlist) {
  for (i = 0; i < $scope.playlists.length; i++) {
    if ($scope.playlists[i].nome === playlist.nome) {
      $scope.playlists.splice(i,1);
      confirm("Deseja excluir essa playlist? Clique em OK para continuar.");
    }
  }
};

$scope.removePlaylists = function(playlists){
  var decisao = confirm($scope.mensagemDeExclusaoPlaylist);
  if (decisao){
    $scope.usuario.musitecaUsuario.removerPlaylists(playlists);
  }
  delete $scope.playlist;
};


//Verifica se existe uma determinada playlist com determinado nome, se existir, retorna uma mensagem alertando da existência, se nao, retorna uma nova playlist vazia



function Album(nomeAlbum, autorAlbum) {
    this.musicas = [];
    this.nomeAlbum = nomeAlbum;
    this.autorAlbum = autorAlbum;

    this.addMusic = function(music) {
      if (existeMusica(this.musicas, music.nome)) {
          alert("Música já existente no álbum");
          delete $scope.musica;
    } else {
          this.musicas.push(music);
          $scope.musicas.push(angular.copy(music));
          delete $scope.musica;
    }
  }
}

function Favorito(nomeFavorito) {
  this.nomeFavorito = nomeFavorito;
}

$scope.adicionarAosFavoritos = function (artist) {
  $scope.favoritos.push(angular.copy(artist));
  confirm("Deseja adicionar esse artistas aos favoritos? Clique em OK para continuar.");
};

const existeArtistaNosFavoritos = function (nomeDoArtista) {
  for (i = 0; i < $scope.favoritos.length; i++) {
    if ($scope.favoritos[i].nome == nomeDoArtista) {
      return $scope.favoritos[i];
    }
  }
  return null;
}

$scope.excluirFavorito = function (favorito) {
  for (i = 0; i < $scope.favoritos.length; i++) {
    if ($scope.favoritos[i].nome == favorito.nome) {
      $scope.favoritos.splice(i,1);
      confirm("Deseja excluir esse artista dos favoritos? Clique em OK para continuar.");
    }
  }
}


//Verifica se existe um determinado album com determinado nome, se existir, retorna o album, se nao, retorna um novo album vazio
retornaAlbum = function (nomeAlbum, autor) {
    for (i = 0; i < $scope.albuns.length; i++) {
      if ($scope.albuns[i].nomeAlbum == nomeAlbum) {
        return $scope.albuns[i];
      }
    }
    var newAlbum = new Album(nomeAlbum, autor);
    $scope.albuns.push(newAlbum);
    return newAlbum;
}

$scope.retornaAlbuns = function (autor) {
  album = [];
  for (i = 0; i < $scope.albuns.length; i++) {
    if ($scope.albuns[i].autorAlbum == autor) {
      album.push($scope.albuns[i]);
    }
  }
  return album;
}

$scope.verificaTable = function (array) {
    return array.length > 0;
}

//Adiciona uma musica a um album que está no array de albuns.
$scope.adicionarMusica = function (music) {
    album = retornaAlbum(music.album, music.autor);
    album.addMusic(angular.copy(music));
    delete music;
}

const existeMusica = function (musicas, nomeMusica) {
    for (i = 0; i < musicas.length; i++) {
        if (musicas[i].nome ===  nomeMusica) {
            return true;
        }
    }
          return false;
}



/*$scope.adicionarArtista = function (artist) {
    if (existeArtista(artist)) {
        alert("artista já existente no sistema");
            } else {
                $scope.artistas.push(angular.copy(artist));
                    delete $scope.artista;
                    }
                  };*/

const existeArtista = function(artist) {
    for (i = 0; i < $scope.artistas.length; i++) {
          if (artist.nome === $scope.artistas[i].nome) {
            return true;
                      }
                  }
                  return false;
                };

$scope.atualizaDadosArtista = function (selectedItem) {
    for (i = 0; i < $scope.artistas.length; i++) {
      if ($scope.artistas[i].nome == selectedItem.nome) {
        $scope.artistas[i].nota = selectedItem.nota;
        $scope.artistas[i].ultimaMusica = selectedItem.ultimaMusica;
      }
    }
}

        });
