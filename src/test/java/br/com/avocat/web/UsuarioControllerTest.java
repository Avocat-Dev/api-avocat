package br.com.avocat.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.avocat.persistence.model.Usuario;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UsuarioControllerTest {

	static final String CONTEXT_PATH = "/v1/usuarios";
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	public void criarNovaConta_entao200() throws Exception {
		//@formatter:off
		this.mockMvc
			.perform(
					post(CONTEXT_PATH + "/nova-conta")
					.content(this.objectMapper.writeValueAsBytes(new Usuario(null, "dev@dev.com.br", "123")))
					.contentType(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.id").isNotEmpty())
					.andExpect(jsonPath("$.username").value("dev@dev.com.br"));
		//@formatter:on
	}
}
