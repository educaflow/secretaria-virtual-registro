package com.fpmislata.persistence.dao;

import com.fpmislata.domain.model.AxelorUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AxelorUserJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    public AxelorUser save(AxelorUser user) {
        entityManager.persist(user);
        return user;
    }

    public Optional<AxelorUser> findByCode(String code) {
        try {
            AxelorUser user = entityManager
                    .createQuery("SELECT u FROM AxelorUser u WHERE u.code = :code", AxelorUser.class)
                    .setParameter("code", code)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public boolean existsByCode(String code) {
        Long count = entityManager
                .createQuery("SELECT COUNT(u) FROM AxelorUser u WHERE u.code = :code", Long.class)
                .setParameter("code", code)
                .getSingleResult();
        return count > 0;
    }
}
