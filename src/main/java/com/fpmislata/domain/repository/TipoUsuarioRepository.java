package com.fpmislata.domain.repository;

import com.fpmislata.domain.model.TipoUsuario;

import java.util.List;
import java.util.Optional;

public interface TipoUsuarioRepository {

    List<TipoUsuario> findAll();

    Optional<TipoUsuario> findById(Long id);

    Optional<TipoUsuario> findByCode(String code);
}
