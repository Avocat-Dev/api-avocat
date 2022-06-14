package br.com.avocat.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.avocat.persistence.model.Usuario;
import br.com.avocat.persistence.model.UsuarioInfo;
import br.com.avocat.persistence.repository.UnidadeRepository;
import br.com.avocat.persistence.repository.UsuarioInfoRepository;
import br.com.avocat.persistence.repository.UsuarioRepository;
import br.com.avocat.web.response.UsuarioResponse;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository credencialRepository;
	
	@Autowired
	private UsuarioInfoRepository usuarioRepository;
	
	@Autowired
	private UnidadeRepository unidadeRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public Optional<UsuarioResponse> criarConta(Usuario credencial) {

		try {
			credencial.setPassword(passwordEncoder.encode(credencial.getPassword()));
		
			var result = credencialRepository.save(credencial);			
			
			if(result != null)
				return Optional.of(new UsuarioResponse(result));
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return Optional.empty();
	}
	
	@Transactional
	public Optional<UsuarioResponse> save(UsuarioInfo usuario) {

		try {
			
			var update = usuarioRepository.findById(usuario.getId());
			
			if(update.isPresent()) {
				
				update.get().setNome(usuario.getNome());
				update.get().setEmail(usuario.getEmail());
				update.get().setCelular(usuario.getCelular());

				var usuarioResult = usuarioRepository.save(update.get());			
				
				var unidade = unidadeRepository.findById(usuario.getUnidadeId()).get();				
				unidade.getUsuarios().add(update.get());
				
				return Optional.of(new UsuarioResponse(usuarioResult));
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return Optional.empty();
	}
}
