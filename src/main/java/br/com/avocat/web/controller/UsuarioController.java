package br.com.avocat.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.avocat.exception.AvocatException;
import br.com.avocat.persistence.model.Usuario;
import br.com.avocat.persistence.model.UsuarioDados;
import br.com.avocat.service.UsuarioService;
import br.com.avocat.util.ConstantesUtil;
import br.com.avocat.util.ControllerUtil;
import br.com.avocat.web.response.UsuarioDadosResponse;
import br.com.avocat.web.response.UsuarioResponse;

@RestController
@RequestMapping(ConstantesUtil.PATH_USUARIO_V1)
public class UsuarioController {

	private static final Logger LOGGER = LogManager.getLogger(UsuarioController.class);
	
	@Autowired
	private UsuarioService usuarioService;

	@PostMapping("/conta")
	public ResponseEntity<UsuarioResponse> novaConta(@RequestBody Usuario usuario) {
		
		try {
			var result = usuarioService.novaConta(usuario);
			return ControllerUtil.resolve(result);
			
		} catch (Exception e) {
			LOGGER.error("Erro ao salvar o usuário ", e);
			throw new AvocatException(e.getMessage());
		}
	}

	@PutMapping
	public ResponseEntity<UsuarioDadosResponse> update(@RequestBody UsuarioDados usuarioDados) {
		
		try {
			var result = usuarioService.update(usuarioDados);
			return ControllerUtil.resolve(result);
			
		} catch (Exception e) {
			LOGGER.error("Erro ao atualizar o usuário ", e);
			throw new AvocatException(e.getMessage());
		}
	}
}
