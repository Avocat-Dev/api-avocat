package br.com.avocat.web;

import br.com.avocat.persistence.model.Unidade;
import br.com.avocat.persistence.model.Usuario;
import br.com.avocat.persistence.repository.UsuarioRepository;
import br.com.avocat.util.ConstantesUtil;
import br.com.avocat.web.request.LoginRequest;
import br.com.avocat.web.response.EscritorioResponse;
import br.com.avocat.web.response.UnidadeResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UnidadeControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private UsuarioRepository usuarioRepository;

	@LocalServerPort
	private int port;

	private String token;

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
	void criarUnidade_entao200() throws Exception {
		//@formatter:off
		this.mockMvc
			.perform(
					post(ConstantesUtil.PATH_ADMINISTRATIVO_V1 + "/unidades")
					.header("Authorization", "Bearer " + token)
					.content(this.objectMapper.writeValueAsBytes(gerarUnidade()))
					.contentType(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isOk());
		//@formatter:on
	}

	@Test
	void buscarUnidadePorId_entao200() throws Exception {
		URI uri = new URI(ConstantesUtil.AMB_LOCAL_HOST + port + ConstantesUtil.PATH_ADMINISTRATIVO_V1 + "/unidades/1");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<Object> request = new HttpEntity<>(headers);

		ResponseEntity<EscritorioResponse> result = this.restTemplate
				.exchange(uri, HttpMethod.GET, request, EscritorioResponse.class);

		assertEquals(result.getStatusCodeValue(), 200);
		assertEquals(result.getBody().getId(), 1L);
	}

	@Test
	void buscarUnidadeTodos_entao200() throws Exception {
		URI uri = new URI(ConstantesUtil.AMB_LOCAL_HOST + port + ConstantesUtil.PATH_ADMINISTRATIVO_V1 + "/unidades/all");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<Object> request = new HttpEntity<>(headers);

		ResponseEntity<List<UnidadeResponse>> result = this.restTemplate
				.exchange(uri, HttpMethod.GET, request, new ParameterizedTypeReference<>() {
				});

		assertEquals(result.getStatusCodeValue(), 200);
	}
	
	private Unidade gerarUnidade() {

		Unidade unidade = new Unidade();
		
		unidade.setEscritorioId(1L);
		
		unidade.setCnpj("11998951000118");
		unidade.setCodigoUnidade("Teste Dev");
		unidade.setEmail(UUID.randomUUID() + "@teste.com.br");
		unidade.setInscEstadual("12345678");
		unidade.setLogoUnidade("url:logo");
		unidade.setNomeUnidade("Teste Dev");
		unidade.setRazaoSocial("Teste Dev");
		unidade.setTel1("11977860977");
		unidade.setTel2("11977860977");
		unidade.setWeb("www.web.com.br");
		
		return unidade;
	}
}
