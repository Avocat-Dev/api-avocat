package br.com.avocat.persistence.model.types;

import lombok.Getter;

@Getter
public enum ProbabilidadeTypes {
	
	SEM_PERSPECTIVA(1, "Sem perspectiva", 0),
	PROVAVEL(2, "Provável", 100),
	POSSIVEL(1, "Possível", 50),
	REMOTA(1, "Remota", 0);
	
	private final int value;
	private final String descricao;	
	private final int porcentagem;

	private ProbabilidadeTypes(int value, String descricao, int porcentagem) {
		this.value = value;
		this.descricao = descricao;
		this.porcentagem = porcentagem;
	}
}
