package com.fpmislata.persistence.repository.impl;

import com.fpmislata.domain.model.AxelorUser;
import com.fpmislata.domain.repository.AxelorUserRepository;
import com.fpmislata.persistence.dao.AxelorUserJpaDao;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AxelorUserRepositoryImpl implements AxelorUserRepository {

    private final AxelorUserJpaDao axelorUserJpaDao;

    public AxelorUserRepositoryImpl(AxelorUserJpaDao axelorUserJpaDao) {
        this.axelorUserJpaDao = axelorUserJpaDao;
    }

    @Override
    public AxelorUser save(AxelorUser user) {
        return axelorUserJpaDao.save(user);
    }

    @Override
    public Optional<AxelorUser> findByCode(String code) {
        return axelorUserJpaDao.findByCode(code);
    }

    @Override
    public boolean existsByCode(String code) {
        return axelorUserJpaDao.existsByCode(code);
    }
}
