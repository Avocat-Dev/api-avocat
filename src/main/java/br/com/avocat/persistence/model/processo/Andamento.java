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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pro_andamentos")
public class Andamento extends AbsctractAuditaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "andamento", sequenceName = "andamento", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "andamento")
	private Long id;

	private String ocorrencia;
	
	@Column(name = "visivel_cliente")
	private boolean isVisivelCliente = false;
	
	@Column(name = "dt_andamento")
	private LocalDate dataAndamento;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tp_andamento_id", nullable = false)
	private TipoAndamento tipoAndamento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "processo_id", nullable = false)
	private Processo processo;
	
	@Transient
	private long processoId;
	
	@Transient
	private long tipoAndamentoId;
}
