package br.com.avocat.web.controller.processo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.avocat.persistence.model.processo.Processo;
import br.com.avocat.persistence.model.processo.ValorCausa;
import br.com.avocat.service.processo.ProcessoService;
import br.com.avocat.util.ConstantesUtil;
import br.com.avocat.util.ControllerUtil;
import br.com.avocat.web.response.ProcessoResponse;
import br.com.avocat.web.response.ValorCausaResponse;

@RestController
@RequestMapping(ConstantesUtil.PATH_PROCESSO_V1)
public class ProcessoController {

	@Autowired
	private ProcessoService processoService;

	@PostMapping
	public ResponseEntity<ProcessoResponse> save(@RequestBody Processo data) {
		var result = processoService.save(data);
		return ControllerUtil.resolve(result);
	}

	@PostMapping("/valor-causa")
	public ResponseEntity<ValorCausaResponse> salvarValorCausa(@RequestBody ValorCausa data) {
		var result = processoService.salvarValorCausa(data);
		return ControllerUtil.resolve(result);
	}
}
