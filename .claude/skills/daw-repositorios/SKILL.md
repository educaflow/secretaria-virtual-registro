---
name: daw-repositorios
description: Creación de repositorios para una aplicación web con Spring Boot.
---

# daw-repositorios

## Descripción

Este skill se enfoca en la creación de repositorios para una aplicación web utilizando Spring Boot.

### Cuando usar este skill

- Cuando se necesite definir los repositorios para una aplicación web.
- Cuando se quiera estructurar la lógica de acceso a datos de una aplicación utilizando Spring Boot.
- Cuando se quiera implementar la capa de persistencia de una aplicación web con Spring Boot.

## Contexto

Los repositorios son una parte fundamental en el desarrollo de aplicaciones web, ya que representan la lógica de negocio para persistir los modelos de dominio. Son independientes de la tecnología de persistencia utilizada, lo que permite cambiar la implementación sin afectar el resto de la aplicación.

## Nomenclatura

- Los repositorios deben llamarse con el sufijo "Repository", por ejemplo, "UserRepository" para un repositorio relacionado con el modelo de dominio "User".
- Las implementaciones de los repositorios deben llamarse con el sufijo "RepositoryImpl", por ejemplo, "UserRepositoryImpl" para la implementación del repositorio "UserRepository".
- Las interfaces de los repositorios deben estar en el paquete "repository" de dominio, por ejemplo, "com.example.domain.repository".
- Las implementaciones de los repositorios deben estar en el paquete "repository.impl" de la capa de persistencia, por ejemplo, "com.example.persistence.repository.impl".
- Siempre usar interfaces para definir los repositorios, incluso si solo hay una implementación.
- Siempre crear las implentaciones de los repositorios en la capa de persistencia, según el principio de "Inversión de dependencias".

## Información adicional

- Los repositorios trabajan con los modelos de dominio.
- Si los modelos son anémicos, los repositorios trabajan directamente con ellos (aceptar y devolver modelos de dominio anémicos).
- Si los modelos son ricos, los repositorios pueden trabajar con ellos o con DTOs (Data Transfer Objects) para transferir datos entre capas, aunque es recomendable trabajar con DTOs.
- Los repositorios no usan tecnologías de persistencia específicas, como JPA, JDBC, etc, para mantener la independencia de la lógica de acceso a datos.

## Ejemplos

Repositorios para un modelo de dominio anémico "User":

```java
public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
}
```

```java
@Named
public class UserRepositoryImpl implements UserRepository {
    private final UserDao userDao;

    @Inject
    public UserRepositoryImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User save(User user) {
        return userDao.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }
}
```

