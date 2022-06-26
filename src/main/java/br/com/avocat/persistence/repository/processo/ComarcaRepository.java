package br.com.avocat.persistence.repository.processo;

import org.springframework.stereotype.Repository;

import br.com.avocat.persistence.model.processo.Comarca;
import br.com.avocat.persistence.repository.generic.GenericRepository;

@Repository
public interface ComarcaRepository extends GenericRepository<Comarca> {

}
