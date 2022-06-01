package br.com.avocat.web;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.avocat.persistence.model.Usuario;
import br.com.avocat.persistence.repository.UsuarioRepository;
import br.com.avocat.web.dto.LoginDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AutenticacaoTokenControllerTest {

	final static Logger LOGGER = LogManager.getLogger();
	
	static final String CONTEXT_PATH = "/v1/auth";

	@MockBean
	UsuarioRepository usuarioRepository;
	
	@Autowired
	ObjectMapper objectMapper;
	
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
				.body(new LoginDto("dev@dev.com.br", "123"))
				.when().post(CONTEXT_PATH + "/token");
		
		JsonPath jsonPath = response.jsonPath();
		
		token = jsonPath.get("token");
		//@formatter:on
	}

	@Test
	public void testarEcoHelloComToken_entao200() throws Exception {
		//@formatter:off
		LOGGER.info("Token gerado: {}", token);
		
		RestAssured.given().log().all()
			.headers("Authorization", "Bearer " + token)
			.contentType(ContentType.JSON)
			.when()
				.get(CONTEXT_PATH + "/eco")
			.then().statusCode(200);			
		//@formatter:on
	}
}
