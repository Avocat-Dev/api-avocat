package br.com.avocat.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.avocat.persistence.model.UsuarioDados;

@Repository
public interface UsuarioDadosRepository extends JpaRepository<UsuarioDados, Long>{	
	
	@Query(value = "select * from usuarios_dados where usuario_id = :id", nativeQuery = true)
	Optional<UsuarioDados> findByUsuarioId(@Param("id") Long id);
}
