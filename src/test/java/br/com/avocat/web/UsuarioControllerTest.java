package br.com.avocat.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.List;
import java.util.UUID;

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

import br.com.avocat.persistence.model.Grupo;
import br.com.avocat.persistence.model.Role;
import br.com.avocat.persistence.model.Usuario;
import br.com.avocat.persistence.model.UsuarioDados;
import br.com.avocat.util.ConstantesUtil;
import br.com.avocat.web.request.LoginRequest;
import br.com.avocat.web.request.MenuRequest;
import br.com.avocat.web.response.TokenResponse;
import br.com.avocat.web.response.UsuarioDadosResponse;
import br.com.avocat.web.response.UsuarioResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String token;

	@BeforeEach
	public void setUp() throws Exception {

		//@formatter:off
		URI uri = new URI(ConstantesUtil.AMB_LOCAL_HOST + port + ConstantesUtil.PATH_AUTH_V1 + "/token");
		
		HttpEntity<LoginRequest> request = new HttpEntity<>(new LoginRequest("dev@dev.com.br", "123"));
		ResponseEntity<TokenResponse> response = this.restTemplate.exchange(uri,HttpMethod.POST, request, TokenResponse.class);

		token = response.getBody().getToken();
		//@formatter:on
	}

	@Test
	public void criarNovoUsuario_entao200() throws Exception {

		URI uri = new URI(ConstantesUtil.AMB_LOCAL_HOST + port + ConstantesUtil.PATH_USUARIO_V1 + "/conta");

		HttpEntity<Usuario> request = new HttpEntity<>(getUsuario());
		ResponseEntity<UsuarioResponse> result = this.restTemplate.exchange(uri, HttpMethod.POST, request,
				UsuarioResponse.class);
		assertEquals(result.getStatusCodeValue(), 200);
	}

	@Test
	public void atualizarUsuarioDados_entao200() throws Exception {

		URI uri = new URI(ConstantesUtil.AMB_LOCAL_HOST + port + ConstantesUtil.PATH_USUARIO_V1);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<UsuarioDados> request = new HttpEntity<>(getUsuarioPut(), headers);
		ResponseEntity<UsuarioDadosResponse> result = this.restTemplate.exchange(uri, HttpMethod.PUT, request,
				UsuarioDadosResponse.class);
		assertEquals(result.getStatusCodeValue(), 200);
	}

	@Test
	public void criarRole_entao200() throws Exception {

		URI uri = new URI(ConstantesUtil.AMB_LOCAL_HOST + port + ConstantesUtil.PATH_USUARIO_V1 + "/roles");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);
		
		HttpEntity<Role> request = new HttpEntity<>(getRole(), headers);
		ResponseEntity<Role> result = this.restTemplate.exchange(uri, HttpMethod.POST, request,
				Role.class);
		assertEquals(result.getStatusCodeValue(), 200);
	}

	@Test
	public void criarGrupo_entao200() throws Exception {

		URI uri = new URI(ConstantesUtil.AMB_LOCAL_HOST + port + ConstantesUtil.PATH_USUARIO_V1 + "/grupos");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);
		
		HttpEntity<Grupo> request = new HttpEntity<>(getGrupo(), headers);
		ResponseEntity<Grupo> result = this.restTemplate.exchange(uri, HttpMethod.POST, request,
				Grupo.class);
		assertEquals(result.getStatusCodeValue(), 200);
	}
	
	@Test
	public void criarMenu_entao200() throws Exception {

		URI uri = new URI(ConstantesUtil.AMB_LOCAL_HOST + port + ConstantesUtil.PATH_USUARIO_V1 + "/menus");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);
		
		HttpEntity<MenuRequest> request = new HttpEntity<>(getMenu(), headers);
		ResponseEntity<Void> result = this.restTemplate.exchange(uri, HttpMethod.POST, request,
				Void.class);
		assertEquals(result.getStatusCodeValue(), 200);
	}
	
	private MenuRequest getMenu() {
		MenuRequest request = new MenuRequest();
		request.setRoleId(36L);
		request.setMenuIds(List.of(202));
		return request;
	}

	private Usuario getUsuario() {
		Usuario usuario = new Usuario();
		usuario.setPassword("123");
		usuario.setUsername(UUID.randomUUID() + "@dev.com.br");
		return usuario;
	}

	private UsuarioDados getUsuarioPut() {
		UsuarioDados usuarioDados = new UsuarioDados();
		usuarioDados.setId(1L);
		usuarioDados.setNome("Michael Sousa");
		usuarioDados.setEmail(UUID.randomUUID() + "@dev.com.br");
		usuarioDados.setCelular("11999880099");
		usuarioDados.setUsuarioId(1L);
		usuarioDados.setUnidadeId(1L);
		usuarioDados.setGrupoId(1L);
		return usuarioDados;
	}
	
	private Grupo getGrupo() {
		Grupo grupo = new Grupo();
		grupo.setDescricao("Advogado");
		grupo.setRoleId(13L);
		
		return grupo;
	}
	
	private Role getRole() {
		Role role = new Role();
		role.setDescricao("Administrador");		
		return role;
	}
}
