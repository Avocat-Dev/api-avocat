package br.com.avocat.persistence.repository.processo;

import br.com.avocat.persistence.model.processo.Processo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long> {
}
