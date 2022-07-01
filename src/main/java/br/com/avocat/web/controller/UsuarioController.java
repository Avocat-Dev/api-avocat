package br.com.avocat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping("/conta")
	public ResponseEntity<UsuarioResponse> novaConta(@RequestBody Usuario data) {
		var result = usuarioService.novaConta(data);
		return ControllerUtil.resolve(result);
	}
	
	@PutMapping
	public ResponseEntity<UsuarioDadosResponse> update(@RequestBody UsuarioDados data) {
		var result = usuarioService.update(data);
		return ControllerUtil.resolve(result);
	}
}
