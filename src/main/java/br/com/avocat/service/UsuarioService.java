package br.com.avocat.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.avocat.persistence.model.Unidade;
import br.com.avocat.persistence.model.Usuario;
import br.com.avocat.persistence.model.UsuarioDados;
import br.com.avocat.persistence.repository.UnidadeRepository;
import br.com.avocat.persistence.repository.UsuarioDadosRepository;
import br.com.avocat.persistence.repository.UsuarioRepository;
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
	
	@Transactional
	public Optional<UsuarioResponse> novaConta(Usuario credencial) {

		try {
			credencial.setPassword(passwordEncoder.encode(credencial.getPassword()));
		
			var result = usuarioRepository.save(credencial);			
			
			if(result != null)
				return Optional.of(new UsuarioResponse(result));
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return Optional.empty();
	}
		
	@Transactional
	public Optional<UsuarioDadosResponse> update(UsuarioDados usuarioDados) {

		try {			
			var usuario = usuarioRepository.findById(usuarioDados.getUsuarioId());
			var unidade = unidadeRepository.findById(usuarioDados.getUnidadeId()).get();
			
			if(usuario.isPresent()) {

				usuarioDados.setUsuario(usuario.get());
				
				UsuarioDados result = new UsuarioDados();
				
				var update = usuarioDadosRepository.findByUsuarioId(usuarioDados.getUsuarioId());

				if(update.isEmpty()) {
					result = usuarioDadosRepository.save(usuarioDados);
					
				} else {
					
					update.get().setNome(usuarioDados.getNome());
					update.get().setEmail(usuarioDados.getEmail());
					update.get().setCelular(usuarioDados.getCelular());
					
					result = usuarioDadosRepository.save(update.get());
				}
								
				relacionarUsuarioComUnidade(unidade, result);
				
				return Optional.of(new UsuarioDadosResponse(result));
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return Optional.empty();
	}
	
	private void relacionarUsuarioComUnidade(Unidade unidade, UsuarioDados result) {
		unidade.getUsuariosDados().add(result);
		unidadeRepository.save(unidade);
	}	
}
