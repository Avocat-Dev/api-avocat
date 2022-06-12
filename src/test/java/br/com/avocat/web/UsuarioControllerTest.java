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
import br.com.avocat.persistence.model.UsuarioDados;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UsuarioControllerTest {

	static final String CONTEXT_PATH = "/v1/usuarios";
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	public void criarNovoUsuario_entao200() throws Exception {
		//@formatter:off
		this.mockMvc
			.perform(
					post(CONTEXT_PATH + "/conta")
					.content(this.objectMapper.writeValueAsBytes(gerarUsuario()))
					.contentType(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.id").isNotEmpty());					
		//@formatter:on
	}
	
	private Usuario gerarUsuario() {
		Usuario usuario = new Usuario();		
		usuario.setPassword("123");
		usuario.setUsername("dev@dev.com.br");
		
		UsuarioDados usuarioDados = new UsuarioDados();		
		usuarioDados.setCelular("");
		usuarioDados.setNome("Teste Automatizado");
		usuarioDados.setEmail("teste@teste.com.br");
		
		usuario.setUsuarioDados(usuarioDados);
		
		return usuario;
	}
}
