package com.fpmislata.domain.model;

import jakarta.persistence.*;

/**
 * Clase abstracta padre para la herencia JOINED de Axelor.
 * Mapea la tabla security_security_actor.
 *
 * CentroUsuario y TipoUsuario extienden esta clase.
 * Al persistir una subclase, JPA escribe en security_security_actor
 * y en la tabla hija correspondiente, compartiendo el mismo id.
 */
@Entity
@Table(name = "security_security_actor")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class SecurityActor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "security_security_actor_seq_gen")
    @SequenceGenerator(name = "security_security_actor_seq_gen", sequenceName = "security_security_actor_seq", allocationSize = 1)
    private Long id;

    @Version
    private Integer version;

    public SecurityActor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
