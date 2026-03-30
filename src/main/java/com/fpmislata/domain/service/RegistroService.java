package com.fpmislata.domain.service;

import com.fpmislata.domain.dto.DatosRegistroDto;
import com.fpmislata.domain.dto.LookupResponse;
import com.fpmislata.domain.model.Centro;
import com.fpmislata.domain.model.TipoUsuario;

import java.util.List;

public interface RegistroService {

    /**
     * Devuelve todos los centros disponibles para el selector del paso 1.
     */
    List<Centro> obtenerCentros();

    /**
     * Comprueba si el documento ya está registrado en el centro indicado.
     * Lanza IllegalStateException si ya existe.
     */
    void verificarDocumento(String documento, Long centroId);

    /**
     * Devuelve todos los tipos de usuario disponibles.
     */
    List<TipoUsuario> obtenerTiposUsuario();

    /**
     * Consulta el padrón (AuthUserRegistry) y determina los tipos preseleccionados
     * y el tipo de alerta a mostrar en el paso 2.
     */
    LookupResponse consultarLookup(String documento, Long centroId);

    /**
     * Ejecuta el registro completo: crea AxelorUser, CentroUsuario y
     * CentroUsuarioTipoUsuario para cada tipo seleccionado.
     * Lanza IllegalStateException si el email ya está en uso.
     */
    void registrar(DatosRegistroDto dto);
}
