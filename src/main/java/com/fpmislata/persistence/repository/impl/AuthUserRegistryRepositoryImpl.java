package com.fpmislata.persistence.repository.impl;

import com.fpmislata.domain.model.AuthUserRegistry;
import com.fpmislata.domain.repository.AuthUserRegistryRepository;
import com.fpmislata.persistence.dao.AuthUserRegistryJpaDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthUserRegistryRepositoryImpl implements AuthUserRegistryRepository {

    private final AuthUserRegistryJpaDao authUserRegistryJpaDao;

    public AuthUserRegistryRepositoryImpl(AuthUserRegistryJpaDao authUserRegistryJpaDao) {
        this.authUserRegistryJpaDao = authUserRegistryJpaDao;
    }

    @Override
    public List<AuthUserRegistry> findByCentroIdAndDni(Long centroId, String dni) {
        return authUserRegistryJpaDao.findByCentroIdAndDni(centroId, dni);
    }
}
