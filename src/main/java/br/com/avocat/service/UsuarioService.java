package br.com.avocat.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.avocat.exception.AvocatException;
import br.com.avocat.persistence.model.Usuario;
import br.com.avocat.persistence.model.UsuarioDados;
import br.com.avocat.persistence.repository.GrupoRepository;
import br.com.avocat.persistence.repository.UnidadeRepository;
import br.com.avocat.persistence.repository.UsuarioDadosRepository;
import br.com.avocat.persistence.repository.UsuarioRepository;
import br.com.avocat.util.ObjetoUtil;
import br.com.avocat.web.response.UsuarioDadosResponse;
import br.com.avocat.web.response.UsuarioResponse;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioDadosRepository usuarioDadosRepository;

	@Autowired
	private UnidadeRepository unidadeRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private GrupoRepository grupoRepository;

	@Transactional
	public Optional<UsuarioResponse> novaConta(Usuario usuario) {
		validarUsuario(usuario);
		
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		var result = usuarioRepository.save(usuario);
		return Optional.of(new UsuarioResponse(result));
	}

	@Transactional
	public Optional<UsuarioDadosResponse> update(UsuarioDados usuarioDados) {
			validarUsuarioDados(usuarioDados);
		
			var resultUsuarioDados = usuarioDadosRepository.findById(usuarioDados.getId());
			var usuario = usuarioRepository.findById(usuarioDados.getUsuarioId());
			var unidade = unidadeRepository.findById(usuarioDados.getUnidadeId());
			var grupo = grupoRepository.findById(usuarioDados.getGrupoId());

			UsuarioDados result = null;

			if (resultUsuarioDados.isEmpty()) {
				if(unidade.isPresent() && grupo.isPresent() && usuario.isPresent()) {
					usuarioDados.setUnidade(unidade.get());
					usuarioDados.setUsuario(usuario.get());
					usuarioDados.setGrupo(grupo.get());
					
					result = usuarioDadosRepository.save(usuarioDados);
				} else {
					throw new AvocatException("Erro ao salvar UsuarioDados ID: " + usuarioDados.getUsuarioId());
				}
			} else {
				if(unidade.isPresent() && grupo.isPresent()) {

					resultUsuarioDados.get().setNome(usuarioDados.getNome());
					resultUsuarioDados.get().setEmail(usuarioDados.getEmail());
					resultUsuarioDados.get().setCelular(usuarioDados.getCelular());
					resultUsuarioDados.get().setUnidade(unidade.get());
					resultUsuarioDados.get().setGrupo(grupo.get());
	
					result = usuarioDadosRepository.save(resultUsuarioDados.get());
				} else {
					throw new AvocatException("Erro ao salvar UsuarioDados ID: " + usuarioDados.getUsuarioId());
				}
			}

			return Optional.of(new UsuarioDadosResponse(result));
	}
	
	private void validarUsuarioDados(UsuarioDados usuarioDados) {

		ObjetoUtil.verifica(usuarioDados.getUnidadeId()).orElseThrow(() ->
			new AvocatException("UnidadeId não pode ser nulo o vazio")
		);
		
		ObjetoUtil.verifica(usuarioDados.getGrupoId()).orElseThrow(() ->
			new AvocatException("GrupoId não pode ser nulo o vazio")
		);
		
		ObjetoUtil.verifica(usuarioDados.getUsuarioId()).orElseThrow(() ->
			new AvocatException("UsuarioID não pode ser nulo o vazio")
		);
		
		ObjetoUtil.verifica(usuarioDados.getEmail()).orElseThrow(() ->
			new AvocatException("Email não pode ser nulo o vazio")
		);
	}

	private void validarUsuario(Usuario usuario) {
		ObjetoUtil.verifica(usuario.getUsername()).orElseThrow(() ->
			new AvocatException("Username não pode ser nulo o vazio")
		);
		
		ObjetoUtil.verifica(usuario.getPassword()).orElseThrow(() ->
			new AvocatException("Password não pode ser nulo o vazio")
		);
	}
}
