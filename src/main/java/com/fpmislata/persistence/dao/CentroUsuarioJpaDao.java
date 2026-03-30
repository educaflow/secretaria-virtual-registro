package com.fpmislata.persistence.dao;

import com.fpmislata.domain.model.CentroUsuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class CentroUsuarioJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    public CentroUsuario save(CentroUsuario centroUsuario) {
        entityManager.persist(centroUsuario);
        return centroUsuario;
    }

    /**
     * Comprueba si existe un CentroUsuario cuyo usuario tenga el DNI indicado
     * y cuyo centro tenga el id indicado.
     */
    public boolean existsByUsuarioDniAndCentroId(String dni, Long centroId) {
        Long count = entityManager
                .createQuery(
                        "SELECT COUNT(cu) FROM CentroUsuario cu " +
                        "WHERE cu.usuario.dni = :dni AND cu.centro.id = :centroId",
                        Long.class)
                .setParameter("dni", dni)
                .setParameter("centroId", centroId)
                .getSingleResult();
        return count > 0;
    }
}
