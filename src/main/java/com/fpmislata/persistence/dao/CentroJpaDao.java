package com.fpmislata.persistence.dao;

import com.fpmislata.domain.model.Centro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CentroJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Centro> findAll() {
        return entityManager
                .createQuery("SELECT c FROM Centro c ORDER BY c.name", Centro.class)
                .getResultList();
    }

    public Optional<Centro> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Centro.class, id));
    }
}
