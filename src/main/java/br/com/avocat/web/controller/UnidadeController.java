package br.com.avocat.web.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.avocat.exception.AvocatException;
import br.com.avocat.persistence.model.Unidade;
import br.com.avocat.service.UnidadeService;
import br.com.avocat.util.ConstantesUtil;
import br.com.avocat.util.ControllerUtil;
import br.com.avocat.web.response.UnidadeResponse;

@RestController
@RequestMapping(ConstantesUtil.PATH_ADMINISTRATIVO_V1 + "/unidades")
public class UnidadeController {

	private static final Logger LOGGER = LogManager.getLogger(UnidadeController.class);

	@Autowired
	private UnidadeService unidadeService;

	@PostMapping
	public ResponseEntity<UnidadeResponse> save(@RequestBody Unidade unidade) {
		
		try {
			var result = unidadeService.save(unidade);
			return ControllerUtil.resolve(result);

		} catch (Exception e) {
			LOGGER.error("Erro ao salvar uniade ", e);
			throw new AvocatException(e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<UnidadeResponse> get(@PathVariable("id") final Long id) {

		try {
			var result = unidadeService.get(id);

			if (result.isEmpty())
				return ControllerUtil.resolveNotFound();

			return ControllerUtil.resolve(result);

		} catch (Exception e) {
			LOGGER.error("Erro ao buscar unidade ", e);
			throw new AvocatException(e.getMessage());
		}
	}

	@GetMapping("/all")
	public ResponseEntity<List<UnidadeResponse>> all() {
		
		try {
			var result = unidadeService.all();
			return ControllerUtil.resolveAll(result);

		} catch (Exception e) {
			LOGGER.error("Erro ao buscar unidades ", e);
			throw new AvocatException(e.getMessage());
		}
	}
}
