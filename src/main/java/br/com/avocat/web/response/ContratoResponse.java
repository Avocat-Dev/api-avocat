package br.com.avocat.web.response;

import java.time.LocalDate;

import br.com.avocat.persistence.model.Contrato;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ContratoResponse {
	
	private Long id;

	private String nomeContrato;
	private LocalDate dataEncerramento;
	private LocalDate dataReajuste;
	private String anotacaoNota;
	private String anotacaoGeral;
	private String anotacaoFaturamento;
	private int modalidadeId;

	public ContratoResponse(Contrato contrato) {
		
		this.id = contrato.getId();
		this.nomeContrato = contrato.getNomeContrato();
		this.dataEncerramento = contrato.getDataEncerramento();
		this.dataReajuste = contrato.getDataReajuste();
		this.anotacaoNota = contrato.getAnotacaoNota();
		this.anotacaoGeral = contrato.getAnotacaoGeral();
		this.anotacaoFaturamento = contrato.getAnotacaoFaturamento();
		this.modalidadeId = contrato.getModalidadeId().getValue();
	}
}
