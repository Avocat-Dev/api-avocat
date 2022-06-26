package br.com.avocat.persistence.model.types;

import lombok.Getter;

@Getter
public enum MoedaTypes {
	
	R$(1, "R$", "BRL", "Real Brasileiro"),
	$(2, "$", "USD", "Dólar (EUA)");
	
	private final int value;
	private final String moeda;
	private final String sigla;
	private final String descricao;

	private MoedaTypes(int value, String moeda, String sigla, String descricao) {
		this.value = value;
		this.moeda = moeda;
		this.sigla = sigla;
		this.descricao = descricao;
	}
}
