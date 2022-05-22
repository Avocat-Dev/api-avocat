package br.com.avocat.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	@Autowired
	CustomAuthenticationManager customAuthenticationManager;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//@formatter:off
		return http				
					.httpBasic(AbstractHttpConfigurer::disable)
	                .csrf(AbstractHttpConfigurer::disable)	                
	                .authorizeRequests(c -> c
	                        .antMatchers(HttpMethod.POST, "/v1/usuarios/nova-conta").permitAll()	                        
	                        .anyRequest().authenticated()
	                )
	                .authenticationManager(customAuthenticationManager)
	                .build();
		//@formatter:on		
	}
}
