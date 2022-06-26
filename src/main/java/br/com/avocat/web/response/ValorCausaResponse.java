package br.com.avocat.web.response;

import br.com.avocat.persistence.model.processo.ValorCausa;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ValorCausaResponse {
	private Long id;

	public ValorCausaResponse(ValorCausa valorCausa) {
		this.id = valorCausa.getId();
	}	
}
