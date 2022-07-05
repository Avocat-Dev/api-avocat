package br.com.avocat.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.avocat.persistence.model.Grupo;
import br.com.avocat.persistence.repository.GrupoRepository;

@Service
public class GrupoService {

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Transactional
	public Optional<Grupo> save(Grupo grupo) {
		
		try {						
			var result = grupoRepository.save(grupo);
			
			if(result != null)
				return Optional.of(result);
			else	
				return Optional.empty();			
		
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Optional<Grupo> get(Long id) {
		var result = grupoRepository.findById(id);
		
		if(result.isPresent())
			return Optional.ofNullable(result.get());
		else
			return Optional.empty();
	}
}
