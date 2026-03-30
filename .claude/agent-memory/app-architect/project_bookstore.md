---
name: Project Bookstore Architecture
description: Decisiones arquitectónicas para tienda online de libros — Spring Boot + React, arquitectura en capas
type: project
---

Proyecto: Tienda online de libros (prueba-claude, groupId: es.cesguiro)

Arquitectura elegida: Monolito modular en capas (no microservicios) — apropiado para MVP simple.

Stack decidido:
- Backend: Spring Boot 3.x (Java 21), Spring Security, Spring Data JPA
- Frontend: React 18 + Vite + TailwindCSS
- Base de datos: PostgreSQL (producción), H2 (tests)
- Cache: Redis (carrito de compras y sesiones)
- Build: Maven (multi-module)

Bounded contexts identificados:
1. Catálogo (libros, autores, categorías)
2. Carrito (sesión/usuario, items)
3. Pedidos (checkout, order management)
4. Usuarios (auth, perfil)

Patrones adoptados:
- Arquitectura en capas: Controller → Service → Repository
- DTOs para transferencia entre capas
- Repository pattern con Spring Data JPA
- JWT para autenticación stateless

**Why:** El usuario busca una solución sencilla y funcional, no sobrediseñada. YAGNI aplicado: monolito modular antes que microservicios.
**How to apply:** Mantener esta estructura de capas en futuras sugerencias de código. Proponer microservicios solo si la escala lo justifica.
