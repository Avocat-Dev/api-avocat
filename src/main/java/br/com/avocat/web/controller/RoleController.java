package br.com.avocat.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.avocat.persistence.model.Role;
import br.com.avocat.util.ConstantesUtil;
import br.com.avocat.web.controller.generic.impl.ControllerGenericImpl;

@RestController
@RequestMapping(ConstantesUtil.PATH_USUARIO_V1 + "/roles")
public class RoleController extends ControllerGenericImpl<Role> {

}
