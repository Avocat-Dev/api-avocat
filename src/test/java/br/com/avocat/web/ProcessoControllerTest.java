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
import br.com.avocat.persistence.model.processo.Comarca;
import br.com.avocat.persistence.model.processo.FaseProcessual;
import br.com.avocat.persistence.model.processo.Foro;
import br.com.avocat.persistence.model.processo.Papel;
import br.com.avocat.persistence.model.processo.Processo;
import br.com.avocat.persistence.model.processo.Rito;
import br.com.avocat.persistence.model.processo.TipoAcao;
import br.com.avocat.persistence.model.processo.Vara;
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

		processo.setContratoId(1L);
		processo.setUnidadeId(1L);

		processo.setAreaId(1L);
		processo.setTipoAcaoId(2L);
		processo.setFaseId(3L);
		processo.setRitoId(4L);
		processo.setComcarcaId(8L);
		processo.setForoId(10L);
		processo.setVaraId(9L);
		processo.setPartePrincipalId(11L);
		processo.setParteContrariaId(12L);

		processo.setNumeroProcesso("0012782-75.2016.5.15.0021");
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
	public void criarArea_entao200() throws Exception {

		URI uri = new URI(PathUtil.LOCAL_HOST + port + PathUtil.PATH_AREA);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<Area> request = new HttpEntity<>(getArea(), headers);
		ResponseEntity<Area> result = this.restTemplate.exchange(uri, HttpMethod.POST, request, Area.class);
		assertEquals(result.getStatusCodeValue(), 200);
	}
	
	@Test
	public void criarTipoAcao_entao200() throws Exception {

		URI uri = new URI(PathUtil.LOCAL_HOST + port + PathUtil.PATH_TIPO_ACAO);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<TipoAcao> request = new HttpEntity<>(getTipoAcao(), headers);
		ResponseEntity<TipoAcao> result = this.restTemplate.exchange(uri, HttpMethod.POST, request, TipoAcao.class);
		assertEquals(result.getStatusCodeValue(), 200);
	}

	@Test
	public void criarFaseProcessual_entao200() throws Exception {

		URI uri = new URI(PathUtil.LOCAL_HOST + port + PathUtil.PATH_FASE_PROCESSUAL);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<FaseProcessual> request = new HttpEntity<>(getFaseProcessual(), headers);
		ResponseEntity<FaseProcessual> result = this.restTemplate.exchange(uri, HttpMethod.POST, request, FaseProcessual.class);
		assertEquals(result.getStatusCodeValue(), 200);
	}
	
	@Test
	public void criarRito_entao200() throws Exception {

		URI uri = new URI(PathUtil.LOCAL_HOST + port + PathUtil.PATH_RITO);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<Rito> request = new HttpEntity<>(getRito(), headers);
		ResponseEntity<Rito> result = this.restTemplate.exchange(uri, HttpMethod.POST, request, Rito.class);
		assertEquals(result.getStatusCodeValue(), 200);
	}

	@Test
	public void criarComarca_entao200() throws Exception {

		URI uri = new URI(PathUtil.LOCAL_HOST + port + PathUtil.PATH_COMARCA);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<Comarca> request = new HttpEntity<>(getComarca(), headers);
		ResponseEntity<Comarca> result = this.restTemplate.exchange(uri, HttpMethod.POST, request, Comarca.class);
		assertEquals(result.getStatusCodeValue(), 200);
	}
	

	@Test
	public void criarForo_entao200() throws Exception {

		URI uri = new URI(PathUtil.LOCAL_HOST + port + PathUtil.PATH_FORO);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<Foro> request = new HttpEntity<>(getForo(), headers);
		ResponseEntity<Foro> result = this.restTemplate.exchange(uri, HttpMethod.POST, request, Foro.class);
		assertEquals(result.getStatusCodeValue(), 200);
	}

	@Test
	public void criarVara_entao200() throws Exception {

		URI uri = new URI(PathUtil.LOCAL_HOST + port + PathUtil.PATH_VARA);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<Vara> request = new HttpEntity<>(getVara(), headers);
		ResponseEntity<Vara> result = this.restTemplate.exchange(uri, HttpMethod.POST, request, Vara.class);
		assertEquals(result.getStatusCodeValue(), 200);
	}

	@Test
	public void criarPapel_entao200() throws Exception {

		URI uri = new URI(PathUtil.LOCAL_HOST + port + PathUtil.PATH_PAPEL);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<Papel> request = new HttpEntity<>(getPepel(), headers);
		ResponseEntity<Papel> result = this.restTemplate.exchange(uri, HttpMethod.POST, request, Papel.class);
		assertEquals(result.getStatusCodeValue(), 200);
	}
	
	private Papel getPepel() {
		Papel papel = new Papel();
		papel.setDescricao("Vítima");
		return papel;
	}

	private Foro getForo() {
		Foro foro = new Foro();
		foro.setDescricao("Foro Inicial");
		return foro;
	}
	
	private Vara getVara() {
		Vara vara = new Vara();
		vara.setDescricao("Vara Santo Amaro");
		return vara;
	}

	private Comarca getComarca() {
		Comarca comarca = new Comarca();
		comarca.setDescricao("Santo Amaro");
		return comarca;
	}
	
	private Rito getRito() {
		Rito rito = new Rito();
		rito.setDescricao("Rito Inicial");
		return rito;
	}

	private FaseProcessual getFaseProcessual() {
		FaseProcessual fase = new FaseProcessual();
		fase.setDescricao("Petição Inicial");
		return fase;
	}

	private Area getArea() {
		Area area = new Area();
		area.setDescricao("Trabalhista");
		return area;
	}
	
	private TipoAcao getTipoAcao() {
		TipoAcao tipo = new TipoAcao();
		tipo.setDescricao("Causa Ganha");
		return tipo;
	}
}
