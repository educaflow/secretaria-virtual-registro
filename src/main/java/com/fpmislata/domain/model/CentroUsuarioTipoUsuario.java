package com.fpmislata.domain.model;

import jakarta.persistence.*;

/**
 * Entidad JPA que mapea la tabla security_centro_usuario_tipo_usuario de Axelor.
 * Relaciona un CentroUsuario con un TipoUsuario.
 *
 * Convención FK de Axelor: snake_case del nombre de campo sin sufijo _id.
 *   - centroUsuario → columna centro_usuario
 *   - tipoUsuario   → columna tipo_usuario
 */
@Entity
@Table(name = "security_centro_usuario_tipo_usuario")
public class CentroUsuarioTipoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "security_centro_usuario_tipo_usuario_seq_gen")
    @SequenceGenerator(
            name = "security_centro_usuario_tipo_usuario_seq_gen",
            sequenceName = "security_centro_usuario_tipo_usuario_seq",
            allocationSize = 1
    )
    private Long id;

    @Version
    private Integer version;

    /** FK a security_centro_usuario — columna: centro_usuario */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "centro_usuario")
    private CentroUsuario centroUsuario;

    /** FK a security_tipo_usuario — columna: tipo_usuario */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_usuario")
    private TipoUsuario tipoUsuario;

    public CentroUsuarioTipoUsuario() {
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

    public CentroUsuario getCentroUsuario() {
        return centroUsuario;
    }

    public void setCentroUsuario(CentroUsuario centroUsuario) {
        this.centroUsuario = centroUsuario;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
