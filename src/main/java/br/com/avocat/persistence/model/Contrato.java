package br.com.avocat.persistence.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name = "adm_contratos")
public class Contrato extends AbsctractAuditaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "contrato", sequenceName = "contrato", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contrato")
	private Long id;

	@Column(nullable = false)
	private String nomeContrato;
	
	private LocalDate dataEncerramento;
	
	private LocalDate dataReajuste;
	
	@Column(columnDefinition = "text")
	private String anotacaoNota;
	
	@Column(columnDefinition = "text")
	private String anotacaoGeral;
	
	@Column(columnDefinition = "text")
	private String anotacaoFaturamento;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "modalidade_id", nullable = false)
	private ContratoTypes modalidadeId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pessoa_id", referencedColumnName = "id", nullable = false)
	private Pessoa pessoa;
	
	@Transient
	private Long pessoaId;
}
