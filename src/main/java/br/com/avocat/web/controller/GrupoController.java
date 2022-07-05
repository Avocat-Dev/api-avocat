package br.com.avocat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.avocat.persistence.model.Grupo;
import br.com.avocat.persistence.repository.RoleRepository;
import br.com.avocat.service.GrupoService;
import br.com.avocat.util.ConstantesUtil;
import br.com.avocat.util.ControllerUtil;

@RestController
@RequestMapping(ConstantesUtil.PATH_USUARIO_V1 + "/grupos")
public class GrupoController {

	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@PostMapping
	public ResponseEntity<Grupo> save(@RequestBody Grupo data) {
		
		var role = roleRepository.findById(data.getRoleId());
		data.setRole(role.get());
		
		var result = grupoService.save(data);		
		return ControllerUtil.resolve(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Grupo> get(Long id) {
		var result = grupoService.get(id);
		
		if(result.isEmpty())
			return ControllerUtil.resolveNotFound();
		
		return ControllerUtil.resolve(result);
	}
}
