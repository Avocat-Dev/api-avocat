package br.com.avocat.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.avocat.persistence.model.Pessoa;
import br.com.avocat.persistence.repository.PessoaRepository;
import br.com.avocat.web.response.PessoaResponse;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Transactional
	public Optional<PessoaResponse> save(Pessoa pessoa) {
		
		try {			
			var result = pessoaRepository.save(pessoa);
			
			if(result != null)
				return Optional.of(new PessoaResponse(result));
			else	
				return Optional.empty();			
		
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Optional<PessoaResponse> get(Long id) {
		var result = pessoaRepository.findById(id);
		
		if(result.isPresent())
			return Optional.ofNullable(new PessoaResponse(result.get()));
		else
			return Optional.empty();
	}

	public List<PessoaResponse> all() {
		var result = pessoaRepository.findAll();		
		return result.stream().map(PessoaResponse::new).collect(Collectors.toList());
	}
}
