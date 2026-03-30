package com.fpmislata.domain.model;

import jakarta.persistence.*;

/**
 * Entidad JPA que mapea la tabla security_tipo_usuario de Axelor.
 * Extiende SecurityActor mediante herencia JOINED.
 * Solo lectura — Axelor gestiona esta tabla.
 */
@Entity
@Table(name = "security_tipo_usuario")
@PrimaryKeyJoinColumn(name = "id")
public class TipoUsuario extends SecurityActor {

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    public TipoUsuario() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
