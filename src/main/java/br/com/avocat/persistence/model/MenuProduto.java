package br.com.avocat.persistence.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MenuProduto {
	
	private Long id;
	private String label;
	private String icon;
	private String routerLink;
	private Integer badge;
	private String badgeStyleClass;
	private Long idMenuPai;
	private List<Menu> items;
	
	public MenuProduto(Long id, String label, String icon, String routerLink, Integer badge, String badgeStyleClass,
			Long idMenuPai, List<Menu> items) {
		super();
		this.id = id;
		this.label = label;
		this.icon = icon;
		this.routerLink = routerLink;
		this.badge = badge;
		this.badgeStyleClass = badgeStyleClass;
		this.idMenuPai = idMenuPai;
		this.items = items;
	}
		
	public MenuProduto(Menu menu) {
		this.id = menu.getId();
		this.label = menu.getLabel();
		this.icon = menu.getIcon();
		this.routerLink = menu.getRouterLink();
		this.badge = menu.getBadge();
		this.badgeStyleClass = menu.getBadgeStyleClass();
		this.idMenuPai = menu.getMenuPaiId();
		this.items = menu.getAuxMenus();
	}
}
