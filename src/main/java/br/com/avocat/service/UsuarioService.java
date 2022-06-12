package br.com.avocat.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.avocat.persistence.model.Usuario;
import br.com.avocat.persistence.repository.UsuarioDadosRepository;
import br.com.avocat.persistence.repository.UsuarioRepository;
import br.com.avocat.web.response.UsuarioResponse;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioDadosRepository usuarioDadosRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired 
	ObjectMapper objectMapper;
	
	@SuppressWarnings("unused")
	@Transactional
	public Optional<UsuarioResponse> save(Usuario usuario) {

		try {
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		
			var result = usuarioRepository.save(usuario);			
			usuarioDadosRepository.save(result.getUsuarioDados());
			
			if(result != null) 
				return Optional.of(new UsuarioResponse(result));
			else  
				return Optional.empty();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
	}
}
