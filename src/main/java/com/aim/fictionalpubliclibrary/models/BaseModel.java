package com.aim.fictionalpubliclibrary.models;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

/**
 * BaseModel is a mapped superclass that provides common fields for all entities.
 * It includes an auto-generated ID, creation timestamp, and last modified timestamp.
 * The class is annotated with @EntityListeners to enable auditing features.
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @CreatedDate
    private Date createdAt;
    
    @LastModifiedDate
    private Date lastModifiedAt;
}
