package br.com.avocat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.avocat.spring.jwt.JwtTokenProvider;
import br.com.avocat.web.dto.LoginDto;
import br.com.avocat.web.dto.TokenDto;

@RestController
@RequestMapping("/v1/auth")
public class AutenticacaoController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@PostMapping("/token")
	public ResponseEntity<TokenDto> auth(@RequestBody final LoginDto data) {
		
		var username = data.getUsername();
		
		var authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, data.getPassword()));
		
		var token = jwtTokenProvider.gerarToken(authentication);
		
		return ResponseEntity.ok(TokenDto.builder().type("Bearer").token(token).build());
	}
	
	@GetMapping("/eco")
	public ResponseEntity<String> eco() {
		return ResponseEntity.ok().body("Eco!");
	}
}
