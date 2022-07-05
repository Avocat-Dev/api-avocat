package br.com.avocat.persistence.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.avocat.persistence.model.generic.GenericEntity;

@Entity
@Table(name = "usu_roles")
public class Role extends GenericEntity {
	private static final long serialVersionUID = 1L;
}
