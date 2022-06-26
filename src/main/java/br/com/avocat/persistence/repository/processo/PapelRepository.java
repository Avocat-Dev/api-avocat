package br.com.avocat.persistence.repository.processo;

import org.springframework.stereotype.Repository;

import br.com.avocat.persistence.model.processo.Papel;
import br.com.avocat.persistence.repository.generic.GenericRepository;

@Repository
public interface PapelRepository extends GenericRepository<Papel> {

}
