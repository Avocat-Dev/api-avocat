package br.com.avocat.spring.jwt;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import br.com.avocat.persistence.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	static final Logger LOGGER = LogManager.getLogger();

	@Value("${auth.jwt.secret}")
	String jwtSecret;

	@Value("${auth.jwt.expiration}")
	long jwtExpiration;

	SecretKey secretKey;

	@PostConstruct
	public void setUpSecretKey() {
		var secret = Base64.getEncoder().encodeToString(this.jwtSecret.getBytes());
		secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}

	public String gerarToken(Authentication auth) {

		Usuario usuario = (Usuario) auth.getPrincipal();
		Claims claims = Jwts.claims().setSubject(usuario.getUsername());

		var expira = Date.from(Instant.now().plus(Duration.ofSeconds(jwtExpiration)));

		//@formatter:off
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date())
				.setExpiration(expira)
				.signWith(this.secretKey, SignatureAlgorithm.HS256)
				.compact();
		//@formatter:on
	}

	public Authentication getAuthentication(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(this.secretKey).build().parseClaimsJws(token).getBody();
				
		User principal = new User(claims.getSubject(), "", Collections.emptyList());
		
		return new UsernamePasswordAuthenticationToken(principal, token, Collections.emptyList());
	}

	public boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(this.secretKey).build().parseClaimsJws(token);
			// parseClaimsJws will check expiration date. No need do here.
			LOGGER.info("expiration date: {}", claims.getBody().getExpiration());
			return true;
		} catch (RuntimeException ex) {
			LOGGER.error("Invalid JWT token: {}", ex.getMessage());
		}
		return false;
	}
}
