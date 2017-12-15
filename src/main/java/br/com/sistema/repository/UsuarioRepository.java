package br.com.sistema.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.sistema.biblioteca.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
	List<Usuario> findByid(long id);
	
}
