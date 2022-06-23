package br.com.avocat.persistence.model.processo;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.avocat.persistence.model.generic.GenericEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "areas")
public class Area extends GenericEntity {

	private static final long serialVersionUID = 1L;

	private String descricao;
}
