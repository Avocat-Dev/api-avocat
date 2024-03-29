package br.com.avocat.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import br.com.avocat.web.response.PessoaResponse;
import br.com.avocat.web.response.PessoaResponse;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.avocat.persistence.model.Pessoa;
import br.com.avocat.persistence.model.Usuario;
import br.com.avocat.persistence.model.types.PessoaTypes;
import br.com.avocat.persistence.repository.UsuarioRepository;
import br.com.avocat.util.ConstantesUtil;
import br.com.avocat.web.request.LoginRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PessoaControllerTest {

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
	void criarPessoa_entao200() throws Exception {
		//@formatter:off
		this.mockMvc
			.perform(
					post(ConstantesUtil.PATH_ADMINISTRATIVO_V1 + "/pessoas")
					.header("Authorization", "Bearer " + token)
					.content(this.objectMapper.writeValueAsBytes(gerarPessoa()))
					.contentType(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isOk());
		//@formatter:on
	}

	@Test
	void buscarPessoaPorId_entao200() throws Exception {
		URI uri = new URI(ConstantesUtil.AMB_LOCAL_HOST + port + ConstantesUtil.PATH_ADMINISTRATIVO_V1 + "/pessoas/1");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<Object> request = new HttpEntity<>(headers);

		ResponseEntity<PessoaResponse> result = this.restTemplate
				.exchange(uri, HttpMethod.GET, request, PessoaResponse.class);

		assertEquals(result.getStatusCodeValue(), 200);
		assertEquals(result.getBody().getId(), 1L);
	}

	@Test
	void buscarPessoaTodos_entao200() throws Exception {
		URI uri = new URI(ConstantesUtil.AMB_LOCAL_HOST + port + ConstantesUtil.PATH_ADMINISTRATIVO_V1 + "/pessoas/all");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<Object> request = new HttpEntity<>(headers);

		ResponseEntity<List<PessoaResponse>> result = this.restTemplate
				.exchange(uri, HttpMethod.GET, request, new ParameterizedTypeReference<>() {
				});

		assertEquals(result.getStatusCodeValue(), 200);
	}

	private Pessoa gerarPessoa() {

		Pessoa pessoa = new Pessoa();

		pessoa.setUnidadeId(1L);

		pessoa.setNome("Empreasa Teste S.A");
		pessoa.setCpfCnpj("00000000000000");
		pessoa.setDiaEmissao(5);
		pessoa.setDiaVencimento(10);
		pessoa.setEmailCobranca("cobranca@gmail.com");
		pessoa.setInscrEstadual("9999");
		pessoa.setTipoPessoa(PessoaTypes.PESSOA_JURICA);
		pessoa.setObservacao("Cadastro do teste automatizado");
		pessoa.setPrazoVencimento(15);

		return pessoa;
	}
}
