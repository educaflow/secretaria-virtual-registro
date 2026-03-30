package com.fpmislata.domain.model;

import jakarta.persistence.*;

/**
 * Entidad JPA que mapea la tabla common_centro de Axelor.
 * Solo lectura — Axelor gestiona esta tabla.
 */
@Entity
@Table(name = "common_centro")
public class Centro {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "common_centro_seq")
    @SequenceGenerator(name = "common_centro_seq", sequenceName = "common_centro_seq", allocationSize = 1)
    private Long id;

    @Version
    private Integer version;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "curso")
    private String curso;

    public Centro() {
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

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}
