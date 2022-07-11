package br.com.avocat.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.avocat.exception.AvocatException;
import br.com.avocat.persistence.model.Unidade;
import br.com.avocat.persistence.repository.EscritorioRepository;
import br.com.avocat.persistence.repository.UnidadeRepository;
import br.com.avocat.util.ObjetoUtil;
import br.com.avocat.web.response.UnidadeResponse;

@Service
public class UnidadeService {

	@Autowired
	private EscritorioRepository escritorioRepository;

	@Autowired
	private UnidadeRepository unidadeRepository;

	@Transactional
	public Optional<UnidadeResponse> save(Unidade unidade) {

		validar(unidade);
		
		var escritorio = escritorioRepository.findById(unidade.getEscritorioId());
		
		if(escritorio.isPresent()) {
			
			unidade.setEscritorio(escritorio.get());
			
			var result = unidadeRepository.save(unidade);
			
			return Optional.of(new UnidadeResponse(result));
			
		} else {
			
			return Optional.empty();
		}
	}

	public Optional<UnidadeResponse> get(Long id) {
		
		var result = unidadeRepository.findById(id);

		if (result.isPresent())
			return Optional.ofNullable(new UnidadeResponse(result.get()));
		else
			return Optional.empty();
	}

	public List<UnidadeResponse> all() {
		
		var result = unidadeRepository.findAll();
		
		return result.stream().map(UnidadeResponse::new).collect(Collectors.toList());
	}

	private void validar(Unidade unidade) {
		
		ObjetoUtil.verifica(unidade.getCnpj()).orElseThrow(() -> 
			new AvocatException("CNPJ ou CPF não deve ser nulo ou vazio.")
		);
		
		ObjetoUtil.verifica(unidade.getEmail()).orElseThrow(() -> 
			new AvocatException("E-mail da unidade não deve ser nulo ou vazio.")
		);
		
		ObjetoUtil.verifica(unidade.getEscritorio()).orElseThrow(() -> 
			new AvocatException("Unidade deve ter um escritório.")
		);
		
		ObjetoUtil.verifica(unidade.getRazaoSocial()).orElseThrow(() -> 
			new AvocatException("Razão social não deve ser nulo ou vazio.")
		);
		
		ObjetoUtil.verifica(unidade.getNomeUnidade()).orElseThrow(() -> 
			new AvocatException("Nome da unidade não deve ser nulo ou vazio.")
		);
	}
}
