package br.com.avocat.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.avocat.exception.AvocatException;
import br.com.avocat.persistence.model.Escritorio;
import br.com.avocat.persistence.repository.EscritorioRepository;
import br.com.avocat.util.ObjetoUtil;
import br.com.avocat.web.response.EscritorioResponse;

@Service
public class EscritorioService {

	@Autowired
	private EscritorioRepository escritorioRepository;

	@Transactional
	public Optional<EscritorioResponse> save(Escritorio escritorio) {

		validar(escritorio);

		var result = escritorioRepository.save(escritorio);
		
		return Optional.of(new EscritorioResponse(result));
	}

	public Optional<EscritorioResponse> get(Long id) {
		var result = escritorioRepository.findById(id);

		if (result.isPresent())
			return Optional.of(new EscritorioResponse(result.get()));
		else
			return Optional.empty();
	}

	private void validar(Escritorio escritorio) {
		ObjetoUtil.verifica(escritorio.getNome()).orElseThrow(() -> 
			new AvocatException("Nome do escritório não pode ser nulo o vazio.")
		);
	}
}
