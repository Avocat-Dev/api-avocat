package br.com.avocat.persistence.model.types;

import lombok.Getter;

@Getter
public enum UsuarioStatusTypes {

	ATIVO(1, "Usuário Ativo"),
	INATIVO(2, "Usuário Inativo"),
	BLOQUEADO(3, "Usuário Bloqueado"),
	PENDENTE_LINK_CONFIRMACAO(4, "Pendente Primeiro Acesso");
	
	private final int value;
	private final String descricao;
	
	UsuarioStatusTypes(int value, String descricao) {
		this.value = value;
		this.descricao = descricao;
	}
}
