package com.fpmislata.controller;

import com.fpmislata.domain.dto.LookupResponse;
import com.fpmislata.domain.service.RegistroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para consultas asíncronas del padrón de usuarios.
 * Permite al frontend preseleccionar tipos de usuario sin recargar la página.
 */
@RestController
@RequestMapping("/api/lookup")
public class LookupController {

    private final RegistroService registroService;

    public LookupController(RegistroService registroService) {
        this.registroService = registroService;
    }

    /**
     * GET /api/lookup/documento?documento=X&centroId=Y
     * Devuelve los tipos de usuario preseleccionados y el tipo de alerta.
     */
    @GetMapping("/documento")
    public ResponseEntity<LookupResponse> lookupDocumento(
            @RequestParam String documento,
            @RequestParam Long centroId
    ) {
        LookupResponse response = registroService.consultarLookup(documento, centroId);
        return ResponseEntity.ok(response);
    }
}
