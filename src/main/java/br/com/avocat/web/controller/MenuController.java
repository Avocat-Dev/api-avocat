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
import br.com.avocat.service.MenuService;
import br.com.avocat.util.ConstantesUtil;
import br.com.avocat.util.ControllerUtil;
import br.com.avocat.web.request.MenuRequest;
import br.com.avocat.web.response.EscritorioResponse;

@RestController
@RequestMapping(ConstantesUtil.PATH_USUARIO_V1 + "/menus")
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	
	@PostMapping
	public ResponseEntity<Void> save(@RequestBody MenuRequest request) {
		menuService.criarMenu(request);		
		return ControllerUtil.resolveVoid();
	}

	@GetMapping("/{id}")
	public ResponseEntity<EscritorioResponse> get(Long id) {
		var result = menuService.get(id);
		
		if(result.isEmpty())
			return ControllerUtil.resolveNotFound();
		
		return ControllerUtil.resolve(result);
	}
}
