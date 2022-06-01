package br.com.avocat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.avocat.persistence.model.Escritorio;
import br.com.avocat.persistence.repository.EscritorioRepository;
import br.com.avocat.web.dto.EscritorioDto;

@Service
public class EscritorioService {

	@Autowired
	private EscritorioRepository escritorioRepository;

	@Autowired
	private ObjectMapper objectMapper;
	
	public EscritorioDto save(EscritorioDto escritorio) {
		var convert = objectMapper.convertValue(escritorio, Escritorio.class);
		
		var result = escritorioRepository.save(convert);
		
		return objectMapper.convertValue(result, EscritorioDto.class);
	}

	public EscritorioDto get(Long id) {
		var result = escritorioRepository.findById(id);
		return objectMapper.convertValue(result.get(), EscritorioDto.class);
	}
}
