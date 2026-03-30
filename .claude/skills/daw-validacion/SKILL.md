---
name: daw-validacion
description: Definición de validaciones para una aplicación web con Spring Boot.
---

# daw-validacion

## Descripción

Este skill se enfoca en la definición de validaciones para una aplicación web utilizando Spring Boot.

### Cuando usar este skill

- Cuando se necesite definir las validaciones para una aplicación web.
- Cuando se quiera estructurar la lógica de validación de una aplicación utilizando Spring Boot.

## Contexto

Las validaciones son una parte fundamental en el desarrollo de aplicaciones web, ya que permiten garantizar la integridad de los datos y la correcta ejecución de las operaciones. En Spring Boot, las validaciones se pueden definir utilizando anotaciones de validación de Jakarta, como `@NotNull`, `@Size`, `@Email`, etc.

## Información adicional

- Las validaciones se pueden aplicar a los modelos de dominio, a los DTOs (Data Transfer Objects) o a los parámetros de los controladores.
- Las validaciones de la lógica de negocio deben estar en los servicios o casos de uso, no en los controladores, para mantener una separación de responsabilidades adecuada.
- Las validaciones que afectan sólo a un atributo específico del modelo de dominio o DTO deben estar en el modelo de dominio o DTO, utilizando anotaciones de validación de Jakarta.
- Las validaciones que afectan a múltiples atributos o a la lógica de negocio en general deben estar en los servicios o casos de uso, utilizando lógica de validación personalizada.
- Se pueden crear excepciones personalizadas para manejar los errores de validación y devolver respuestas adecuadas al cliente, como códigos de estado HTTP 400 (Bad Request) con mensajes de error descriptivos.

## Ejemplos

Validaciones para un modelo de dominio anémico "User":

```java
public record UserDto(
    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    String name,
    @NotNull(message = "El email no puede ser nulo")
    @Email(message = "El email debe ser válido")
    String email) {
}
```