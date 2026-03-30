package com.fpmislata.persistence.dao;

import com.fpmislata.domain.model.AxelorGroup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AxelorGroupJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<AxelorGroup> findByCode(String code) {
        try {
            AxelorGroup group = entityManager
                    .createQuery("SELECT g FROM AxelorGroup g WHERE g.code = :code", AxelorGroup.class)
                    .setParameter("code", code)
                    .getSingleResult();
            return Optional.of(group);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
