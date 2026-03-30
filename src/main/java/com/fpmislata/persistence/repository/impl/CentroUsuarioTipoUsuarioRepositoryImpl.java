package com.fpmislata.persistence.repository.impl;

import com.fpmislata.domain.model.CentroUsuarioTipoUsuario;
import com.fpmislata.domain.repository.CentroUsuarioTipoUsuarioRepository;
import com.fpmislata.persistence.dao.CentroUsuarioTipoUsuarioJpaDao;
import org.springframework.stereotype.Repository;

@Repository
public class CentroUsuarioTipoUsuarioRepositoryImpl implements CentroUsuarioTipoUsuarioRepository {

    private final CentroUsuarioTipoUsuarioJpaDao centroUsuarioTipoUsuarioJpaDao;

    public CentroUsuarioTipoUsuarioRepositoryImpl(CentroUsuarioTipoUsuarioJpaDao centroUsuarioTipoUsuarioJpaDao) {
        this.centroUsuarioTipoUsuarioJpaDao = centroUsuarioTipoUsuarioJpaDao;
    }

    @Override
    public CentroUsuarioTipoUsuario save(CentroUsuarioTipoUsuario centroUsuarioTipoUsuario) {
        return centroUsuarioTipoUsuarioJpaDao.save(centroUsuarioTipoUsuario);
    }
}
