package br.com.avocat.persistence.repository.processo;

import org.springframework.stereotype.Repository;

import br.com.avocat.persistence.model.processo.FaseProcessual;
import br.com.avocat.persistence.repository.generic.GenericRepository;

@Repository
public interface FaseProcessualRepository extends GenericRepository<FaseProcessual> {

}
