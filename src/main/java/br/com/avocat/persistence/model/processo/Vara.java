package br.com.avocat.persistence.model.processo;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.avocat.persistence.model.generic.GenericEntity;

@Entity
@Table(name = "pro_varas")
public class Vara extends GenericEntity {
	private static final long serialVersionUID = 1L;
}
