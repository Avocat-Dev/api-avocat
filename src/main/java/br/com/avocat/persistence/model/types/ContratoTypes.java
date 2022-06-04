package br.com.avocat.persistence.model.types;

import lombok.Getter;

@Getter
public enum ContratoTypes {

	HORAS(0, "Horas"),
    CUSTO_FIXO(1, "Custo Fixo"),
    ESCALONADO_HORA(2, "Escalonado por Hora"),
    ESCALONADO_PROCESSO(3, "Escalonado por Processo"),
    SEM_CUSTO(4, "Sem Custo"),
	ADMINISTRATIVO(3, "Administrativo");

    private final int value;
    private final String descricao;

    ContratoTypes(Integer value, String descricao) {
        this.value = value;
        this.descricao = descricao;
    }
}
