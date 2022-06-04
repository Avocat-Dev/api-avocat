package br.com.avocat.persistence.model.types;

import lombok.Getter;

@Getter
public enum UsuarioTypes {

	DONO(1, "Dono"),
	CONVIDADO(2, "Convidado");
	
	private final int value;
	private final String descricao;
	
	UsuarioTypes(int value, String descricao) {
		this.value = value;
		this.descricao = descricao;
	}
}
