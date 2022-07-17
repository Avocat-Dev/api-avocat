package br.com.avocat.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.avocat.spring.jwt.JwtTokenAuthenticationFilter;
import br.com.avocat.spring.jwt.JwtTokenProvider;

@Configuration
public class SecurityConfig {
	
	@Autowired
	CustomAuthenticationManager customAuthenticationManager;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http, JwtTokenProvider tokenProvider) throws Exception {
		//@formatter:off
		return http				
					.httpBasic(AbstractHttpConfigurer::disable)
	                .csrf(AbstractHttpConfigurer::disable)	                
	                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	                .authorizeRequests(c -> c
	                        .antMatchers(HttpMethod.POST, "/api/v1/usuarios/conta").permitAll()
	                        .antMatchers(HttpMethod.POST, "/api/v1/auth/token").permitAll()
	                        .anyRequest().authenticated()
	                )
	                .authenticationManager(customAuthenticationManager)	                
	                .addFilterBefore(new JwtTokenAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
	                .build();
		//@formatter:on		
	}
}
