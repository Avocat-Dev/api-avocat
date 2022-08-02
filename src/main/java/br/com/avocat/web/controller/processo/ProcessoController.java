package br.com.avocat.web.controller.processo;

import br.com.avocat.exception.AvocatException;
import br.com.avocat.persistence.model.processo.Processo;
import br.com.avocat.persistence.model.processo.ValorCausa;
import br.com.avocat.service.processo.ProcessoService;
import br.com.avocat.util.ConstantesUtil;
import br.com.avocat.util.ControllerUtil;
import br.com.avocat.web.controller.ContratoController;
import br.com.avocat.web.response.ContratoResponse;
import br.com.avocat.web.response.ProcessoAllResponse;
import br.com.avocat.web.response.ProcessoResponse;
import br.com.avocat.web.response.ValorCausaResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/all")
    public ResponseEntity<List<ProcessoAllResponse>> all() {

        try {
            var result = processoService.all();
            return ControllerUtil.resolveAll(result);

        } catch (Exception e) {
            LOGGER.error("Erro ao listar os contratos ", e);
            throw new AvocatException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcessoResponse> get(@PathVariable("id") Long id) {

        try {
            var result = processoService.get(id);

            if (result.isEmpty())
                return ControllerUtil.resolveNotFound();

            return ControllerUtil.resolve(result);

        } catch (Exception e) {
            LOGGER.error("Erro ao buscar o processo ", e);
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
