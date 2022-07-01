package br.com.avocat.web.controller.processo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.avocat.persistence.model.processo.Vara;
import br.com.avocat.util.ConstantesUtil;
import br.com.avocat.web.controller.generic.impl.ControllerGenericImpl;

@RestController
@RequestMapping(ConstantesUtil.PATH_PROCESSO_V1 + "/varas")
public class VaraController extends ControllerGenericImpl<Vara> {

}
