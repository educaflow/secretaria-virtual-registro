package com.fpmislata.domain.repository;

import com.fpmislata.domain.model.AuthUserRegistry;

import java.util.List;

public interface AuthUserRegistryRepository {

    List<AuthUserRegistry> findByCentroIdAndDni(Long centroId, String dni);
}
