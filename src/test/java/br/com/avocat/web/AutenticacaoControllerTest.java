package br.com.avocat.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.avocat.persistence.model.Usuario;
import br.com.avocat.persistence.repository.UsuarioRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AutenticacaoControllerTest {

	static final String CONTEXT_PATH = "/v1/auth";
	
	@MockBean
	UsuarioRepository usuarioRepository;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@BeforeEach
	public void setUp() {
		//@formatter:off
		given(this.usuarioRepository.findByUsername("dev@dev.com.br"))
        	.willReturn(Optional.of(
        			new Usuario(1L, "dev@dev.com.br", "$2a$10$ielFeDLFnuavoyASyyfA4.W6L8N2vMLFa5JMF5aPpMw5InBY1.fnK")
        			));
		//@formatter:on
	}
	
	@Test
	public void gerarTokenJwt_entao200() throws Exception {
		//@formatter:off
		this.mockMvc
			.perform(
					post(CONTEXT_PATH + "/token")
					.content(this.objectMapper.writeValueAsBytes(new Usuario(null, "dev@dev.com.br", "123")))
					.contentType(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.type").value("Bearer"))
					.andExpect(jsonPath("$.token").isNotEmpty());					
		//@formatter:on
	}
}
