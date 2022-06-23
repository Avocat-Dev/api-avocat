package br.com.avocat.persistence.model.types;

import lombok.Getter;

@Getter
public enum PessoaTypes {

	PESSOA_FISICA(0, "Pessoa F�sica", "PF"),
	PESSOA_JURICA(1, "Pessoa Jur�dica", "PJ");

	private final int value;
	private final String sigla;
	private final String descricao;

	private PessoaTypes(Integer value, String descricao, String sigla) {
		this.value = value;
		this.descricao = descricao;
		this.sigla = sigla;
	}
}
