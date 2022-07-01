package br.com.avocat.web.controller.processo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.avocat.persistence.model.processo.Rito;
import br.com.avocat.util.ConstantesUtil;
import br.com.avocat.web.controller.generic.impl.ControllerGenericImpl;

@RestController
@RequestMapping(ConstantesUtil.PATH_PROCESSO_V1 + "/ritos")
public class RitoController extends ControllerGenericImpl<Rito> {

}
