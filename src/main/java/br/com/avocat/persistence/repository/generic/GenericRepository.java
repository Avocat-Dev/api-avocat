package br.com.avocat.persistence.repository.generic;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.avocat.persistence.model.generic.GenericEntity;

public interface GenericRepository<T extends GenericEntity> extends JpaRepository<T, Long> {

}
