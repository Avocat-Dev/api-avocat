package br.com.avocat.persistence.repository.processo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.avocat.persistence.model.processo.ValorCausa;

@Repository
public interface ValorCausaRepository extends JpaRepository<ValorCausa, Long>{

}
