package br.com.avocat.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import br.com.avocat.persistence.model.Menu;
import br.com.avocat.persistence.model.MenuProduto;
import br.com.avocat.persistence.model.RoleMenu;
import br.com.avocat.persistence.repository.MenuRepositoriy;
import br.com.avocat.persistence.repository.RoleMenuRepository;
import br.com.avocat.persistence.repository.RoleRepository;
import br.com.avocat.web.request.MenuRequest;

@Service
public class MenuService {

	@Autowired
	private MenuRepositoriy menuRepository;
	
	@Autowired
	private RoleMenuRepository roleMenuRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Transactional
	public void criarMenu(MenuRequest menuRequest) {

		var menus = menuRepository.buscarMenus(menuRequest.getMenuIds());
		
		List<MenuProduto> listMenu = new ArrayList<>();

		for (Menu menu : menus) {

				var paiId = menu.getId();
				
				var filhos = buscarFilhos(paiId);
				
				if(!filhos.isEmpty())
					menu.setAuxMenus(filhos);
				
				listMenu.add(new MenuProduto(menu));
			}
		
		var json = converterListaMenuToJson(listMenu);

		var result = salvarMenu(json, menuRequest.getRoleId());
		
		if(result == null || json.isEmpty())
			throw new RuntimeException("Erro ao gravar o menu");
	}

	private String converterListaMenuToJson(List<MenuProduto> listMenu) {
		var json = new Gson().toJson(listMenu);		 
		return StringUtils.remove(json, ",\"auxMenus\":[]");
	}

	@Transactional
	private RoleMenu salvarMenu(String json, Long roleId) {
		
		var roleMenu = roleMenuRepository.buscarRoleId(roleId);
		
		if(roleMenu.isPresent())
			roleMenuRepository.delete(roleMenu.get());				
			
		var role = roleRepository.findById(roleId);
		
		return roleMenuRepository.save(new RoleMenu(null, json, role.get()));
	}

	private List<Menu> buscarFilhos(Long paiId) {
		List<Menu> filhos = new ArrayList<>();
		filhos = menuRepository.buscarMenusFilhos(paiId);
		return filhos;
	}
	
	public String buscarMenuUauario(Long idEscritorio, Long idRole) {
		return roleMenuRepository.buscarRoleMenu(idEscritorio, idRole).getMenu();
	}
	
	public List<Long> buscarIdsMenu() {			
		return menuRepository.listarIds();				
	}
}
