package br.com.avocat.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

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

import br.com.avocat.persistence.model.processo.Area;
import br.com.avocat.persistence.model.processo.Processo;
import br.com.avocat.util.PathUtil;
import br.com.avocat.web.request.LoginRequest;
import br.com.avocat.web.response.ProcessoResponse;
import br.com.avocat.web.response.TokenResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProcessoControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String token;

	@BeforeEach
	public void setUp() throws Exception {

		//@formatter:off
		URI uri = new URI(PathUtil.LOCAL_HOST + port + PathUtil.PATH_AUTH_TOKEN);
		
		HttpEntity<LoginRequest> request = new HttpEntity<>(new LoginRequest("dev@dev.com.br", "123"));
		ResponseEntity<TokenResponse> response = this.restTemplate.exchange(uri,HttpMethod.POST, request, TokenResponse.class);

		token = response.getBody().getToken();
		//@formatter:on
	}

	@Test
	public void cadastrarProcesso_entao200() throws Exception {

		URI uri = new URI(PathUtil.LOCAL_HOST + port + PathUtil.PATH_PROCESSO);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<Processo> request = new HttpEntity<>(getProcesso(), headers);
		ResponseEntity<ProcessoResponse> result = this.restTemplate.exchange(uri, HttpMethod.POST, request,
				ProcessoResponse.class);
		assertEquals(result.getStatusCodeValue(), 200);
	}

	private Processo getProcesso() {

		Processo processo = new Processo();

		processo.setAreaId(1L);
		processo.setContratoId(1L);
		processo.setUnidadeId(1L);

		processo.setNumeroProcesso("0012782-75.2016.5.15.0024");
		processo.setCodigoAuxiliar("");
		processo.setDataDistribuicao(null);
		processo.setDataEntrada(null);
		processo.setPartePrincipal("A. Raymond Brasil Ltda");
		processo.setParteContraria("Adao Thiago Royo");
		processo.setDetalheObjeto("Posto Avançado da Justiça do Trabalho de Jundiai em Vinhedo.");
		processo.setObservacaoInterna("");
		processo.setObservacaoCliente("");
		processo.setObservacaoEncerramento("");
		processo.setObservacaoFinanceiro("");

		return processo;
	}

	@Test
	public void criarNovaArea_entao200() throws Exception {

		URI uri = new URI(PathUtil.LOCAL_HOST + port + PathUtil.PATH_AREA);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<Area> request = new HttpEntity<>(getArea(), headers);
		ResponseEntity<Area> result = this.restTemplate.exchange(uri, HttpMethod.POST, request, Area.class);
		assertEquals(result.getStatusCodeValue(), 200);
	}

	private Area getArea() {
		Area area = new Area();
		area.setDescricao("Trabalhista");
		return area;
	}
}
