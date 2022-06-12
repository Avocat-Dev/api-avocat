package br.com.avocat.web.response;

import br.com.avocat.persistence.model.Unidade;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UnidadeResponse {
	private Long id;
	private String cnpj;
	private String inscEstadual;
	private String tel1;
	private String tel2;
	private String email;
	private String web;
	private String razaoSocial;
	private String nomeUnidade;
	private String codigoUnidade;
	private String logoUnidade;
	
	public UnidadeResponse(Unidade unidade) {		
		this.id = unidade.getId();
		this.cnpj = unidade.getCnpj();
		this.inscEstadual = unidade.getInscEstadual();
		this.tel1 = unidade.getTel1();
		this.tel2 = unidade.getTel2();
		this.email = unidade.getEmail();
		this.web = unidade.getWeb();
		this.razaoSocial = unidade.getRazaoSocial();
		this.nomeUnidade = unidade.getNomeUnidade();
		this.codigoUnidade = unidade.getCodigoUnidade();
		this.logoUnidade = unidade.getLogoUnidade();
	}	
}
