package br.com.sistema.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.sistema.biblioteca.Album;

@Repository
public interface AlbumRepository extends CrudRepository<Album, Long>{
	List<Album> findByid(long id);
}
