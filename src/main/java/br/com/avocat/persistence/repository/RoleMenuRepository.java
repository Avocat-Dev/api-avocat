package br.com.avocat.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.avocat.persistence.model.RoleMenu;

@Repository
public interface RoleMenuRepository extends JpaRepository<RoleMenu, Long>{

	static final String BUSCAR_MENU_ROLE = "select * from role_menu where id_escritorio = ?1 and id_role = ?2";
	
	static final String BUSCAR_IDS_MENU = "select ids_menus from role_menu where id_escritorio = ?1 and id_role = ?2";
	
	static final String DELETE_ROLE_MENU = "delete from role_menu where id_escritorio = ?1 and id_role = ?2";
	
	@Query(value = BUSCAR_MENU_ROLE, nativeQuery = true)
	RoleMenu buscarRoleMenu(Long idEscritorio, Long idRole);

	@Modifying
	@Query(value = DELETE_ROLE_MENU, nativeQuery = true)
	void deletaMenuRole(Long idEscritorio, Long idRole);

	@Query(value = BUSCAR_IDS_MENU, nativeQuery = true)
	String buscarIdsMenuIdRole(Long idEscritorio, Long idRole);

	@Query(value = "select * from usu_menus_roles where role_id = :roleId", nativeQuery = true)
	Optional<RoleMenu> buscarRoleId(@Param("roleId") Long roleId);
}
