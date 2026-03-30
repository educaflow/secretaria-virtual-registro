---
name: daw-controladores
description: Definición de controladores para una aplicación web con Spring Boot.
---

# daw-controladores

## Descripción

Este skill se enfoca en la definición de controladores para una aplicación web utilizando Spring Boot.

### Cuando usar este skill

- Cuando se necesite definir los controladores para una aplicación web.
- Cuando se quiera estructurar la lógica de presentación de una aplicación utilizando Spring Boot.
- Cuando se quiera implementar la capa de presentación de una aplicación web con Spring Boot.

## Contexto

Los controladores son una parte fundamental en el desarrollo de aplicaciones web, ya que representan la lógica de presentación y las operaciones que se pueden realizar sobre los modelos de dominio. En Spring Boot, los controladores se definen como clases Java anotadas con `@Controller` o `@RestController`.

## Nomenclatura

- Los controladores deben llamarse con el sufijo "Controller", por ejemplo, "UserController" para un controlador relacionado con el modelo de dominio "User".
- Los controladores deben estar en el paquete "controller" de presentación, por ejemplo, "com.example.presentation.controller".

## Información adicional

- Los controladores trabajan con los servicios o casos de uso para manejar las solicitudes HTTP y devolver las respuestas adecuadas.
- Los controladores no deben contener lógica de negocio, solo lógica de presentación. La lógica de negocio debe estar en los servicios o casos de uso.
- Si se usan modelos de dominio anémicos, los controladores pueden trabajar directamente con ellos (aceptar y devolver modelos de dominio anémicos). 
- Si se usan modelos de dominio ricos, los controladores pueden trabajar con ellos o con DTOs (Data Transfer Objects) para transferir datos entre capas, aunque es recomendable trabajar con DTOs para evitar exponer la lógica de negocio al cliente.
- La información que se devuelve (o se recibe) al cliente puede ser diferente del modelo de dominio, por lo que es recomendable usar DTOs (Data Transfer Objects) para transferir datos entre la capa de presentación y la capa de negocio, y evitar exponer datos innecesarios al cliente.
- Si se está implementando una API REST, devolver objetos JSON usando la clase `ResponseEntity` para tener un mayor control sobre el código de estado HTTP y los encabezados de la respuesta.
- Si se está implementando una aplicación web con vistas, usar Thymleaf o cualquier otro motor de plantillas para renderizar las vistas y devolver el nombre de la plantilla desde el controlador.
- Usar anotaciones de Spring MVC como `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`, etc, para mapear las solicitudes HTTP a los métodos del controlador de manera clara y concisa.
- Usar un gestor de excepciones global para manejar las excepciones de manera centralizada y devolver respuestas adecuadas al cliente en caso de errores.

## Ejemplos

Controladores de una web con vistas para un modelo de dominio "User":

```java
@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user-detail";
    }
}
```

Controladores de una API REST para un modelo de dominio "User":

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService; 

    public UserController(UserService userService) {
        this.userService = userService;
    } 

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
}
```

Gestor de excepciones global para manejar errores en los controladores:

```java
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
    }    
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
    }

}
