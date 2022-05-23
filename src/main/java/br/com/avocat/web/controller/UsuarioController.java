package br.com.avocat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.avocat.service.UsuarioService;
import br.com.avocat.web.dto.LoginDto;
import br.com.avocat.web.dto.UsuarioDto;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController {

	@Autowired
	UsuarioService acessoService;
	
	@PostMapping("/nova-conta")
	public ResponseEntity<UsuarioDto> salvar(@RequestBody final LoginDto data) {
		return ResponseEntity.ok(acessoService.salvar(data));
	}
}
