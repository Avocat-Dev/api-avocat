package br.com.avocat.service;

import br.com.avocat.exception.AvocatException;
import br.com.avocat.persistence.model.Pessoa;
import br.com.avocat.persistence.repository.PessoaRepository;
import br.com.avocat.persistence.repository.UnidadeRepository;
import br.com.avocat.util.ObjetoUtil;
import br.com.avocat.web.response.PessoaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private UnidadeRepository unidadeRepository;

	@Transactional
	public Optional<PessoaResponse> save(Pessoa pessoa) {
		validar(pessoa);
		
		var unidade = unidadeRepository.findById(pessoa.getUnidadeId());

		if(unidade.isPresent()) {
			pessoa.setUnidade(unidade.get());
			var result = pessoaRepository.save(pessoa);
			return Optional.of(new PessoaResponse(result));
		} else {
			return Optional.empty();
		}
	}

	public Optional<PessoaResponse> get(Long id) {
		var result = pessoaRepository.findById(id);
		return result.map(PessoaResponse::new);
	}

	public List<PessoaResponse> all() {
		var result = pessoaRepository.findAll();
		return result.stream().map(PessoaResponse::new).toList();
	}

	@Transactional
	public void delete(Long id) {
		pessoaRepository.deleteById(id);
	}
	
	private void validar(Pessoa pessoa) {
		
		ObjetoUtil.verifica(pessoa.getEmailCobranca()).orElseThrow(() -> 
			new AvocatException("E-mail de cobrança da pessoa não pode ser nulo o vazio.")
		);
		
		ObjetoUtil.verifica(pessoa.getNome()).orElseThrow(() -> 
			new AvocatException("Nome da pessoa não pode ser nulo o vazio.")
		);
		
		ObjetoUtil.verifica(pessoa.getCpfCnpj()).orElseThrow(() -> 
			new AvocatException("CPF/CNPJ da pessoa não pode ser nulo o vazio.")
		);
		
		ObjetoUtil.verifica(pessoa.getTipoPessoa()).orElseThrow(() -> 
			new AvocatException("Tipo da pessoa não pode ser nulo o vazio.")
		);
		
		ObjetoUtil.verifica(pessoa.getUnidadeId()).orElseThrow(() ->
			new AvocatException("A unidade da pessoa não pode ser nulo o vazio.")
		);
	}
}
