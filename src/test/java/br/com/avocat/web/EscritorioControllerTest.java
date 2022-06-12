package br.com.avocat.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.net.URI;
import java.util.Optional;

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
import org.springframework.http.ResponseEntity;

import br.com.avocat.persistence.model.Escritorio;
import br.com.avocat.persistence.model.Usuario;
import br.com.avocat.persistence.repository.UsuarioRepository;
import br.com.avocat.util.PathUtil;
import br.com.avocat.web.request.LoginRequest;
import br.com.avocat.web.response.EscritorioResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EscritorioControllerTest {

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
				.when().post(PathUtil.PATH_AUTH_TOKEN);
		
		JsonPath jsonPath = response.jsonPath();
		
		token = jsonPath.get("token");
		//@formatter:on
	}

	@Test
	public void cadastrarEscritorio_entao200() throws Exception {

		URI uri = new URI(PathUtil.LOCAL_HOST + port + PathUtil.PATH_ESCRITORIO);

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
	public void buscarEscritorioPorId_entao200() throws Exception {
		URI uri = new URI(PathUtil.LOCAL_HOST + port + PathUtil.PATH_ESCRITORIO + "/1");
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		ResponseEntity<EscritorioResponse> result = this.restTemplate.getForEntity(uri, EscritorioResponse.class);
		
		assertEquals(result.getStatusCodeValue(), 200);
		assertEquals(result.getBody().getId(), 1L);
	}
}
