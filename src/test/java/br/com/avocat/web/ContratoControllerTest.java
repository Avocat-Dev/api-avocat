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

import br.com.avocat.persistence.model.Contrato;
import br.com.avocat.persistence.model.Usuario;
import br.com.avocat.persistence.model.types.ContratoTypes;
import br.com.avocat.persistence.repository.UsuarioRepository;
import br.com.avocat.util.PathUtil;
import br.com.avocat.web.request.LoginRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ContratoControllerTest {

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
				.when().post(PathUtil.PATH_AUTH_TOKEN);
		
		JsonPath jsonPath = response.jsonPath();
		
		token = jsonPath.get("token");
		//@formatter:on
	}

	@Test
	public void criarContrato_entao200() throws Exception {
		//@formatter:off
		this.mockMvc
			.perform(
					post(PathUtil.PATH_CONTRATO)
					.header("Authorization", "Bearer " + token)
					.content(this.objectMapper.writeValueAsBytes(gerarContrato()))
					.contentType(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isOk());
		//@formatter:on
	}

	private Contrato gerarContrato() {

		Contrato contrato = new Contrato();

		contrato.setPessoaId(1L);
		contrato.setAnotacaoFaturamento("Anotação Faturamento");
		contrato.setAnotacaoGeral("Anotaçãoo Geral");
		contrato.setAnotacaoNota("Anotção impressa na nota");
		contrato.setNomeContrato("Contrato Teste Automatizado");
		contrato.setDataEncerramento(null);
		contrato.setDataReajuste(null);
		contrato.setModalidadeId(ContratoTypes.CUSTO_FIXO);

		return contrato;
	}
}
