package br.com.avocat.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.avocat.persistence.model.Contrato;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long>{

}
