package br.com.avocat.web.response;

import br.com.avocat.persistence.model.Escritorio;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EscritorioResponse {

	private Long id;
	private String nome;
	
	public EscritorioResponse(Escritorio escritorio) {		
		this.id = escritorio.getId();
		this.nome = escritorio.getNome();
	}
}
