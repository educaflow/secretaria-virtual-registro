---
name: daw-buenas-practicas
description: Buenas prácticas en el desarrollo de aplicaciones web con Spring Boot. Usar cuando se quiera asegurar la calidad, mantenibilidad y escalabilidad de una aplicación web desarrollada con Spring Boot.
---

# daw-buenas-practicas

## Descripción

Este skill se enfoca en las buenas prácticas a seguir en el desarrollo de aplicaciones web utilizando Spring Boot.

### Cuando usar este skill

- Siempre que se esté desarrollando una aplicación web con Spring Boot, es importante seguir buenas prácticas para asegurar la calidad, mantenibilidad y escalabilidad del proyecto.


## Buenas prácticas

- Seguir el principio de responsabilidad única (SRP) para asegurar que cada clase tenga una única responsabilidad y sea fácil de mantener.
- Siempre usar interfaces para definir los diferentes componentes de la aplicación, como servicios, repositorios, etc, para facilitar la implementación y el testing.
- Usar inyección de dependencias para gestionar las dependencias entre los diferentes componentes de la aplicación y facilitar la reutilización y el testing.
- En la capa de dominio, no añadir dependencias externas, como frameworks o librerías, para mantener la independencia de la lógica de negocio.
- Usar, siempre que sea posible, anotaciones de Jakarta EE (como @Inject, @Named, etc) en lugar de anotaciones específicas de Spring (como @Autowired, @Component, etc) para mantener la portabilidad del código y evitar el acoplamiento con el framework.
- Usar inyección de dependencias basada en constructor para asegurar que las dependencias sean inmutables y facilitar el testing.
- Evitar el uso de inyección de dependencias basada en campos, ya que puede dificultar el testing y la mantenibilidad del código.


## Ejemplos

- Ejemplo de una clase de servicio siguiendo buenas prácticas:

```java
public interface UserService {
    User createUser(String name, String email);
    User getUserById(Long id);
}

```

```java
@Named
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Inject
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(String name, String email) {
        User user = new User(name, email);
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
```