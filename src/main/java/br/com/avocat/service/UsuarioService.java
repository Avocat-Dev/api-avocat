package br.com.avocat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.avocat.persistence.model.Usuario;
import br.com.avocat.persistence.repository.UsuarioRepository;
import br.com.avocat.web.dto.LoginDto;
import br.com.avocat.web.dto.UsuarioDto;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired 
	ObjectMapper objectMapper;
	
	public UsuarioDto salvar(LoginDto data) {
		
		var usuario = objectMapper.convertValue(data, Usuario.class);
		
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			
		Usuario result = null;
		
		try {
			result = usuarioRepository.save(usuario);			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return objectMapper.convertValue(result, UsuarioDto.class);
	}
}
