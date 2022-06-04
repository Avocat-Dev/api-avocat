package br.com.avocat.persistence.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "unidades")
public class Unidade extends AbsctractAuditaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "unidade", sequenceName = "unidade", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unidade")
	private Long id;

	@Column(unique = true, length = 14)
	private String cnpj;

	@Column(name = "insc_estadual", length = 10)
	private String inscEstadual;

	@Column(length = 15)
	private String tel1;

	@Column(length = 15)
	private String tel2;

	@Column(unique = true, length = 100)
	private String email;

	private String web;
	private String razaoSocial;
	private String nomeUnidade;
	private String codigoUnidade;
	private String logoUnidade;
	
	@OneToMany
	@JoinColumn(name = "unidade_id")
	private List<Pessoa> pessoas = new ArrayList<>();
	
	@OneToMany
	@JoinColumn(name = "unidade_id")
	private List<Usuario> usuarios = new ArrayList<>();
}
