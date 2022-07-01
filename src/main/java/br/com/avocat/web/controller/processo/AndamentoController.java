package br.com.avocat.web.controller.processo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.avocat.persistence.model.processo.Andamento;
import br.com.avocat.service.processo.AndamentoService;
import br.com.avocat.util.ConstantesUtil;
import br.com.avocat.util.ControllerUtil;
import br.com.avocat.web.response.AndamentoResponse;

@RestController
@RequestMapping(ConstantesUtil.PATH_PROCESSO_V1 + "/andamentos")
public class AndamentoController {

	@Autowired
	private AndamentoService andamentoService;

	@PostMapping
	public ResponseEntity<AndamentoResponse> save(@RequestBody Andamento data) {
		var result = andamentoService.save(data);
		return ControllerUtil.resolve(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AndamentoResponse> get(@PathVariable("id") final Long id) {
		var result = andamentoService.get(id);
		if (result.isEmpty())
			return ControllerUtil.resolveNotFound();

		return ControllerUtil.resolve(result);
	}

	@GetMapping("/all")
	public ResponseEntity<List<AndamentoResponse>> all() {
		var result = andamentoService.all();
		return ControllerUtil.resolveAll(result);
	}
}
