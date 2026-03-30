# CLAUDE.md

Este archivo proporciona orientación a Claude Code (claude.ai/code) cuando trabaja con el código de este repositorio.

## Comandos

```bash
# Compilar el proyecto
mvn clean package

# Ejecutar los tests
mvn test

# Ejecutar una clase de test específica
mvn test -Dtest=SomeTest

# Ejecutar la aplicación (una vez configurada)
mvn spring-boot:run
```

## Contexto del proyecto

`secretaria-virtual-registro` es una aplicación web Spring Boot dentro de la plataforma `eduflow`. Se integra con la aplicación `secretaria-virtual` basada en Axelor y consume sus entidades de dominio (Centro, User, Municipio, Provincia, ComunidadAutonoma, Conselleria, etc.), cuyas definiciones XML se encuentran en `.claude/skills/axelor-domain/references/entities/`.

La aplicación arranca en el puerto **8081**. Requiere una base de datos PostgreSQL en `localhost:5432` con la base de datos `educaflow`, usuario `educaflow`, contraseña `educaflow`.

La aplicación consiste en un registro de usuarios en dos pasos: primero se verifica el documento (DNI) y el centro, luego se recogen los datos personales y se crean las entidades correspondientes en la base de datos de Axelor (`auth_user`, `security_centro_usuario`, `security_centro_usuario_tipo_usuario`).

- **Group ID:** `com.fpmislata`
- **Versión de Java:** 21

## Arquitectura

Arquitectura en capas — dirección de dependencia estricta: `controller → domain → persistence`.

```
src/main/java/com/fpmislata/
├── controller/          # Controladores Spring MVC o REST; capa delgada, delega en servicios
├── domain/
│   ├── model/           # Entidades de dominio simples (POJOs)
│   ├── service/         # Interfaces de lógica de negocio
│   │   └── impl/        # Implementaciones de servicios
│   ├── repository/      # Interfaces de repositorio (orientadas al dominio)
│   ├── dto/             # Objetos de Transferencia de Datos entre capas
│   └── exception/       # Excepciones específicas del dominio
└── persistence/
    ├── repository/
    │   └── impl/        # Implementaciones de repositorio
    └── dao/             # DAOs para consultas JPA (sin subcarpeta impl)
```

**Restricciones clave:**
- La capa de dominio debe ser independiente de las capas web y de persistencia.
- Los controladores no deben contener lógica de negocio — delegar en servicios o casos de uso.
- Usar DTOs para transferir datos entre capas; no exponer modelos de dominio directamente en los controladores.
- Usar los skills en `.claude/skills/` como guía para cada capa: `daw-controladores`, `daw-servicios`, `daw-repositorios`, `daw-daos`, `daw-dtos`, `daw-modelos`, `daw-validacion`.

## Entidades de dominio Axelor

La aplicación se integra con Axelor. Las definiciones de entidades relevantes (para referencia y mapeo) están en `.claude/skills/axelor-domain/references/entities/`. El nombre de las tablas en Axelor sigue el patrón `{modulo}_{NombreEntidad}` en minúsculas (p. ej., `common_centro`).

Entidades principales: `Centro`, `User`, `Municipio`, `Provincia`, `ComunidadAutonoma`, `Conselleria`, `CentroUsuario`, `TipoUsuario`, `Role`.

## Frontend

Usar **Thymeleaf** para plantillas del lado del servidor y **Bootstrap** para el diseño responsive. Consultar `.claude/skills/thymeleaf/SKILL.md` y `.claude/skills/bootstrap/SKILL.md`.

### Mapeo de la base de datos de Axelor

Esta app NO usa DDL de Hibernate (`ddl-auto=none`). Todas las tablas son propiedad de Axelor. Axelor convierte los nombres de entidad de **camelCase a snake_case**: `{module}_{entity_snake_case}`.

| Entidad JPA | Tabla Axelor |
|---|---|
| `AxelorUser` | `auth_user` |
| `AxelorGroup` | `auth_group` |
| `SecurityActor` | `security_security_actor` |
| `CentroUsuario` | `security_centro_usuario` |
| `TipoUsuario` | `security_tipo_usuario` |
| `CentroUsuarioTipoUsuario` | `security_centro_usuario_tipo_usuario` |
| `Centro` | `common_centro` |
| `AuthUserRegistry` | `security_auth_user_registry` |

`SecurityActor` utiliza herencia JPA **JOINED** — insertar un `CentroUsuario` o `TipoUsuario` escribe una fila en `security_security_actor` y otra en la tabla hija, compartiendo el mismo `id`.

**Herencia JOINED y carga de asociaciones:** al cargar una asociación `@ManyToOne` a una subclase con `FetchType.LAZY`, Hibernate puede no inicializar correctamente los campos de la tabla hija. Usar siempre `LEFT JOIN FETCH` en la query JPQL para estas asociaciones (ver `AuthUserRegistryJpaDao`).

Cada entidad tiene su propia secuencia PostgreSQL (no existe `hibernate_sequence` en esta BD):

| Entidad | Secuencia |
|---|---|
| `AxelorUser` | `auth_user_seq` |
| `SecurityActor` / `CentroUsuario` | `security_security_actor_seq` |
| `CentroUsuarioTipoUsuario` | `security_centro_usuario_tipo_usuario_seq` |

Columnas relevantes de `auth_user` que se rellenan al registrar:

| Campo Java | Columna | Valor |
|---|---|---|
| `code` | `code` | email (login único) |
| `name` | `name` | nombre + apellidos |
| `password` | `password` | hash Argon2id formato Shiro 2 |
| `email` | `email` | email |
| `dni` | `dni` | documento introducido |
| `nombre` | `nombre` | nombre |
| `apellidos` | `apellidos` | apellidos |
| `language` | `language` | `"es"` |
| `group` | `group_id` | FK al grupo con `code = "users"` |
| `centroActivo` | `centro_activo` | FK al centro seleccionado |

Las columnas FK siguen la convención Axelor: nombre simple del campo sin sufijo `_id` (excepto `group_id` que lo define explícitamente el modelo base de Axelor).

### TipoUsuario

La clave de negocio de `TipoUsuario` es `code` (campo `code` en `security_tipo_usuario`). Los códigos están en **mayúsculas**. En el formulario solo se muestran los 6 tipos permitidos:

| Code | Descripción |
|---|---|
| `ALUMNO` | Alumno |
| `EXALUMNO` | Exalumno |
| `PROFESOR` | Profesor |
| `EXPROFESOR` | Exprofesor |
| `PROFESOR_EXTERNO` | Profesor externo |
| `FAMILIAR` | Familiar |

Los checkboxes del formulario usan `code` como valor (no `id`). `DatosRegistroDto.tiposUsuario` es `List<String>` (codes). `LookupResponse.tiposPreseleccionados` es `List<String>` (codes).

### Encriptación de contraseñas

Axelor usa **Apache Shiro 2 + Argon2id**. El formato almacenado es:

```
$shiro2$argon2id$v=19$t=1,m=65536,p=4$<salt_base64>$<hash_base64>
```

Parámetros: iteraciones=1, memoria=65536 KB, paralelismo=4, sal=16 bytes, hash=32 bytes, codificación Base64 sin padding. Implementado en `ShiroArgon2PasswordEncoder` usando BouncyCastle (`bcprov-jdk18on:1.80`). **No usar BCrypt** — Axelor no lo reconoce.

### Flujo de registro

**Paso 1** — `GET /registro`: el usuario elige tipo de documento, escribe el número y selecciona el centro.

**POST /registro/verificar**:
1. Comprueba si ya existe un `CentroUsuario` con ese `dni` en ese centro → error si existe.
2. Redirige a paso 2 con `documento`, `tipoDocumento` y `centroId` como query params.

**Paso 2** — `GET /registro/datos`: busca en `security_auth_user_registry` por centro + DNI:
- Registro con **`curso = centro.curso`** → preselecciona los tipos correspondientes (alerta `success`).
- Registro con **curso distinto** → mapea `PROFESOR→EXPROFESOR`, `ALUMNO→EXALUMNO` (alerta `warning`).
- Sin registro → sin preselección (alerta `secondary`).

El formulario muestra checkboxes para los 6 tipos permitidos: **Alumno, Exalumno, Profesor, Exprofesor, Profesor externo, Familiar**.

**POST /registro**:
1. Verifica que el email no esté ya usado como `code` en `auth_user`.
2. Busca el grupo Axelor con `code = "users"`.
3. Guarda `AxelorUser` (contraseña Argon2id, idioma `es`, `centro_activo` = centro elegido).
4. Guarda `CentroUsuario` enlazando usuario ↔ centro.
5. Guarda un `CentroUsuarioTipoUsuario` por cada tipo seleccionado (busca `TipoUsuario` por `code`).

**GET /registro/exito**: pantalla de confirmación.

**GET /api/lookup/documento?documento=X&centroId=Y**: endpoint REST para consulta asíncrona del registro, devuelve `LookupResponse` (JSON).
