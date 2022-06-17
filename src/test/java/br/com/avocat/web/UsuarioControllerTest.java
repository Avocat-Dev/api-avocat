package br.com.avocat.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import br.com.avocat.persistence.model.Usuario;
import br.com.avocat.persistence.model.UsuarioDados;
import br.com.avocat.util.PathUtil;
import br.com.avocat.web.request.LoginRequest;
import br.com.avocat.web.response.TokenResponse;
import br.com.avocat.web.response.UsuarioDadosResponse;
import br.com.avocat.web.response.UsuarioResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	String token;

	@BeforeEach
	public void setUp()  throws Exception {

		//@formatter:off
		URI uri = new URI(PathUtil.LOCAL_HOST + port + PathUtil.PATH_AUTH_TOKEN);
		
		HttpEntity<LoginRequest> request = new HttpEntity<>(new LoginRequest("dev@dev.com.br", "123"));
		ResponseEntity<TokenResponse> response = this.restTemplate.exchange(uri,HttpMethod.POST, request, TokenResponse.class);

		token = response.getBody().getToken();
		//@formatter:on
	}
	
	@Test
	public void criarNovoUsuario_entao200() throws Exception {
		
		URI uri = new URI(PathUtil.LOCAL_HOST + port + PathUtil.PATH_USUARIOS + "/conta");		

		HttpEntity<Usuario> request = new HttpEntity<>(getUsuario());
		ResponseEntity<UsuarioResponse> result = this.restTemplate.exchange(uri, HttpMethod.POST, request, UsuarioResponse.class); 
		assertEquals(result.getStatusCodeValue(), 200);
	}
	
	@Test
	public void atualizarUsuarioDados_entao200() throws Exception {

		URI uri = new URI(PathUtil.LOCAL_HOST + port + PathUtil.PATH_USUARIOS);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<UsuarioDados> request = new HttpEntity<>(getUsuarioPut(), headers);
		ResponseEntity<UsuarioDadosResponse> result = this.restTemplate.exchange(uri,HttpMethod.PUT, request, UsuarioDadosResponse.class); 
		assertEquals(result.getStatusCodeValue(), 200);				
	}
	
	private Usuario getUsuario() {
		Usuario usuario = new Usuario();				
		usuario.setPassword("123");
		usuario.setUsername("dev@dev.com.br");
		return usuario;
	}
	
	private UsuarioDados getUsuarioPut() {
		UsuarioDados usuarioDados = new UsuarioDados();		
		usuarioDados.setNome("Michael Sousa");
		usuarioDados.setEmail("dev@dev.com.br");
		usuarioDados.setCelular("11999880099");
		usuarioDados.setUsuarioId(1L);
		usuarioDados.setUsuario(null);
		return usuarioDados;
	}
}
