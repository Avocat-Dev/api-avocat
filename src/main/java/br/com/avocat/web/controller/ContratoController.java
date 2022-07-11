package br.com.avocat.web.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.avocat.exception.AvocatException;
import br.com.avocat.persistence.model.Contrato;
import br.com.avocat.service.ContratoService;
import br.com.avocat.util.ConstantesUtil;
import br.com.avocat.util.ControllerUtil;
import br.com.avocat.web.response.ContratoResponse;

@RestController
@RequestMapping(ConstantesUtil.PATH_ADMINISTRATIVO_V1 + "/contratos")
public class ContratoController {

	private static final Logger LOGGER = LogManager.getLogger(ContratoController.class);

	@Autowired
	private ContratoService contratoService;

	@PostMapping
	public ResponseEntity<ContratoResponse> save(@RequestBody Contrato contrato) {
		
		try {
			var result = contratoService.save(contrato);
			return ControllerUtil.resolve(result);
			
		} catch (Exception e) {
			LOGGER.error("Erro ao salvar o contrato ", e);
			throw new AvocatException(e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<ContratoResponse> get(@PathVariable("id") Long id) {
		
		try {
			var result = contratoService.get(id);

			if (result.isEmpty())
				return ControllerUtil.resolveNotFound();

			return ControllerUtil.resolve(result);
			
		} catch (Exception e) {
			LOGGER.error("Erro ao buscar o contrato ", e);
			throw new AvocatException(e.getMessage());
		}
	}

	@GetMapping("/all")
	public ResponseEntity<List<ContratoResponse>> all() {
		
		try {
			var result = contratoService.all();
			return ControllerUtil.resolveAll(result);
			
		} catch (Exception e) {
			LOGGER.error("Erro ao listar os contratos ", e);
			throw new AvocatException(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) {

		try {
			contratoService.delete(id);
		} catch (Exception e) {
			LOGGER.error("Erro ao deletar a pessoa id: " + id, e);
			throw new AvocatException(e.getMessage());
		}

		return ControllerUtil.resolveOk("Contrato de id " + id + "deletado com sucesso!");
	}
}
