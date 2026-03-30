package com.fpmislata.domain.dto;

import java.util.List;

/**
 * DTO de respuesta para el endpoint REST GET /api/lookup/documento.
 * Devuelve los tipos de usuario preseleccionados y la alerta a mostrar.
 */
public record LookupResponse(
        List<String> tiposPreseleccionados,
        String mensaje,
        String tipoAlerta
) {
}
