package com.fpmislata.domain.model;

import jakarta.persistence.*;

/**
 * Entidad JPA que mapea la tabla auth_user de Axelor.
 * Se crea al registrar un nuevo usuario.
 *
 * Convención de columnas FK de Axelor:
 *   - group  → group_id  (excepción explícita del modelo base de Axelor)
 *   - centroActivo → centro_activo  (snake_case sin sufijo _id)
 */
@Entity
@Table(name = "auth_user")
public class AxelorUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_user_seq_gen")
    @SequenceGenerator(name = "auth_user_seq_gen", sequenceName = "auth_user_seq", allocationSize = 1)
    private Long id;

    @Version
    private Integer version;

    /** Login único — se usa el email. */
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    /** Nombre completo (nombre + apellidos). */
    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "language")
    private String language;

    @Column(name = "blocked")
    private Boolean blocked;

    @Column(name = "dni")
    private String dni;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellidos")
    private String apellidos;

    /** FK a auth_group — Axelor usa explícitamente group_id. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private AxelorGroup group;

    /** FK a common_centro — Axelor usa snake_case sin _id: centro_activo. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "centro_activo")
    private Centro centroActivo;

    public AxelorUser() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public AxelorGroup getGroup() {
        return group;
    }

    public void setGroup(AxelorGroup group) {
        this.group = group;
    }

    public Centro getCentroActivo() {
        return centroActivo;
    }

    public void setCentroActivo(Centro centroActivo) {
        this.centroActivo = centroActivo;
    }
}
