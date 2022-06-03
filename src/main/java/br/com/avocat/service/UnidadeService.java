package br.com.avocat.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.avocat.persistence.model.Unidade;
import br.com.avocat.persistence.repository.UnidadeRepository;
import br.com.avocat.web.response.UnidadeResponse;

@Service
public class UnidadeService {

	@Autowired
	private UnidadeRepository unidadeRepository;

	@Transactional
	public Optional<UnidadeResponse> save(Unidade unidade) {
		
		try {			
			var result = unidadeRepository.save(unidade);
			
			if(result != null)
				return Optional.of(new UnidadeResponse(result));
			else	
				return Optional.empty();			
		
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Optional<UnidadeResponse> get(Long id) {
		var result = unidadeRepository.findById(id);
		
		if(result.isPresent())
			return Optional.ofNullable(new UnidadeResponse(result.get()));
		else
			return Optional.empty();
	}

	public List<UnidadeResponse> all() {
		var result = unidadeRepository.findAll();		
		return result.stream().map(UnidadeResponse::new).collect(Collectors.toList());
	}

}
