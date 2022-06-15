package br.com.avocat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.avocat.persistence.model.Usuario;
import br.com.avocat.service.UsuarioService;
import br.com.avocat.util.ControllerUtil;
import br.com.avocat.web.response.UsuarioResponse;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService acessoService;
	
	@PostMapping("/conta")
	public ResponseEntity<UsuarioResponse> novaConta(@RequestBody Usuario data) {
		var result = acessoService.novaConta(data);
		return ControllerUtil.resolve(result);
	}
}
