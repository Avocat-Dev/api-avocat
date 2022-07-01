package br.com.avocat.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import br.com.avocat.persistence.model.processo.Andamento;
import br.com.avocat.persistence.model.processo.TipoAndamento;
import br.com.avocat.util.ConstantesUtil;
import br.com.avocat.web.request.LoginRequest;
import br.com.avocat.web.response.AndamentoResponse;
import br.com.avocat.web.response.TokenResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AndamentoControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String token;

	@BeforeEach
	public void setUp() throws Exception {

		//@formatter:off
		URI uri = new URI(ConstantesUtil.AMB_LOCAL_HOST + port + ConstantesUtil.PATH_AUTH_V1 + "/token");
		
		HttpEntity<LoginRequest> request = new HttpEntity<>(new LoginRequest("dev@dev.com.br", "123"));
		ResponseEntity<TokenResponse> response = this.restTemplate.exchange(uri,HttpMethod.POST, request, TokenResponse.class);

		token = response.getBody().getToken();
		//@formatter:on
	}

	@Test
	public void cadastrarAndamento_entao200() throws Exception {

		URI uri = new URI(ConstantesUtil.AMB_LOCAL_HOST + port + ConstantesUtil.PATH_PROCESSO_V1 + "/andamentos");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		var andamentos = getAndamentos();
		
		for(Andamento andamento : andamentos) {
			
			HttpEntity<Andamento> request = new HttpEntity<>(andamento, headers);
			
			ResponseEntity<AndamentoResponse> result = this.restTemplate.exchange(uri, HttpMethod.POST, request,
					AndamentoResponse.class);		
			assertEquals(result.getStatusCodeValue(), 200);
		}
	}

	@Test
	public void cadastrarTipoAndamento_entao200() throws Exception {

		URI uri = new URI(ConstantesUtil.AMB_LOCAL_HOST + port + ConstantesUtil.PATH_PROCESSO_V1 + "/tipos-andamentos");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<TipoAndamento> request = new HttpEntity<>(getTipoAndamento(), headers);
		ResponseEntity<TipoAndamento> result = this.restTemplate.exchange(uri, HttpMethod.POST, request,
				TipoAndamento.class);
		assertEquals(result.getStatusCodeValue(), 200);
	}
	
	private TipoAndamento getTipoAndamento() {
		TipoAndamento tpAndamento = new TipoAndamento();
		tpAndamento.setDescricao("Intimação Judicial");
		return tpAndamento;
	}
	
	private List<Andamento> getAndamentos() {
		
		List<Andamento> andamentos = new ArrayList<>();
		
		for(int c = 0; c < 3; c++)			
			andamentos.add(buildAndamento());
			
		return andamentos;
	}
	
	private Andamento buildAndamento() {
		Andamento andamento = new Andamento();
		andamento.setOcorrencia("Simples ocorrência...Teste");
		andamento.setDataAndamento(LocalDate.now());
		andamento.setProcessoId(1L);
		andamento.setTipoAndamentoId(12L);
		
		return andamento;
	}
}
