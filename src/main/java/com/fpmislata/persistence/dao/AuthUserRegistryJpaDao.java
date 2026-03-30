package com.fpmislata.persistence.dao;

import com.fpmislata.domain.model.AuthUserRegistry;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthUserRegistryJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<AuthUserRegistry> findByCentroIdAndDni(Long centroId, String dni) {
        return entityManager
                .createQuery(
                        "SELECT r FROM AuthUserRegistry r " +
                        "LEFT JOIN FETCH r.tipoUsuario " +
                        "WHERE r.centro.id = :centroId AND r.dni = :dni",
                        AuthUserRegistry.class)
                .setParameter("centroId", centroId)
                .setParameter("dni", dni)
                .getResultList();
    }
}
