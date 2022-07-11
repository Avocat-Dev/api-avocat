package br.com.avocat.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.avocat.persistence.model.Menu;

@Repository
public interface MenuRepositoriy extends JpaRepository<Menu, Long> {
	
	static final String BUSCA_MENUS_POR_IDS = "select * from usu_menus where id in (?1) order by id asc;";
	
	static final String LISTAR_IDS = "select id from usu_menus order by id";
	
	@Query(value = BUSCA_MENUS_POR_IDS, nativeQuery = true)
	List<Menu> buscarMenus(List<Integer> listaMenus);
	
	@Query(value = LISTAR_IDS, nativeQuery = true)
	List<Long> listarIds();
	
	@Query(value = "select m from Menu m where m.menuPaiId = :paiId")
	List<Menu> buscarMenusFilhos(@Param("paiId") Long paiId);
}
