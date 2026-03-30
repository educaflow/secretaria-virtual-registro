package com.fpmislata.domain.service.impl;

import com.fpmislata.domain.dto.DatosRegistroDto;
import com.fpmislata.domain.dto.LookupResponse;
import com.fpmislata.domain.model.*;
import com.fpmislata.domain.repository.*;
import com.fpmislata.domain.service.RegistroService;
import com.fpmislata.infrastructure.security.ShiroArgon2PasswordEncoder;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroServiceImpl implements RegistroService {

    private final CentroRepository centroRepository;
    private final AxelorUserRepository axelorUserRepository;
    private final AxelorGroupRepository axelorGroupRepository;
    private final CentroUsuarioRepository centroUsuarioRepository;
    private final TipoUsuarioRepository tipoUsuarioRepository;
    private final CentroUsuarioTipoUsuarioRepository centroUsuarioTipoUsuarioRepository;
    private final AuthUserRegistryRepository authUserRegistryRepository;
    private final ShiroArgon2PasswordEncoder passwordEncoder;

    public RegistroServiceImpl(
            CentroRepository centroRepository,
            AxelorUserRepository axelorUserRepository,
            AxelorGroupRepository axelorGroupRepository,
            CentroUsuarioRepository centroUsuarioRepository,
            TipoUsuarioRepository tipoUsuarioRepository,
            CentroUsuarioTipoUsuarioRepository centroUsuarioTipoUsuarioRepository,
            AuthUserRegistryRepository authUserRegistryRepository,
            ShiroArgon2PasswordEncoder passwordEncoder
    ) {
        this.centroRepository = centroRepository;
        this.axelorUserRepository = axelorUserRepository;
        this.axelorGroupRepository = axelorGroupRepository;
        this.centroUsuarioRepository = centroUsuarioRepository;
        this.tipoUsuarioRepository = tipoUsuarioRepository;
        this.centroUsuarioTipoUsuarioRepository = centroUsuarioTipoUsuarioRepository;
        this.authUserRegistryRepository = authUserRegistryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Centro> obtenerCentros() {
        return centroRepository.findAll();
    }

    @Override
    public void verificarDocumento(String documento, Long centroId) {
        if (centroUsuarioRepository.existsByUsuarioDniAndCentroId(documento, centroId)) {
            throw new IllegalStateException(
                    "El documento ya está registrado en este centro.");
        }
    }

    @Override
    public List<TipoUsuario> obtenerTiposUsuario() {
        return tipoUsuarioRepository.findAll();
    }

    @Override
    public LookupResponse consultarLookup(String documento, Long centroId) {
        Centro centro = centroRepository.findById(centroId)
                .orElseThrow(() -> new IllegalArgumentException("Centro no encontrado: " + centroId));

        List<AuthUserRegistry> registros = authUserRegistryRepository.findByCentroIdAndDni(centroId, documento);

        if (registros.isEmpty()) {
            return new LookupResponse(
                    List.of(),
                    "No se han encontrado registros previos para este documento en el centro seleccionado.",
                    "secondary"
            );
        }

        // Separa registros del curso actual y de cursos anteriores
        String cursoActual = centro.getCurso();
        List<AuthUserRegistry> registrosCursoActual = registros.stream()
                .filter(r -> cursoActual != null && cursoActual.equals(r.getCurso()))
                .toList();
        List<AuthUserRegistry> registrosCursoAnterior = registros.stream()
                .filter(r -> cursoActual == null || !cursoActual.equals(r.getCurso()))
                .toList();

        if (!registrosCursoActual.isEmpty()) {
            List<String> codes = registrosCursoActual.stream()
                    .filter(r -> r.getTipoUsuario() != null)
                    .map(r -> r.getTipoUsuario().getCode())
                    .distinct()
                    .toList();
            return new LookupResponse(
                    codes,
                    "Se han encontrado registros para el curso actual. Se han preseleccionado los tipos de usuario correspondientes.",
                    "success"
            );
        }

        // Solo registros de cursos anteriores — mapear Profesor→Exprofesor, Alumno→Exalumno
        List<String> ids = registrosCursoAnterior.stream()
                .filter(r -> r.getTipoUsuario() != null)
                .map(r -> mapearTipoExCursoAnterior(r.getTipoUsuario().getCode()))
                .filter(code -> code != null)
                .distinct()
                .toList();
        return new LookupResponse(
                ids,
                "Se han encontrado registros de cursos anteriores. Compruebe los tipos de usuario preseleccionados.",
                "warning"
        );
    }

    /**
     * Dado el code de un TipoUsuario de un registro de año anterior,
     * devuelve el code del tipo "ex-" correspondiente:
     *   profesor  → exprofesor
     *   alumno    → exalumno
     * Cualquier otro tipo (familiar, profesor externo…) no tiene equivalente ex- y se descarta.
     */
    private String mapearTipoExCursoAnterior(String code) {
        if (code == null) return null;
        return switch (code.toUpperCase()) {
            case "PROFESOR" -> "EXPROFESOR";
            case "ALUMNO"   -> "EXALUMNO";
            default         -> null;
        };
    }

    @Override
    @Transactional
    public void registrar(DatosRegistroDto dto) {
        // 1. Verificar que el email no está ya en uso
        if (axelorUserRepository.existsByCode(dto.getEmail())) {
            throw new IllegalStateException(
                    "El email ya está registrado como usuario. Por favor, utilice otro email.");
        }

        // 2. Verificar que las contraseñas coinciden
        if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
            throw new IllegalStateException("Las contraseñas no coinciden.");
        }

        // 3. Obtener el centro
        Centro centro = centroRepository.findById(dto.getCentroId())
                .orElseThrow(() -> new IllegalArgumentException("Centro no encontrado: " + dto.getCentroId()));

        // 4. Obtener el grupo "users"
        AxelorGroup grupo = axelorGroupRepository.findByCode("users")
                .orElseThrow(() -> new IllegalStateException(
                        "No se encontró el grupo 'users' en la base de datos."));

        // 5. Crear y guardar AxelorUser
        AxelorUser user = new AxelorUser();
        user.setCode(dto.getEmail());
        user.setEmail(dto.getEmail());
        user.setName(dto.getNombre() + " " + dto.getApellidos());
        user.setNombre(dto.getNombre());
        user.setApellidos(dto.getApellidos());
        user.setDni(dto.getDocumento());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setLanguage("es");
        user.setBlocked(false);
        user.setGroup(grupo);
        user.setCentroActivo(centro);

        AxelorUser savedUser = axelorUserRepository.save(user);

        // 6. Crear y guardar CentroUsuario
        CentroUsuario centroUsuario = new CentroUsuario();
        centroUsuario.setUsuario(savedUser);
        centroUsuario.setCentro(centro);

        CentroUsuario savedCentroUsuario = centroUsuarioRepository.save(centroUsuario);

        // 7. Crear CentroUsuarioTipoUsuario por cada tipo seleccionado
        List<String> tiposCodes = dto.getTiposUsuario();
        if (tiposCodes != null && !tiposCodes.isEmpty()) {
            for (String tipoCode : tiposCodes) {
                TipoUsuario tipoUsuario = tipoUsuarioRepository.findByCode(tipoCode)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Tipo de usuario no encontrado: " + tipoCode));

                CentroUsuarioTipoUsuario cutu = new CentroUsuarioTipoUsuario();
                cutu.setCentroUsuario(savedCentroUsuario);
                cutu.setTipoUsuario(tipoUsuario);
                centroUsuarioTipoUsuarioRepository.save(cutu);
            }
        }
    }
}
