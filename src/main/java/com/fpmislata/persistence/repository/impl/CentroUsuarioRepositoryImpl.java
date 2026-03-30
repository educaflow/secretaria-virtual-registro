package com.fpmislata.persistence.repository.impl;

import com.fpmislata.domain.model.CentroUsuario;
import com.fpmislata.domain.repository.CentroUsuarioRepository;
import com.fpmislata.persistence.dao.CentroUsuarioJpaDao;
import org.springframework.stereotype.Repository;

@Repository
public class CentroUsuarioRepositoryImpl implements CentroUsuarioRepository {

    private final CentroUsuarioJpaDao centroUsuarioJpaDao;

    public CentroUsuarioRepositoryImpl(CentroUsuarioJpaDao centroUsuarioJpaDao) {
        this.centroUsuarioJpaDao = centroUsuarioJpaDao;
    }

    @Override
    public CentroUsuario save(CentroUsuario centroUsuario) {
        return centroUsuarioJpaDao.save(centroUsuario);
    }

    @Override
    public boolean existsByUsuarioDniAndCentroId(String dni, Long centroId) {
        return centroUsuarioJpaDao.existsByUsuarioDniAndCentroId(dni, centroId);
    }
}
