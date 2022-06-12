package br.com.avocat.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.avocat.persistence.model.UsuarioDados;

@Repository
public interface UsuarioDadosRepository extends JpaRepository<UsuarioDados, Long>{	
}
