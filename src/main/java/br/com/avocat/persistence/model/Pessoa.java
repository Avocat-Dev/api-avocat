package br.com.avocat.persistence.model;

import java.io.Serializable;

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

import br.com.avocat.persistence.model.types.PessoaTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "adm_pessoas")
public class Pessoa extends AbsctractAuditaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "pessoa", sequenceName = "pessoa", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa")
	private Long id;

	@Column(nullable = false, length = 100)
	private String nome;

	@Column(nullable = false, length = 20)
	private String cpfCnpj;

	@Column(length = 100)
	private String emailCobranca;

	@Column(length = 1000)
	private String observacao;

	@Enumerated(EnumType.ORDINAL)
	private PessoaTypes tipoPessoa;

	private String inscrEstadual;
	private Integer diaEmissao;
	private Integer diaVencimento;
	private Integer prazoVencimento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unidade_id", referencedColumnName = "id", nullable = false)
	private Unidade unidade;
	
	@Transient
	private Long unidadeId;
}
