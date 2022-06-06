package br.com.avocat.web.response;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.com.avocat.persistence.model.Contrato;
import br.com.avocat.persistence.model.types.ContratoTypes;
import lombok.Getter;
import lombok.Setter;

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

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "modalidade_id", nullable = false)
	private ContratoTypes modalidadeId;

	public ContratoResponse(Contrato contrato) {
		
		this.id = contrato.getId();
		this.nomeContrato = contrato.getNomeContrato();
		this.dataEncerramento = contrato.getDataEncerramento();
		this.dataReajuste = contrato.getDataReajuste();
		this.anotacaoNota = contrato.getAnotacaoNota();
		this.anotacaoGeral = contrato.getAnotacaoGeral();
		this.anotacaoFaturamento = contrato.getAnotacaoFaturamento();
		this.modalidadeId = contrato.getModalidadeId();
	}
}
