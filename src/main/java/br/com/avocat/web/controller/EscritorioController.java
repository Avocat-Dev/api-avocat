package br.com.avocat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.avocat.service.EscritorioService;
import br.com.avocat.util.ControllerUtil;
import br.com.avocat.web.dto.EscritorioDto;

@RestController
@RequestMapping("/v1/escritorios")
public class EscritorioController {
	
	@Autowired
	private EscritorioService escritorioService;
	
	@PostMapping
	public ResponseEntity<EscritorioDto> save(@RequestBody EscritorioDto data) {
		var result = escritorioService.save(data);		
		return ControllerUtil.resolve(result, EscritorioDto.class);
	}

	@GetMapping
	public ResponseEntity<EscritorioDto> get(Long id) {
		var result = escritorioService.get(id);				
		return ControllerUtil.resolve(result, EscritorioDto.class);
	}
}
