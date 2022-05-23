package br.com.avocat.persistence.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbsctractAuditaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@CreatedDate
    LocalDateTime createdDate;
    
    @LastModifiedDate
    LocalDateTime lastModifiedDate;
    
    @CreatedBy
    @AttributeOverride(name = "username", column = @Column(name = "created_by"))
    @Embedded
    Username createdBy;
    
    @LastModifiedBy
    @AttributeOverride(name = "username", column = @Column(name = "last_modified_by"))
    @Embedded
    Username lastModifiedBy;
}
