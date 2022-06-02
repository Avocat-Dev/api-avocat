package br.com.avocat.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.avocat.persistence.model.Unidade;
import br.com.avocat.persistence.repository.UnidadeRepository;

@Service
public class UnidadeService {

	@Autowired
	private UnidadeRepository unidadeRepository;

	@Transactional
	public Optional<Unidade> save(Unidade unidade) {
		
		try {
			
			var result = unidadeRepository.save(unidade);
			return Optional.ofNullable(result);			
		
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Optional<Unidade> get(Long id) {
		var result = unidadeRepository.findById(id);		
		return Optional.ofNullable(result.get());
	}

	public List<Unidade> all() {
		return unidadeRepository.findAll();
	}

}
