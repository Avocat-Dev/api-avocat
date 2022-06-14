package br.com.avocat.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.avocat.persistence.model.Unidade;
import br.com.avocat.persistence.model.Usuario;
import br.com.avocat.persistence.repository.UsuarioRepository;
import br.com.avocat.util.PathUtil;
import br.com.avocat.web.request.LoginRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UnidadeControllerTest {
	
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
				.when().post(PathUtil.PATH_AUTH_TOKEN);
		
		JsonPath jsonPath = response.jsonPath();
		
		token = jsonPath.get("token");
		//@formatter:on
	}

	@Test
	public void criarUnidade_entao200() throws Exception {
		//@formatter:off
		this.mockMvc
			.perform(
					post(PathUtil.PATH_UNIDADE)
					.header("Authorization", "Bearer " + token)
					.content(this.objectMapper.writeValueAsBytes(gerarUnidade()))
					.contentType(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isOk());
		//@formatter:on
	}
	
	private Unidade gerarUnidade() {

		Unidade unidade = new Unidade();
		
		unidade.setEscritorioId(2L);
		
		unidade.setCnpj("00000000000001");
		unidade.setCodigoUnidade("Teste Dev");
		unidade.setEmail("unidade1@teste.com.br");
		unidade.setInscEstadual("12345678");
		unidade.setLogoUnidade("url:logo");
		unidade.setNomeUnidade("Teste Dev");
		unidade.setTel1("11977860977");
		unidade.setTel2("11977860977");
		unidade.setWeb("www.web.com.br");
		
		return unidade;
	}
}
