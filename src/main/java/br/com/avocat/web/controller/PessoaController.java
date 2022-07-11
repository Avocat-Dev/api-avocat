package br.com.avocat.web.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.avocat.exception.AvocatException;
import br.com.avocat.persistence.model.Pessoa;
import br.com.avocat.service.PessoaService;
import br.com.avocat.util.ConstantesUtil;
import br.com.avocat.util.ControllerUtil;
import br.com.avocat.web.response.PessoaResponse;

@RestController
@RequestMapping(ConstantesUtil.PATH_ADMINISTRATIVO_V1 + "/pessoas")
public class PessoaController {

    private static final Logger LOGGER = LogManager.getLogger(PessoaController.class);

    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<PessoaResponse> save(@RequestBody Pessoa pessoa) {

        try {
            var result = pessoaService.save(pessoa);
            return ControllerUtil.resolve(result);

        } catch (Exception e) {
            LOGGER.error("Erro ao salvar pessoa ", e);
            throw new AvocatException(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponse> get(@PathVariable("id") final Long id) {

        try {
            var result = pessoaService.get(id);

            if (result.isEmpty())
                return ControllerUtil.resolveNotFound();

            return ControllerUtil.resolve(result);

        } catch (Exception e) {
            LOGGER.error("Erro ao buscar pessoa ", e);
            throw new AvocatException(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<PessoaResponse>> all() {

        try {
            var result = pessoaService.all();
            return ControllerUtil.resolveAll(result);

        } catch (Exception e) {
            LOGGER.error("Erro ao listar pessoas ", e);
            throw new AvocatException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {

        try {
            pessoaService.delete(id);
        } catch (Exception e) {
            LOGGER.error("Erro ao deletar a pessoa id: " + id, e);
            throw new AvocatException(e.getMessage());
        }

		return ControllerUtil.resolveOk("Pessoa de id " + id + "deletado com sucesso!");
    }
}
