package br.com.avocat.web.controller;

import br.com.avocat.exception.AvocatException;
import br.com.avocat.service.MenuService;
import br.com.avocat.util.ConstantesUtil;
import br.com.avocat.util.ControllerUtil;
import br.com.avocat.web.request.MenuRequest;
import br.com.avocat.web.response.EscritorioResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ConstantesUtil.PATH_USUARIO_V1 + "/menus")
public class MenuController {

    private static final Logger LOGGER = LogManager.getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody MenuRequest request) {

        try {
            menuService.criarMenu(request);
            return ControllerUtil.resolveVoid();

        } catch (Exception e) {
            LOGGER.error("Erro ao salvar escritorio ", e);
            throw new AvocatException(e.getMessage());
        }
    }
}
