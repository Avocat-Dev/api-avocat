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

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.avocat.persistence.model.Usuario;
import br.com.avocat.persistence.model.UsuarioDados;
import br.com.avocat.util.PathUtil;
import br.com.avocat.web.request.LoginRequest;
import br.com.avocat.web.response.TokenResponse;
import br.com.avocat.web.response.UsuarioDadosResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UsuarioControllerTest {

	static final String CONTEXT_PATH = "/v1/usuarios";
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
		
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
	/*
	@Test
	public void criarNovoUsuario_entao200() throws Exception {
		//@formatter:off
		this.mockMvc
			.perform(
					post(CONTEXT_PATH + "/conta")
					.content(this.objectMapper.writeValueAsBytes(getUsuario()))
					.contentType(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.id").isNotEmpty());					
		//@formatter:on
	}
	*/
	
	@Test
	public void atualizarUsuarioDados_entao200() throws Exception {
		//@formatter:off
		URI uri = new URI(PathUtil.LOCAL_HOST + port + CONTEXT_PATH);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<UsuarioDados> request = new HttpEntity<>(getUsuarioPut(), headers);
		ResponseEntity<UsuarioDadosResponse> result = this.restTemplate.exchange(uri,HttpMethod.PUT, request, UsuarioDadosResponse.class);
		
		assertEquals(result.getStatusCodeValue(), 200);				
		//@formatter:on
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
		usuarioDados.setEmail("brasil@gmail.com");
		usuarioDados.setCelular("11999880099");
		usuarioDados.setUsuarioId(1L);
		usuarioDados.setUsuario(null);
		return usuarioDados;
	}
}
