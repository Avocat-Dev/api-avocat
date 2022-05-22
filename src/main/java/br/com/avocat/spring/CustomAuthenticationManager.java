package br.com.avocat.spring;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.avocat.persistence.model.Usuario;
import br.com.avocat.persistence.repository.UsuarioRepository;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		var username = authentication.getPrincipal() + "";
        var password = authentication.getCredentials() + "";
               
        UserDetails user = loadUserByUsername(username);
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }
        
        if (!user.isEnabled()) {
            throw new DisabledException("User account is not active");
        }
        
        return new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
	}

	public UserDetails loadUserByUsername(String username) {
		//@formatter:off
		Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
		
		if (usuario.isEmpty())
			throw new UsernameNotFoundException(username);        
		
		return new Usuario(
				usuario.get().getId(), 
				usuario.get().getUsername(),				
				usuario.get().getPassword()
				);
		//@formatter:on
	}	
}
