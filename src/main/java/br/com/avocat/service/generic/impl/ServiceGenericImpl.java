package br.com.avocat.service.generic.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.avocat.persistence.model.generic.GenericEntity;
import br.com.avocat.persistence.repository.generic.GenericRepository;
import br.com.avocat.service.generic.ServiceGeneric;

@Service
public class ServiceGenericImpl<T extends GenericEntity> implements ServiceGeneric<T> {

	@Autowired
	protected GenericRepository<T> genericRepository;
	
	@Override
	public List<T> all() {
		return genericRepository.findAll();
	}

	@Override
	public Optional<T> get(Long id) {		
		var result = genericRepository.findById(id);
		
		if(result.isPresent())
			return Optional.ofNullable(result.get());
		else
			return Optional.empty();
	}
	
	@Override
	public Optional<T> save(T entity) {
		try {												
			var result = genericRepository.save(entity);		
			
			if(result != null)
				return Optional.of(entity);
			else	
				return Optional.empty();			
		
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void del(Long id) {
		genericRepository.deleteById(id);
	}
}
