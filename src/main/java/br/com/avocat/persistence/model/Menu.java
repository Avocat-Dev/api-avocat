package br.com.avocat.persistence.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Michael
 *
 */
/**
 * @author Michael
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "usu_menus")
public class Menu implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "pessoa", sequenceName = "pessoa", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa")
	private Long id;
	
	@Column(nullable = false)
	private String label;
	
	@Column(nullable = false)
	private String icon;
	
	@Column(nullable = false)
	private String routerLink;
	
	private Integer badge;
	
	@Column(name = "style")
	private String badgeStyleClass; 
	
	@Column(name = "id_menu_pai")
	private Long menuPaiId;	
	
	
	/**
	 * auxMenus atributo usado para auxiliar a 
	 * criacao de um menu customizado para a Role.
	 */
	@Transient
	private List<Menu> auxMenus = new ArrayList<>();
}
