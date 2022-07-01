package br.com.avocat.web.response;

import java.time.LocalDate;

import br.com.avocat.persistence.model.processo.Andamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class AndamentoResponse {

	private Long id;
	private String ocorrencia;
	private boolean isVisivelCliente;
	private LocalDate dataAndamento;
	//TODO verificar como as informações dessas entidades podem
	//retornadas no response.
	//private TipoAndamento tipoAndamento;
	//private Processo processo;
	
	public AndamentoResponse(Andamento andamento) {
		this.id = andamento.getId();
		this.ocorrencia = andamento.getOcorrencia();
		this.isVisivelCliente = andamento.isVisivelCliente();
		this.dataAndamento = andamento.getDataAndamento();
		//this.tipoAndamento = andamento.getTipoAndamento();
		//this.processo = andamento.getProcesso();
	}
}
