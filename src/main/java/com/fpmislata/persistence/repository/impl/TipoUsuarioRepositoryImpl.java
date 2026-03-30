package com.fpmislata.persistence.repository.impl;

import com.fpmislata.domain.model.TipoUsuario;
import com.fpmislata.domain.repository.TipoUsuarioRepository;
import com.fpmislata.persistence.dao.TipoUsuarioJpaDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TipoUsuarioRepositoryImpl implements TipoUsuarioRepository {

    private final TipoUsuarioJpaDao tipoUsuarioJpaDao;

    public TipoUsuarioRepositoryImpl(TipoUsuarioJpaDao tipoUsuarioJpaDao) {
        this.tipoUsuarioJpaDao = tipoUsuarioJpaDao;
    }

    @Override
    public List<TipoUsuario> findAll() {
        return tipoUsuarioJpaDao.findAll();
    }

    @Override
    public Optional<TipoUsuario> findById(Long id) {
        return tipoUsuarioJpaDao.findById(id);
    }

    @Override
    public Optional<TipoUsuario> findByCode(String code) {
        return tipoUsuarioJpaDao.findByCode(code);
    }
}
