package br.com.avocat.util;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ControllerUtil {
	
	private ControllerUtil() {
	    throw new IllegalStateException("Utility class");
	  }
	
	public static <T> ResponseEntity<T> resolveNotFound() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();		
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

	public static ResponseEntity<Void> resolveVoid() {
		return ResponseEntity.ok().build();
	}

	public static ResponseEntity<String> resolveOk(String msg) {
		return ResponseEntity.ok().body(msg);
	}
}
