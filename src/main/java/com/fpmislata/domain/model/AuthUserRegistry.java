package com.fpmislata.domain.model;

import jakarta.persistence.*;

/**
 * Entidad JPA que mapea la tabla security_auth_user_registry de Axelor.
 * Solo lectura — contiene el padrón de usuarios esperados por centro y curso.
 *
 * Se usa para preseleccionar tipos de usuario en el paso 2 del registro.
 *
 * Convención FK de Axelor: snake_case del nombre de campo sin sufijo _id.
 *   - centro      → columna centro
 *   - tipoUsuario → columna tipo_usuario
 */
@Entity
@Table(name = "security_auth_user_registry")
public class AuthUserRegistry {

    @Id
    private Long id;

    @Version
    private Integer version;

    /** FK a common_centro — columna: centro */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "centro")
    private Centro centro;

    @Column(name = "curso")
    private String curso;

    @Column(name = "dni")
    private String dni;

    /** FK a security_tipo_usuario — columna: tipo_usuario */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_usuario")
    private TipoUsuario tipoUsuario;

    public AuthUserRegistry() {
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

    public Centro getCentro() {
        return centro;
    }

    public void setCentro(Centro centro) {
        this.centro = centro;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
