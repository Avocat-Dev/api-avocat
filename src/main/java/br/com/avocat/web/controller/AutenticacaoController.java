package br.com.avocat.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.avocat.exception.AvocatException;
import br.com.avocat.spring.jwt.JwtTokenProvider;
import br.com.avocat.util.ConstantesUtil;
import br.com.avocat.util.ObjetoUtil;
import br.com.avocat.web.request.LoginRequest;
import br.com.avocat.web.response.TokenResponse;

@RestController
@RequestMapping(ConstantesUtil.PATH_AUTH_V1)
public class AutenticacaoController {

	private static final Logger LOGGER = LogManager.getLogger(AutenticacaoController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@PostMapping("/token")
	public ResponseEntity<TokenResponse> auth(@RequestBody LoginRequest login) {

		validar(login);
		
		try {

			var username = login.getUsername();

			var authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, login.getPassword()));

			var token = jwtTokenProvider.gerarToken(authentication);

			return ResponseEntity.ok(TokenResponse.builder().type("Bearer").token(token).build());

		} catch (Exception e) {
			LOGGER.error("Erro ao salvar pessoa ", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	private void validar(LoginRequest login) {
		
		ObjetoUtil.verifica(login.getUsername()).orElseThrow(() ->
			new AvocatException("Username não pode ser nulo o vazio")
		);
		
		ObjetoUtil.verifica(login.getPassword()).orElseThrow(() ->
			new AvocatException("Password não pode ser nulo o vazio")
		);
	}
}
