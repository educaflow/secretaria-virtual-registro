---
name: daw-arquitectura
description: Arquitectura de una aplicación web con Spring Boot. Usar cuando se necesite definir la arquitectura de una aplicación web o estructurar la lógica de negocio utilizando Spring Boot.
---

# daw-arquitectura

## Descripción

Este skill se enfoca en la arquitectura de una aplicación web utilizando Spring Boot.

### Cuando usar este skill

- Cuando se necesite definir la arquitectura de una aplicación web.
- Cuando se quiera estructurar la lógica de negocio de una aplicación utilizando Spring Boot.

## Arquitectura recomendada

```
project/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/
│   │   │       ├── controller/
│   │   │       ├── domain/
│   │   │       │   ├── service/
│   │   │       │   │   └── impl/
│   │   │       │   ├── model/
│   │   │       │   ├── repository/
│   │   │       │   ├── dto/
│   │   │       │   └── exception/
│   │   │       └── persistence/
│   │   │           ├── repository/
│   │   │           │   └── impl/
│   │   │           └── dao/
│   │   │               └── impl/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/example/
├── pom.xml (or build.gradle)
└── README.md
```

---