---
name: daw-casos-uso
description: Definición de casos de uso para una aplicación web con Spring Boot.
---

# daw-casos-uso

## Descripción

Este skill se enfoca en la definición de casos de uso para una aplicación web utilizando Spring Boot.

### Cuando usar este skill

- Cuando se necesite definir los casos de uso para una aplicación web.
- Cuando se quiera estructurar la lógica de negocio de una aplicación utilizando Spring Boot.

## Contexto

Los casos de uso son una parte fundamental en el desarrollo de aplicaciones web, ya que representan la lógica de negocio y las operaciones que se pueden realizar sobre los modelos de dominio. En Spring Boot, los casos de uso se pueden definir como clases Java que contienen la lógica de negocio y trabajan con los servicios para realizar las operaciones necesarias.

## Nomenclatura

- Los casos de uso deben llamarse con el sufijo "UseCase", por ejemplo, "CreateUserUseCase" para un caso de uso relacionado con la creación de un usuario.
- Las implementaciones de los casos de uso deben llamarse con el sufijo "UseCaseImpl", por ejemplo, "CreateUserUseCaseImpl" para la implementación del caso de uso "CreateUserUseCase".
- Los casos de uso deben estar en el paquete "usecase" de dominio, por ejemplo, "com.example.domain.usecase".
- Las implementaciones de los casos de uso deben estar en el paquete "usecase.impl" de la capa de negocio, por ejemplo, "com.example.business.usecase.impl".

## Información adicional

- Siempre usar interfaces para definir los casos de uso, incluso si solo hay una implementación.
- Los casos de uso son de granularidad más alta que los servicios, ya que pueden contener varios servicios para realizar una operación completa.
- Los casos de uso trabajan con los servicios para realizar las operaciones necesarias sobre los modelos de dominio
- Si se usan modelos de dominio anémicos, es recomendable separar casos de uso de servicios, ya que los casos de uso pueden contener toda la lógica de negocio y trabajar directamente con los modelos de dominio anémicos (aceptar y devolver modelos de dominio anémicos).
- Si se usan modelos de dominio ricos, no hace falta separar casos de uso de servicios, ya que los servicios pueden contener toda la lógica de negocio y trabajar directamente con los modelos de dominio ricos (aceptar y devolver modelos de dominio ricos), aunque también se pueden usar casos de uso para organizar la lógica de negocio en operaciones más complejas.

## Ejemplos

Casos de uso para un modelo de dominio anémico "User":

```java
public interface CreateUserUseCase {
    User createUser(String name, String email);
}
```

```java
@Named
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserService userService;

    @Inject
    public CreateUserUseCaseImpl(UserService userService) {
        this.userService = userService;
    }
    
    @Override
    public User createUser(String name, String email) {
        return userService.createUser(name, email);
    }
}
```