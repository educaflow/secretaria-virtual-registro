---
name: daw-modelos
description: Creación de modelos de dominio de una aplicación web con Spring Boot.
---

# daw-modelos

## Descripción

Este skill se enfoca en la creación de modelos de dominio para una aplicación web utilizando Spring Boot. 

### Cuando usar este skill

- Cuando se necesite definir los modelos de dominio para una aplicación web.
- Cuando se quiera estructurar la lógica de negocio de una aplicación utilizando Spring Boot.

## Contexto

Los modelos de dominio son una parte fundamental en el desarrollo de aplicaciones web, ya que representan las entidades y las relaciones entre ellas. En Spring Boot, los modelos de dominio se definen como clases Java.

## Tipos de modelos

- Modelos de dominio anémicos: Son aquellos que solo contienen atributos y no tienen lógica de negocio. Son simples contenedores de datos, similares a los DTOs (Data Transfer Objects).
- Modelos de dominio ricos: Son aquellos que contienen tanto atributos como lógica de negocio. Encapsulan el comportamiento de las entidades y pueden tener métodos que operen sobre sus datos.

## Información adicional

- Si no está definido en el proyecto, preguntar al usuario si quiere modelos de dominio anémicos o ricos.
- Si el usuario no tiene claro qué tipo de modelos quiere, explicar las diferencias entre modelos anémicos y ricos, y ayudarlo a decidir cuál es el más adecuado para su proyecto.
- Si se usan modelos de dominio ricos, recomendar al usuario usar DTOs (Data Transfer Objects) para transferir datos entre capas y evitar exponer la lógica de negocio en la capa de presentación.

## Ejemplos

### Ejemplo 1: Modelo de dominio anémico

Usar la clase Record de Java para definir un modelo de dominio anémico:

```java
public record User(String name, String email) {
}
```

### Ejemplo 2: Modelo de dominio rico

Definir un modelo de dominio rico con lógica de negocio:

```java
public class User {
    private String name;
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isValidEmail() {
        // Lógica para validar el formato del correo electrónico
        return email != null && email.contains("@");
    }
}
```

## Recomendaciones

- Si se usan modelos de dominio anémicos, recomendar al usuario usar casos de uso separados de los servicios para manejar la lógica de negocio.

