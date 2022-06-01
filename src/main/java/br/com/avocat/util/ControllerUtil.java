package br.com.avocat.util;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ControllerUtil {

	public static <T> ResponseEntity<T> resolve(Object result, Class<T> clazz) {
		
		if(result != null) {
			var dto = new ObjectMapper().convertValue(result, clazz);		
			return ResponseEntity.ok(dto);		
		} else {
			return ResponseEntity.internalServerError().body(null);
		}
	}
}
