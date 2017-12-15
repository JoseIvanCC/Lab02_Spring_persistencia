package br.com.sistema.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.sistema.biblioteca.Artista;

@Repository
public interface ArtistaRepository extends CrudRepository<Artista, Long>{
	List<Artista> findByid(long id);
}
