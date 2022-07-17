package br.com.avocat.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.avocat.exception.AvocatException;
import br.com.avocat.persistence.model.Grupo;
import br.com.avocat.service.GrupoService;
import br.com.avocat.util.ConstantesUtil;
import br.com.avocat.util.ControllerUtil;

@RestController
@RequestMapping(ConstantesUtil.PATH_USUARIO_V1 + "/grupos")
public class GrupoController {

	private static final Logger LOGGER = LogManager.getLogger(GrupoController.class);

	@Autowired
	private GrupoService grupoService;

	@PostMapping
	public ResponseEntity<Grupo> save(@RequestBody Grupo grupo) {

		try {
			var result = grupoService.save(grupo);
			return ControllerUtil.resolve(result);
			
		} catch (Exception e) {
			LOGGER.error("Erro ao salvar escritorio ", e);
			throw new AvocatException(e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Grupo> get(Long id) {
		var result = grupoService.get(id);

		if (result.isEmpty())
			return ControllerUtil.resolveNotFound();

		return ControllerUtil.resolve(result);
	}
}
