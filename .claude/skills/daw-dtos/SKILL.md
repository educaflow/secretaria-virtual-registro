---
name: daw-dtos
description: Definición de DTOs (Data Transfer Objects) para una aplicación web con Spring Boot.
---

# daw-dtos

## Descripción

Este skill se enfoca en la definición de DTOs (Data Transfer Objects) para una aplicación web utilizando Spring Boot.

### Cuando usar este skill

- Cuando se necesite definir los DTOs para una aplicación web.
- Cuando se quiera estructurar la lógica de transferencia de datos entre capas de una aplicación utilizando Spring Boot.


## Contexto

Los DTOs (Data Transfer Objects) son una parte fundamental en el desarrollo de aplicaciones web, ya que representan la lógica de transferencia de datos entre capas. Son simples contenedores de datos que se utilizan para transferir información entre la capa de presentación y la capa de negocio, o entre la capa de negocio y la capa de persistencia.

## Nomenclatura

- Los DTOs deben llamarse con el sufijo "Dto", por ejemplo, "UserDto" para un DTO relacionado con el modelo de dominio "User".
- Si el DTO es para transportar datos entre dominio y alguna de las otras capas, debe estar en el paquete "dto" de la capa de dominio, por ejemplo, "com.example.domain.dto".
- Si el DTO es para devolver datos al cliente, debe estar en el paquete "response" de la capa de presentación, por ejemplo, "com.example.presentation.dto".
- Si el DTO es para recibir datos del cliente, debe estar en el paquete "request" de la capa de presentación, por ejemplo, "com.example.presentation.dto".
- Siempre que se pueda, usar la clase Record de Java para definir los DTOs, ya que son inmutables y proporcionan una sintaxis más concisa.
- Si se usan modelos de dominio anémicos, el nombre de los DTOs debe ser el mismo que el de los modelos de dominio anémicos, pero con el sufijo "Dto", por ejemplo, "UserDto" para un DTO relacionado con el modelo de dominio anémico "User". Esto facilita la identificación de los DTOs y su relación con los modelos de dominio anémicos.

## Información adicional

- Los DTOs no deben contener lógica de negocio, solo atributos y métodos de acceso (getters).
- Los DTOs pueden contener validaciones de datos utilizando anotaciones de validación de Jakarta, como @NotNull, @Size, etc.
- Los DTOs pueden ser usados para transferir datos entre capas, pero no deben ser usados para representar modelos de dominio, ya que los modelos de dominio deben contener la lógica de negocio y los DTOs no, a no ser que se usen modelos de dominio anémicos, en cuyo caso los DTOs pueden ser usados para representar los modelos de dominio anémicos.
- Podemos tener diferentes DTOs, por ejemplo, un DTO para recibir datos del cliente (request), otro DTO para devolver datos al cliente (response), y otro DTO para transferir datos entre la capa de negocio y la capa de persistencia (domain). Esto permite una mayor flexibilidad y separación de responsabilidades entre las capas de la aplicación.
- Los DTOs para la transferencia de datos entre dominio y presentación pueden ser los mismo que entre dominio y persistencia, aunque es recomendable tener DTOs específicos para la capa de presentación, ya que pueden contener solo los datos necesarios para la presentación y evitar exponer datos innecesarios al cliente.
- Si se usan DTOs, usar mapeadores para convertir entre los modelos de dominio y los DTOs, como MapStruct o ModelMapper, para evitar escribir código de mapeo manual y reducir el riesgo de errores.

## Ejemplos

DTOs de respuesta de la capa de presentación para un modelo de dominio "User":

```java
public record UserResponseDto(String name, String email) {
}
``` 

DTOs de solicitud de la capa de presentación para un modelo de dominio "User":

```javapublic record UserRequestDto(String name, String email) {
}
```

DTOs de dominio para un modelo de dominio anémico "User":

```java
public record UserDto(String name, String email) {
}
```

DTOs de dominio para el repositorio de un modelo de dominio rico "User":

```java
public record UserDto(String name, String email) {
}
```