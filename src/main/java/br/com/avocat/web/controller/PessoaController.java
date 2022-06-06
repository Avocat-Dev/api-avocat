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

import br.com.avocat.persistence.model.Pessoa;
import br.com.avocat.service.PessoaService;
import br.com.avocat.util.ControllerUtil;
import br.com.avocat.web.response.PessoaResponse;

@RestController
@RequestMapping("/v1/pessoas")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;

	@PostMapping
	public ResponseEntity<PessoaResponse> save(@RequestBody Pessoa data) {
		var result = pessoaService.save(data);					
		return ControllerUtil.resolve(result);		
	}

	@GetMapping("/{id}")
	public ResponseEntity<PessoaResponse> get(@PathVariable("id") final Long id) {
		var result = pessoaService.get(id);		
		
		if(result.isEmpty())
			return ControllerUtil.resolveNotFound();
		
		return ControllerUtil.resolve(result);
	}

	@GetMapping("/all")
	public ResponseEntity<List<PessoaResponse>> all() {
		var result = pessoaService.all();						
		return ControllerUtil.resolveAll(result);
	}
}
