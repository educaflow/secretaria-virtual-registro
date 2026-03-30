---
name: daw-daos
description: Definición de DAOs (Data Access Objects) para una aplicación web con Spring Boot.
---

# daw-daos

## Descripción

Este skill se enfoca en la definición de DAOs (Data Access Objects) para una aplicación web utilizando Spring Boot.

### Cuando usar este skill

- Cuando se necesite definir los DAOs para una aplicación web.
- Cuando se quiera estructurar la lógica de acceso a datos de una aplicación utilizando Spring Boot.
- Cuando se quiera implementar la capa de persistencia de una aplicación web con Spring Boot.

## Contexto

Los DAOs (Data Access Objects) son una parte fundamental en el desarrollo de aplicaciones web, ya que representan la lógica de negocio para acceder a los datos. Son independientes de la tecnología de persistencia utilizada, lo que permite cambiar la implementación sin afectar el resto de la aplicación.

## Nomenclatura

- Los DAOs deben llamarse con el sufijo "Dao", por ejemplo, "UserDao" para un DAO relacionado con el modelo de dominio "User".
- Las implementaciones de los DAOs deben llamarse con el sufijo "DaoImpl", por ejemplo, "UserDaoImpl" para la implementación del DAO "UserDao".
- Las interfaces de los DAOs deben estar en el paquete "dao" de persistencia, por ejemplo, "com.example.persistence.dao".
- Las implementaciones de los DAOs deben estar en el paquete "dao.impl" de persistencia, por ejemplo, "com.example.persistence.dao.impl".
- Siempre usar interfaces para definir los DAOs, incluso si solo hay una implementación.

## Información adicional

- Los DAOs trabajan con los modelos de persistencia, por ejemplo, entidades JPA, documentos MongoDB, etc.
- Las implementaciones de los DAOs pueden usar tecnologías de persistencia específicas, como JPA, JDBC, etc, para acceder a los datos.
- Los DAOs no deben contener lógica de negocio, solo lógica de acceso a datos. La lógica de negocio debe estar en los servicios.
- Si se utiliza JPA, es recomendable usar el EntityManager para implementar los DAOs, aunque también se pueden usar otras tecnologías de persistencia.


## Ejemplos

DAOs para un modelo de dominio anémico "User":

```java
public interface UserDao {
    User save(User user);
    Optional<User> findById(Long id);
}
```

```java
@Named
public class UserDaoImpl implements UserDao {
    private final EntityManager entityManager;

    @Inject
    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    } 

    @Override
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }
 }
```