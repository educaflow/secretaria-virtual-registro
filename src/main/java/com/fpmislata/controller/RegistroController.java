package com.fpmislata.controller;

import com.fpmislata.domain.dto.DatosRegistroDto;
import com.fpmislata.domain.dto.LookupResponse;
import com.fpmislata.domain.dto.VerificarDocumentoDto;
import com.fpmislata.domain.model.Centro;
import com.fpmislata.domain.model.TipoUsuario;
import com.fpmislata.domain.service.RegistroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    private final RegistroService registroService;

    @Value("${app.secretaria-virtual.url}")
    private String secretariaVirtualUrl;

    public RegistroController(RegistroService registroService) {
        this.registroService = registroService;
    }

    /**
     * GET /registro — Muestra el formulario del paso 1 (verificación de documento).
     */
    @GetMapping
    public String mostrarPaso1(Model model) {
        List<Centro> centros = registroService.obtenerCentros();
        model.addAttribute("centros", centros);
        model.addAttribute("verificarDto", new VerificarDocumentoDto());
        return "registro/paso1";
    }

    /**
     * POST /registro/verificar — Verifica el documento y redirige al paso 2.
     */
    @PostMapping("/verificar")
    public String verificarDocumento(
            @Valid @ModelAttribute("verificarDto") VerificarDocumentoDto dto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            List<Centro> centros = registroService.obtenerCentros();
            model.addAttribute("centros", centros);
            return "registro/paso1";
        }

        try {
            registroService.verificarDocumento(dto.getDocumento(), dto.getCentroId());
        } catch (IllegalStateException e) {
            List<Centro> centros = registroService.obtenerCentros();
            model.addAttribute("centros", centros);
            model.addAttribute("errorVerificacion", e.getMessage());
            return "registro/paso1";
        }

        return "redirect:/registro/datos"
                + "?documento=" + dto.getDocumento()
                + "&tipoDocumento=" + dto.getTipoDocumento()
                + "&centroId=" + dto.getCentroId();
    }

    /**
     * GET /registro/datos — Muestra el formulario del paso 2 (datos personales).
     */
    @GetMapping("/datos")
    public String mostrarPaso2(
            @RequestParam String documento,
            @RequestParam String tipoDocumento,
            @RequestParam Long centroId,
            Model model
    ) {
        List<TipoUsuario> tiposUsuario = registroService.obtenerTiposUsuario();
        LookupResponse lookup = registroService.consultarLookup(documento, centroId);

        DatosRegistroDto datosDto = new DatosRegistroDto();
        datosDto.setDocumento(documento);
        datosDto.setTipoDocumento(tipoDocumento);
        datosDto.setCentroId(centroId);

        model.addAttribute("tiposUsuario", tiposUsuario);
        model.addAttribute("lookup", lookup);
        model.addAttribute("datosDto", datosDto);
        model.addAttribute("documento", documento);
        model.addAttribute("tipoDocumento", tipoDocumento);
        model.addAttribute("centroId", centroId);

        return "registro/paso2";
    }

    /**
     * POST /registro — Procesa el registro completo.
     */
    @PostMapping
    public String registrar(
            @Valid @ModelAttribute("datosDto") DatosRegistroDto dto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            List<TipoUsuario> tiposUsuario = registroService.obtenerTiposUsuario();
            LookupResponse lookup = registroService.consultarLookup(dto.getDocumento(), dto.getCentroId());
            model.addAttribute("tiposUsuario", tiposUsuario);
            model.addAttribute("lookup", lookup);
            return "registro/paso2";
        }

        try {
            registroService.registrar(dto);
        } catch (IllegalStateException e) {
            List<TipoUsuario> tiposUsuario = registroService.obtenerTiposUsuario();
            LookupResponse lookup = registroService.consultarLookup(dto.getDocumento(), dto.getCentroId());
            model.addAttribute("tiposUsuario", tiposUsuario);
            model.addAttribute("lookup", lookup);
            model.addAttribute("errorRegistro", e.getMessage());
            return "registro/paso2";
        }

        redirectAttributes.addFlashAttribute("registroExitoso", true);
        return "redirect:/registro/exito";
    }

    /**
     * GET /registro/exito — Pantalla de confirmación del registro.
     */
    @GetMapping("/exito")
    public String mostrarExito(Model model) {
        model.addAttribute("secretariaVirtualUrl", secretariaVirtualUrl);
        return "registro/exito";
    }
}
