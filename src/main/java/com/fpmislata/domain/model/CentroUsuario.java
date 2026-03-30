package com.fpmislata.domain.model;

import jakarta.persistence.*;

/**
 * Entidad JPA que mapea la tabla security_centro_usuario de Axelor.
 * Extiende SecurityActor mediante herencia JOINED.
 *
 * Al insertar, JPA escribe en security_security_actor (id, version)
 * y en security_centro_usuario (id, centro, usuario) con el mismo id.
 *
 * Convención FK de Axelor: snake_case del nombre de campo sin sufijo _id.
 *   - centro  → columna centro
 *   - usuario → columna usuario
 */
@Entity
@Table(name = "security_centro_usuario")
@PrimaryKeyJoinColumn(name = "id")
public class CentroUsuario extends SecurityActor {

    /** FK a common_centro — columna: centro */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "centro")
    private Centro centro;

    /** FK a auth_user — columna: usuario */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario")
    private AxelorUser usuario;

    public CentroUsuario() {
    }

    public Centro getCentro() {
        return centro;
    }

    public void setCentro(Centro centro) {
        this.centro = centro;
    }

    public AxelorUser getUsuario() {
        return usuario;
    }

    public void setUsuario(AxelorUser usuario) {
        this.usuario = usuario;
    }
}
