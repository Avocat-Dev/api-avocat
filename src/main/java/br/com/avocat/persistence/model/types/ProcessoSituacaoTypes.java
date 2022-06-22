package br.com.avocat.persistence.model.types;

import lombok.Getter;

@Getter
public enum ProcessoSituacaoTypes {

	ANDAMENTO(1, "Andamento"),
	ENCERRADO(2, "Encerrado");
	
	private final int value;
	private final String descricao;
	
	private ProcessoSituacaoTypes(int value, String descricao) {
		this.value = value;
		this.descricao = descricao;
	}	
}
