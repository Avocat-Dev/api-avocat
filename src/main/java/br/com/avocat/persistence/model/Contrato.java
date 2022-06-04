package br.com.avocat.persistence.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.avocat.persistence.model.types.ContratoTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "contratos")
public class Contrato extends AbsctractAuditaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "escritorio", sequenceName = "escritorio", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "escritorio")
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
}
