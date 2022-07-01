package br.com.avocat.persistence.model.processo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.avocat.persistence.model.AbsctractAuditaEntity;
import br.com.avocat.persistence.model.types.MoedaTypes;
import br.com.avocat.persistence.model.types.ProbabilidadeTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pro_valores_causas")
public class ValorCausa extends AbsctractAuditaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "pro_valor_causa", sequenceName = "pro_valor_causa", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pro_valor_causa")
	private Long id;
	
	@Enumerated(EnumType.STRING)	
	private MoedaTypes moeda;
	
	@Enumerated(EnumType.STRING)	
	private ProbabilidadeTypes probabilidade;
	
	//Valores iniciais
	private LocalDate dataReferencia;
	private LocalDate dataReferenciaCalculoJuros;	
	private BigDecimal valorOriginal;
	private BigDecimal multaOriginal;	
	private BigDecimal jurosAuto;
	private BigDecimal totalAuto;
	
	//Atualizacao dos valores
	private LocalDate dataReferenciaUltimaAtualizacao;
	private BigDecimal correcaoMonetaria;
	private BigDecimal juros;	
	private BigDecimal multa;
	
	private String observavao;
	
	@OneToOne
	@JoinColumn(name = "tipo_valor_id", referencedColumnName = "id", nullable = false)
	private TipoValor tipoValor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "processo_id", referencedColumnName = "id", nullable = false)
	private Processo processo;
	
	@Transient
	private Long processoId;
	
	@Transient
	private Long tipoValorId;
}
