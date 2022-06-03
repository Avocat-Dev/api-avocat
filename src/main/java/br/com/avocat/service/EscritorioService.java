package br.com.avocat.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.avocat.persistence.model.Escritorio;
import br.com.avocat.persistence.repository.EscritorioRepository;
import br.com.avocat.web.response.EscritorioResponse;

@Service
public class EscritorioService {

	@Autowired
	private EscritorioRepository escritorioRepository;

	@Transactional
	public Optional<EscritorioResponse> save(Escritorio escritorio) {		
		try {
			var result = escritorioRepository.save(escritorio);
			
			if(result != null)
				return Optional.of(new EscritorioResponse(result));
			else
				return Optional.empty();
				
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	public Optional<EscritorioResponse> get(Long id) {
		var result = escritorioRepository.findById(id);
		
		if(result.isPresent())
			return Optional.of(new EscritorioResponse(result.get()));
		else 
			return Optional.empty();
	}
}
