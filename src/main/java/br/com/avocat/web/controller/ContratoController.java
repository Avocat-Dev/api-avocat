package br.com.avocat.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.avocat.persistence.model.Contrato;
import br.com.avocat.service.ContratoService;
import br.com.avocat.util.ControllerUtil;
import br.com.avocat.web.response.ContratoResponse;

@RestController
@RequestMapping("/v1/contratos")
public class ContratoController {

	@Autowired
	private ContratoService contratoService;

	@PostMapping
	public ResponseEntity<ContratoResponse> save(@RequestBody Contrato data) {
		var result = contratoService.save(data);					
		return ControllerUtil.resolve(result);		
	}

	@GetMapping("/{id}")
	public ResponseEntity<ContratoResponse> get(@PathVariable("id") final Long id) {
		var result = contratoService.get(id);		
		
		if(result.isEmpty())
			return ControllerUtil.resolveNotFound();
		
		return ControllerUtil.resolve(result);
	}

	@GetMapping("/all")
	public ResponseEntity<List<ContratoResponse>> all() {
		var result = contratoService.all();						
		return ControllerUtil.resolveAll(result);
	}
}
