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

import br.com.avocat.persistence.model.Unidade;
import br.com.avocat.service.UnidadeService;
import br.com.avocat.util.ControllerUtil;

@RestController
@RequestMapping("/v1/unidades")
public class UnidadeController {

	@Autowired
	private UnidadeService unidadeService;

	@PostMapping
	public ResponseEntity<Unidade> save(@RequestBody Unidade data) {
		var result = unidadeService.save(data);
		return ControllerUtil.resolveBadRequest(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Unidade> get(@PathVariable("id") final Long id) {
		var result = unidadeService.get(id);
		return ControllerUtil.resolveNotFound(result);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Unidade>> all() {
		var result = unidadeService.all();
		return ControllerUtil.resolveAll(result);
	}
}
