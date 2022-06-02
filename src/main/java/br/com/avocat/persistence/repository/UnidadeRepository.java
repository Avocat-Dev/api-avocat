package br.com.avocat.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.avocat.persistence.model.Unidade;

@Repository
public interface UnidadeRepository extends JpaRepository<Unidade, Long>{

}
