package br.com.avocat.util;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

public class ControllerUtil {
	
	public static <T> ResponseEntity<T> resolveNotFound() {		
		return ResponseEntity.notFound().build();		
	}

	public static <T> ResponseEntity<T> resolve(Optional<T> result) {
		
		if(result.isPresent()) {				
			return ResponseEntity.ok().body(result.get());		
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	public static <T> ResponseEntity<List<T>> resolveAll(List<T> result) {
		return ResponseEntity.ok().body(result);		
	}
}
