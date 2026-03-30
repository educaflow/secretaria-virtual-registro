---
name: daw-servicios
description: Creación de servicios para una aplicación web con Spring Boot.
---

# daw-servicios

## Descripción

Este skill se enfoca en la creación de servicios para una aplicación web utilizando Spring Boot.

### Cuando usar este skill

- Cuando se necesite definir los servicios para una aplicación web.
- Cuando se quiera estructurar la lógica de negocio de una aplicación utilizando Spring Boot.

## Contexto

Los servicios son una parte fundamental en el desarrollo de aplicaciones web, ya que representan la lógica de negocio y las operaciones que se pueden realizar sobre los modelos de dominio.

## Nomenclatura

- Los servicios deben llamarse con el sufijo "Service", por ejemplo, "UserService" para un servicio relacionado con el modelo de dominio "User".
- Las implementaciones de los servicios deben llamarse con el sufijo "ServiceImpl", por ejemplo, "UserServiceImpl" para la implementación del servicio "UserService".

## Información adicional

- Siempre usar interfaces para definir los servicios, incluso si solo hay una implementación.
- Si se usan modelos de dominio anémicos, los servicios deben contener toda la lógica de negocio y trabajar directamente con ellos (aceptar y devolver modelos de dominio anémicos). 
- Si se usan modelos de dominio ricos, los servicios pueden trabajar con ellos o con DTOs (Data Transfer Objects) para transferir datos entre capas.
- Los servicios trabajan con los modelos de dominio si son ricos, o con los DTOs (Data Transfer Objects) si se usan modelos de dominio anémicos.
- Los servicios pueden contener toda la lógica de negocio o usarlos con casos de uso, con lo que se pueden reutilizar en diferentes casos de uso.
- Los servicios deberían estar agrupados por modelo de dominio, es decir, cada servicio debería estar relacionado con un modelo de dominio específico.

## Ejemplos

Servicios para un modelo de dominio anémico "User":

```java
public interface UserService {
    User createUser(String name, String email);
    User getUserById(Long id);
    List<User> getAllUsers();
    void deleteUser(Long id);
}

```

```java
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository; 

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

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
```

Servicios para un modelo de dominio rico "User":

```java
public interface UserService {
    UserDto createUser(String name, String email);
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
    void deleteUser(Long id);
}
```

```java
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(String name, String email) {
        User user = new User(name, email);
        userRepository.save(user);
        return new UserDto(user.getName(), user.getEmail());
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return new UserDto(user.getName(), user.getEmail());
        }
        return null;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserDto(user.getName(), user.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
```