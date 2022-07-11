package br.com.avocat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.avocat.persistence.model.Escritorio;
import br.com.avocat.service.EscritorioService;
import br.com.avocat.util.ConstantesUtil;
import br.com.avocat.util.ControllerUtil;

@RestController
@RequestMapping(ConstantesUtil.PATH_ADMINISTRATIVO_V1 + "/escritorios")
public class EscritorioController {
	
	@Autowired
	private EscritorioService escritorioService;
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody Escritorio data) {
		try {
			var result = escritorioService.save(data);		
			return ControllerUtil.resolve(result);
			
		} catch (Exception e) {
			return ControllerUtil.resolveBadRequest(e);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> get(Long id) {
		var result = escritorioService.get(id);
		
		if(result.isEmpty())
			return ControllerUtil.resolveNotFound();
		
		return ControllerUtil.resolve(result);
	}
}
