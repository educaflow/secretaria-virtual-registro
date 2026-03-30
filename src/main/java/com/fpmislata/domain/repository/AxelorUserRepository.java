package com.fpmislata.domain.repository;

import com.fpmislata.domain.model.AxelorUser;

import java.util.Optional;

public interface AxelorUserRepository {

    AxelorUser save(AxelorUser user);

    Optional<AxelorUser> findByCode(String code);

    boolean existsByCode(String code);
}
