package br.com.sistema.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.sistema.biblioteca.Musica;

@Repository
public interface MusicaRepository extends CrudRepository<Musica, Long>{
	List<Musica> findByid(long id);
}
