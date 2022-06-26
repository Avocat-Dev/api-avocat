package br.com.avocat.web.controller.processo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.avocat.persistence.model.processo.TipoValor;
import br.com.avocat.web.controller.generic.impl.ControllerGenericImpl;

@RestController
@RequestMapping("/v1/processos/tipos-valores")
public class TipoValorController extends ControllerGenericImpl<TipoValor> {

}
