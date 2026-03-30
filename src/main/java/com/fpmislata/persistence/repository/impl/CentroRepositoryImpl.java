package com.fpmislata.persistence.repository.impl;

import com.fpmislata.domain.model.Centro;
import com.fpmislata.domain.repository.CentroRepository;
import com.fpmislata.persistence.dao.CentroJpaDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CentroRepositoryImpl implements CentroRepository {

    private final CentroJpaDao centroJpaDao;

    public CentroRepositoryImpl(CentroJpaDao centroJpaDao) {
        this.centroJpaDao = centroJpaDao;
    }

    @Override
    public List<Centro> findAll() {
        return centroJpaDao.findAll();
    }

    @Override
    public Optional<Centro> findById(Long id) {
        return centroJpaDao.findById(id);
    }
}
