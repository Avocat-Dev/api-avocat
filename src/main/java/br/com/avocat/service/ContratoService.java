package br.com.avocat.service;

import br.com.avocat.exception.AvocatException;
import br.com.avocat.persistence.model.Contrato;
import br.com.avocat.persistence.repository.ContratoRepository;
import br.com.avocat.persistence.repository.PessoaRepository;
import br.com.avocat.util.ObjetoUtil;
import br.com.avocat.web.response.ContratoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ContratoService {

	@Autowired
	private ContratoRepository contratoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Transactional
	public Optional<ContratoResponse> save(Contrato contrato) {
			validar(contrato);
		
			var pessoa = pessoaRepository.findById(contrato.getPessoaId());
			
			if(pessoa.isPresent()) {
				contrato.setPessoa(pessoa.get());
				var result = contratoRepository.save(contrato);
				return Optional.of(new ContratoResponse(result));
			} else {
				return Optional.empty();
			}
	}

	public Optional<ContratoResponse> get(Long id) {
		var result = contratoRepository.findById(id);
		return result.map(ContratoResponse::new);
	}

	public List<ContratoResponse> all() {
		var result = contratoRepository.findAll();
		return result.stream().map(ContratoResponse::new).toList();
	}

	@Transactional
	public void delete(Long id) {
		contratoRepository.deleteById(id);
	}

	private void validar(Contrato contrato) {
		
		ObjetoUtil.verifica(contrato.getNomeContrato()).orElseThrow(() -> 
			new AvocatException("Nome do contrato não pode ser nulo o vazio.")
		);
		
		ObjetoUtil.verifica(contrato.getModalidadeId()).orElseThrow(() -> 
			new AvocatException("Modalidade do contrato não pode ser nulo o vazio.")
		);
		
		ObjetoUtil.verifica(contrato.getPessoaId()).orElseThrow(() ->
			new AvocatException("A pessoa do contrato não pode ser nulo o vazio.")
		);
	}
}
