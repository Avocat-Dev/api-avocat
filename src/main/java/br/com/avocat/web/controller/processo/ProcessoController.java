package br.com.avocat.web.controller.processo;

import java.util.List;

import br.com.avocat.exception.AvocatException;
import br.com.avocat.web.controller.ContratoController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger LOGGER = LogManager.getLogger(ContratoController.class);

    @Autowired
    private ProcessoService processoService;

    @PostMapping
    public ResponseEntity<ProcessoResponse> save(@RequestBody Processo processo) {

        try {
            var result = processoService.save(processo);
            return ControllerUtil.resolve(result);
        } catch (Exception e) {
            LOGGER.error("Erro ao deletar a contrato", e);
            throw new AvocatException(e.getMessage());
        }
    }

    @PostMapping("/valor-causa")
    public ResponseEntity<ValorCausaResponse> salvarValorCausa(@RequestBody ValorCausa valorCausa) {

        try {
            var result = processoService.salvarValorCausa(valorCausa);
            return ControllerUtil.resolve(result);
        } catch (Exception e) {
            LOGGER.error("Erro ao deletar a contrato", e);
            throw new AvocatException(e.getMessage());
        }
    }
}
