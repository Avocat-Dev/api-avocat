package br.com.avocat.persistence.repository.processo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.avocat.persistence.model.processo.Andamento;

@Repository
public interface AndamentoRepository extends JpaRepository<Andamento, Long> {
}
