package com.fpmislata.domain.repository;

import com.fpmislata.domain.model.Centro;

import java.util.List;
import java.util.Optional;

public interface CentroRepository {

    List<Centro> findAll();

    Optional<Centro> findById(Long id);
}
