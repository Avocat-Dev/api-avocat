package br.com.avocat.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.avocat.exception.AvocatException;
import br.com.avocat.persistence.model.Pessoa;
import br.com.avocat.persistence.repository.PessoaRepository;
import br.com.avocat.persistence.repository.UnidadeRepository;
import br.com.avocat.util.ObjetoUtil;
import br.com.avocat.web.response.PessoaResponse;

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

		if (result.isPresent()) {
			return Optional.ofNullable(new PessoaResponse(result.get()));
		} else {
			return Optional.empty();
		}
	}

	public List<PessoaResponse> all() {
		var result = pessoaRepository.findAll();
		return result.stream().map(PessoaResponse::new).collect(Collectors.toList());
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
		
		ObjetoUtil.verifica(pessoa.getUnidade()).orElseThrow(() -> 
			new AvocatException("A unidade da pessoa não pode ser nulo o vazio.")
		);
	}
}
