package br.com.sistema.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.sistema.biblioteca.Playlist;

@Repository
public interface PlaylistRepository extends CrudRepository<Playlist, Long>{
	List<Playlist> findByid(long id);
}
