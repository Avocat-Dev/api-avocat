package br.com.avocat.web.response;

import br.com.avocat.persistence.model.Pessoa;
import br.com.avocat.persistence.model.types.PessoaTypes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PessoaResponse {

	private Long id;
	private String nome;
	private String cpfCnpj;
	private String emailCobranca;
	private String observacao;
	private PessoaTypes tipoPessoa;
	private String inscrEstadual;
	private Integer diaEmissao;
	private Integer diaVencimento;
	private Integer prazoVencimento;
	
	public PessoaResponse(Pessoa pessoa) {
		this.id = pessoa.getId();
		this.nome = pessoa.getNome();
		this.cpfCnpj = pessoa.getCpfCnpj();
		this.emailCobranca = pessoa.getEmailCobranca();
		this.observacao = pessoa.getObservacao();
		this.tipoPessoa = pessoa.getTipoPessoa();
		this.inscrEstadual = pessoa.getInscrEstadual();
		this.diaEmissao = pessoa.getDiaEmissao();
		this.diaVencimento = pessoa.getDiaVencimento();
		this.prazoVencimento = pessoa.getPrazoVencimento();
	}
}
