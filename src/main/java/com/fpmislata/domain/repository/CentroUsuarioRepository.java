package com.fpmislata.domain.repository;

import com.fpmislata.domain.model.CentroUsuario;

public interface CentroUsuarioRepository {

    CentroUsuario save(CentroUsuario centroUsuario);

    boolean existsByUsuarioDniAndCentroId(String dni, Long centroId);
}
