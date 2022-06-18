package br.com.avocat.service.generic;

import java.util.List;
import java.util.Optional;

import br.com.avocat.persistence.model.generic.GenericEntity;

public interface ServiceGeneric<T extends GenericEntity> {
	List<T> all();
	Optional<T> save(T entity);
	void del(Long id);
}
