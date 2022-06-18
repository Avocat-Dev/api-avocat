package br.com.avocat.web.controller.generic;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.avocat.persistence.model.generic.GenericEntity;

public interface ControllerGeneric<T extends GenericEntity> {
	
	ResponseEntity<T> save(@RequestBody T entity);

	ResponseEntity<T> get(Long id);
	
	ResponseEntity<List<T>> all();

	ResponseEntity<Void> del(@PathVariable Long id);
}
