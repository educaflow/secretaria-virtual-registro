package com.fpmislata.persistence.repository.impl;

import com.fpmislata.domain.model.AxelorGroup;
import com.fpmislata.domain.repository.AxelorGroupRepository;
import com.fpmislata.persistence.dao.AxelorGroupJpaDao;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AxelorGroupRepositoryImpl implements AxelorGroupRepository {

    private final AxelorGroupJpaDao axelorGroupJpaDao;

    public AxelorGroupRepositoryImpl(AxelorGroupJpaDao axelorGroupJpaDao) {
        this.axelorGroupJpaDao = axelorGroupJpaDao;
    }

    @Override
    public Optional<AxelorGroup> findByCode(String code) {
        return axelorGroupJpaDao.findByCode(code);
    }
}
