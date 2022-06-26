package br.com.avocat.persistence.model.processo;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import br.com.avocat.persistence.model.Contrato;
import br.com.avocat.persistence.model.Unidade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "processos")
public class Processo extends AbsctractAuditaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "processo", sequenceName = "processo", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "processo")
	private Long id;

	@Column(name = "nr_processo", nullable = false)
	private String numeroProcesso;
	private String codigoAuxiliar;

	@Column(name = "parte_principal", nullable = false)
	private String partePrincipal;

	@Column(name = "parte_contraria", nullable = false)
	private String parteContraria;

	@Column(name = "obs_interna")
	private String observacaoInterna;

	@Column(name = "obs_encerramento")
	private String observacaoEncerramento;

	@Column(name = "obs_cliente")
	private String observacaoCliente;

	@Column(name = "obs_financeiro")
	private String observacaoFinanceiro;

	private String detalheObjeto;

	private LocalDate dataEntrada;
	private LocalDate dataDistribuicao;

	@OneToOne
	@JoinColumn(name = "papel_pt_principal_id")
	private Papel papelPartePrincipal;
	
	@OneToOne
	@JoinColumn(name = "papel_pt_contraria_id")
	private Papel papelParteContraria;
	
	@OneToOne
	@JoinColumn(name = "area_id")
	private Area area;
	
	@OneToOne
	@JoinColumn(name = "acao_id")
	private TipoAcao tipoAcao;
	
	@OneToOne
	@JoinColumn(name = "fase_id")
	private FaseProcessual faseProcessual;
	
	@OneToOne
	@JoinColumn(name = "rito_id")
	private Rito rito;
	
	@OneToOne
	@JoinColumn(name = "comarca_id")
	private Comarca comarca;
	
	@OneToOne
	@JoinColumn(name = "foro_id")
	private Foro foro;
	
	@OneToOne
	@JoinColumn(name = "vara_id")
	private Vara vara;

	@OneToOne
	@JoinColumn(name = "unidade_id", nullable = false)
	private Unidade unidade;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contrato_id", referencedColumnName = "id", nullable = false)
	private Contrato contrato;


	@Transient
	private Long unidadeId;

	@Transient
	private Long contratoId;
	
	@Transient
	private Long areaId;	

	@Transient
	private Long tipoAcaoId;
	
	@Transient
	private Long faseId;
	
	@Transient
	private Long ritoId;
	
	@Transient
	private Long comcarcaId;
	
	@Transient
	private Long foroId;
	
	@Transient
	private Long varaId;
	
	@Transient
	private Long partePrincipalId;
	
	@Transient
	private Long parteContrariaId;
}
