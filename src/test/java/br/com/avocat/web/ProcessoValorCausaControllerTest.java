package br.com.avocat.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;

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

import br.com.avocat.persistence.model.processo.TipoValor;
import br.com.avocat.persistence.model.processo.ValorCausa;
import br.com.avocat.persistence.model.types.MoedaTypes;
import br.com.avocat.persistence.model.types.ProbabilidadeTypes;
import br.com.avocat.util.PathUtil;
import br.com.avocat.web.request.LoginRequest;
import br.com.avocat.web.response.TokenResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProcessoValorCausaControllerTest {

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
	public void cadastrarValorCausa_entao200() throws Exception {

		URI uri = new URI(PathUtil.LOCAL_HOST + port + PathUtil.PATH_PROCESSO + "/valor-causa");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<ValorCausa> request = new HttpEntity<>(getValorCausa(), headers);
		ResponseEntity<ValorCausa> result = this.restTemplate.exchange(uri, HttpMethod.POST, request,
				ValorCausa.class);
		assertEquals(result.getStatusCodeValue(), 200);
	}

	@Test
	public void criarTipoValor_entao200() throws Exception {

		URI uri = new URI(PathUtil.LOCAL_HOST + port + PathUtil.PATH_PROCESSO + "/tipos-valores");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<TipoValor> request = new HttpEntity<>(getTipoValor(), headers);
		ResponseEntity<TipoValor> result = this.restTemplate.exchange(uri, HttpMethod.POST, request,
				TipoValor.class);
		assertEquals(result.getStatusCodeValue(), 200);
	}
	
	private TipoValor getTipoValor() {
		TipoValor tipo = new TipoValor();
		tipo.setDescricao("Pensão Alimentícia");
		return tipo;
	}

	private ValorCausa getValorCausa() {

		ValorCausa valor = new ValorCausa();
		
		valor.setProcessoId(5L);
		valor.setTipoValorId(14L);
		
		valor.setMoeda(MoedaTypes.R$);
		valor.setProbabilidade(ProbabilidadeTypes.PROVAVEL);
		
		valor.setDataReferencia(LocalDate.now());
		valor.setDataReferenciaCalculoJuros(LocalDate.now());
		valor.setValorOriginal(new BigDecimal("1000.00"));
		valor.setMultaOriginal(new BigDecimal("100.00"));
		valor.setJuros(new BigDecimal("100.00"));
		
		valor.setDataReferenciaUltimaAtualizacao(LocalDate.now());
		valor.setCorrecaoMonetaria(new BigDecimal("1000.00"));
		valor.setJuros(new BigDecimal("100.00"));
		valor.setMulta(new BigDecimal("100.00"));
		
		valor.setObservavao("Inclusão do valor inicial do processo.");
		
		return valor;
	}
}
