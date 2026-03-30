package com.fpmislata.domain.model;

import jakarta.persistence.*;

/**
 * Entidad JPA que mapea la tabla auth_group de Axelor.
 * Solo lectura — se usa para asociar el grupo "users" al nuevo usuario.
 */
@Entity
@Table(name = "auth_group")
public class AxelorGroup {

    @Id
    private Long id;

    @Version
    private Integer version;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    public AxelorGroup() {
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
