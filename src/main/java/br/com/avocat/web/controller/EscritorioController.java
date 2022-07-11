package br.com.avocat.web.controller;

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

import br.com.avocat.exception.AvocatException;
import br.com.avocat.persistence.model.Escritorio;
import br.com.avocat.service.EscritorioService;
import br.com.avocat.util.ConstantesUtil;
import br.com.avocat.util.ControllerUtil;
import br.com.avocat.web.response.EscritorioResponse;

@RestController
@RequestMapping(ConstantesUtil.PATH_ADMINISTRATIVO_V1 + "/escritorios")
public class EscritorioController {

    private static final Logger LOGGER = LogManager.getLogger(EscritorioController.class);

    @Autowired
    private EscritorioService escritorioService;

    @PostMapping
    public ResponseEntity<EscritorioResponse> save(@RequestBody Escritorio escritorio) {

        try {
            var result = escritorioService.save(escritorio);
            return ControllerUtil.resolve(result);

        } catch (Exception e) {
            LOGGER.error("Erro ao salvar escritorio ", e);
            throw new AvocatException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EscritorioResponse> get(@PathVariable("id") Long id) {

        try {
            var result = escritorioService.get(id);

            if (result.isEmpty())
                return ControllerUtil.resolveNotFound();

            return ControllerUtil.resolve(result);

        } catch (Exception e) {
            LOGGER.error("Erro ao buscar escritorio ", e);
            throw new AvocatException(e.getMessage());
        }
    }
}
