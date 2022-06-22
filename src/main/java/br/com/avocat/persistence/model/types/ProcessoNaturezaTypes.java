package br.com.avocat.persistence.model.types;

import lombok.Getter;

@Getter
public enum ProcessoNaturezaTypes {

	JUDICIAL(1, "Judicial"),
	ADMINISTRATIVA(2, "Administrativa");
	
	private final int value;
	private final String descricao;
	
	private ProcessoNaturezaTypes(int value, String descricao) {
		this.value = value;
		this.descricao = descricao;
	}	
}
