package com.fpmislata.persistence.dao;

import com.fpmislata.domain.model.CentroUsuarioTipoUsuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class CentroUsuarioTipoUsuarioJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    public CentroUsuarioTipoUsuario save(CentroUsuarioTipoUsuario centroUsuarioTipoUsuario) {
        entityManager.persist(centroUsuarioTipoUsuario);
        return centroUsuarioTipoUsuario;
    }
}
