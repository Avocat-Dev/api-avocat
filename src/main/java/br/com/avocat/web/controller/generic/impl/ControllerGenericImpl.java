package br.com.avocat.web.controller.generic.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.avocat.persistence.model.generic.GenericEntity;
import br.com.avocat.service.generic.ServiceGeneric;
import br.com.avocat.util.ControllerUtil;
import br.com.avocat.web.controller.generic.ControllerGeneric;

public class ControllerGenericImpl<T extends GenericEntity> implements ControllerGeneric<T> {

	@Autowired
	private ServiceGeneric<T> genericService;
	
	@Override
	@PostMapping
	public ResponseEntity<T> save(T entity) {
		var result = genericService.save(entity);					
		return ControllerUtil.resolve(result);
	}

	@Override
	@GetMapping
	public ResponseEntity<List<T>> all() {
		var all = genericService.all();
		return ControllerUtil.resolveAll(all);
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<T> get(@PathVariable("id") Long id) {
		var result = genericService.get(id);		
		
		if(result.isEmpty())
			return ControllerUtil.resolveNotFound();
		
		return ControllerUtil.resolve(result);
	}

	@Override
	@DeleteMapping
	public ResponseEntity<Void> del(Long id) {
		genericService.del(id);
		return ResponseEntity.ok().build();
	}
}
