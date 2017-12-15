package br.com.sistema.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.sistema.biblioteca.Musiteca;

@Repository
public interface MusitecaRepository extends CrudRepository<Musiteca, Long>{
	List<Musiteca> findByid(long id);
}
