package br.com.avocat.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.net.URI;
import java.util.Optional;

import br.com.avocat.persistence.model.UsuarioDados;
import br.com.avocat.web.response.UsuarioDadosResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import br.com.avocat.persistence.model.Escritorio;
import br.com.avocat.persistence.model.Usuario;
import br.com.avocat.persistence.repository.UsuarioRepository;
import br.com.avocat.util.ConstantesUtil;
import br.com.avocat.web.request.LoginRequest;
import br.com.avocat.web.response.EscritorioResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class EscritorioControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private UsuarioRepository usuarioRepository;

	@LocalServerPort
	private int port;

	String token;

	@BeforeEach
	public void setUp() {

		//@formatter:off
		given(this.usuarioRepository.findByUsername("dev@dev.com.br"))
        	.willReturn(Optional.of(
        			new Usuario(1L, "dev@dev.com.br", "$2a$10$ielFeDLFnuavoyASyyfA4.W6L8N2vMLFa5JMF5aPpMw5InBY1.fnK")
        			));
		//@formatter:on

		//@formatter:off
		RestAssured.port = this.port;
		
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(new LoginRequest("dev@dev.com.br", "123"))
				.when().post(ConstantesUtil.PATH_AUTH_V1 + "/token");
		
		JsonPath jsonPath = response.jsonPath();
		
		token = jsonPath.get("token");
		//@formatter:on
	}

	@Test
	void cadastrarEscritorio_entao200() throws Exception {

		URI uri = new URI(ConstantesUtil.AMB_LOCAL_HOST + port + ConstantesUtil.PATH_ADMINISTRATIVO_V1 + "/escritorios");

		var contrato = new Escritorio();
		contrato.setNome("Escritório Teste");
		
		var escritorioDto = new EscritorioResponse(contrato);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<EscritorioResponse> request = new HttpEntity<>(escritorioDto, headers);

		ResponseEntity<EscritorioResponse> result = this.restTemplate.postForEntity(uri, request, EscritorioResponse.class);
		
		assertEquals(result.getStatusCodeValue(), 200);		
	}
	
	@Test
	void buscarEscritorioPorId_entao200() throws Exception {
		URI uri = new URI(ConstantesUtil.AMB_LOCAL_HOST + port + ConstantesUtil.PATH_ADMINISTRATIVO_V1 + "/escritorios/1");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<Object> request = new HttpEntity<>(headers);

		ResponseEntity<EscritorioResponse> result = this.restTemplate
				.exchange(uri, HttpMethod.GET, request, EscritorioResponse.class);

		assertEquals(result.getStatusCodeValue(), 200);
		assertEquals(result.getBody().getId(), 1L);
	}
}
