package com.fpmislata.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para el paso 1 del registro: verificación de documento y centro.
 */
public class VerificarDocumentoDto {

    @NotBlank(message = "El tipo de documento es obligatorio")
    private String tipoDocumento;

    @NotBlank(message = "El número de documento es obligatorio")
    private String documento;

    @NotNull(message = "Debe seleccionar un centro")
    private Long centroId;

    public VerificarDocumentoDto() {
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Long getCentroId() {
        return centroId;
    }

    public void setCentroId(Long centroId) {
        this.centroId = centroId;
    }
}
