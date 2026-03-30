package com.fpmislata.persistence.dao;

import com.fpmislata.domain.model.TipoUsuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TipoUsuarioJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    private static final List<String> CODIGOS_PERMITIDOS = List.of(
            "ALUMNO", "EXALUMNO", "PROFESOR", "EXPROFESOR", "PROFESOR_EXTERNO", "FAMILIAR"
    );

    public List<TipoUsuario> findAll() {
        return entityManager
                .createQuery(
                        "SELECT t FROM TipoUsuario t WHERE t.code IN :codigos ORDER BY t.name",
                        TipoUsuario.class)
                .setParameter("codigos", CODIGOS_PERMITIDOS)
                .getResultList();
    }

    public Optional<TipoUsuario> findById(Long id) {
        return Optional.ofNullable(entityManager.find(TipoUsuario.class, id));
    }

    public Optional<TipoUsuario> findByCode(String code) {
        return entityManager
                .createQuery("SELECT t FROM TipoUsuario t WHERE t.code = :code", TipoUsuario.class)
                .setParameter("code", code)
                .getResultStream()
                .findFirst();
    }
}
