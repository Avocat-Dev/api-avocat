package br.com.avocat.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.avocat.exception.AvocatException;
import br.com.avocat.persistence.model.Grupo;
import br.com.avocat.persistence.repository.GrupoRepository;
import br.com.avocat.persistence.repository.RoleRepository;
import br.com.avocat.util.ObjetoUtil;

@Service
public class GrupoService {

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Transactional
	public Optional<Grupo> save(Grupo grupo) {

		validar(grupo);
		
		var role = roleRepository.findById(grupo.getRoleId());

		if(role.isPresent()) {
			
			grupo.setRole(role.get());
	
			var result = grupoRepository.save(grupo);
	
			return Optional.of(result);
			
		} else {
			throw new AvocatException("Role não encontrada ID: " + grupo.getRoleId());
		}
	}

	public Optional<Grupo> get(Long id) {
		
		var result = grupoRepository.findById(id);

		if (result.isPresent()) {
			return Optional.ofNullable(result.get());
			
		} else {
			return Optional.empty();
		}
	}
	
	private void validar(Grupo grupo) {
		
		ObjetoUtil.verifica(grupo.getDescricao()).orElseThrow(() ->
			new AvocatException("Username não pode ser nulo o vazio")
		);
		
		ObjetoUtil.verifica(grupo.getRoleId()).orElseThrow(() ->
			new AvocatException("RoleId não pode ser nulo o vazio")
		);
	}
}
