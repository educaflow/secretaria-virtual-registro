package com.fpmislata.domain.repository;

import com.fpmislata.domain.model.AxelorGroup;

import java.util.Optional;

public interface AxelorGroupRepository {

    Optional<AxelorGroup> findByCode(String code);
}
