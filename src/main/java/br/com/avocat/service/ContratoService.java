package br.com.avocat.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.avocat.persistence.model.Contrato;
import br.com.avocat.persistence.repository.ContratoRepository;
import br.com.avocat.persistence.repository.PessoaRepository;
import br.com.avocat.web.response.ContratoResponse;

@Service
public class ContratoService {

	@Autowired
	private ContratoRepository contratoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Transactional
	public Optional<ContratoResponse> save(Contrato contrato) {
		
		try {			
			var pessoa = pessoaRepository.findById(contrato.getPessoaId());
			contrato.setPessoa(pessoa.get());
			
			var result = contratoRepository.save(contrato);
			
			if(result != null)
				return Optional.of(new ContratoResponse(result));
			else	
				return Optional.empty();			
		
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Optional<ContratoResponse> get(Long id) {
		var result = contratoRepository.findById(id);
		
		if(result.isPresent())
			return Optional.ofNullable(new ContratoResponse(result.get()));
		else
			return Optional.empty();
	}

	public List<ContratoResponse> all() {
		var result = contratoRepository.findAll();		
		return result.stream().map(ContratoResponse::new).collect(Collectors.toList());
	}
}
