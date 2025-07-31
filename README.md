# Proyecto Spring Boot

## Descripción

Este proyecto es una aplicación desarrollada en Java utilizando el framework Spring Boot y gestionada con Maven. Permite la gestión de entidades y operaciones CRUD (Crear, Leer, Actualizar, Eliminar) a través de una API REST.

## Herramientas utilizadas

- **Java 17+**: Lenguaje de programación principal.
- **Spring Boot**: Framework para desarrollo rápido de aplicaciones web.
- **Maven**: Herramienta de gestión de dependencias y construcción.
- **Spring Data JPA**: Acceso y persistencia de datos.
- **H2 Database**: Base de datos en memoria para pruebas.
- **JUnit**: Pruebas unitarias.

## Funcionalidades implementadas

- **API REST** para gestión de entidades (ejemplo: usuarios, productos).
- **Operaciones CRUD**:  
  - Crear entidad  
  - Consultar entidad  
  - Actualizar entidad  
  - Eliminar entidad
- **Validación de datos** en los endpoints.
- **Persistencia** de datos usando JPA y H2.
- **Pruebas unitarias** para los servicios principales.

## Cómo ejecutar

1. Clona el repositorio.
2. Ejecuta `mvn spring-boot:run`.
3. Accede a la API en `http://localhost:8080`.

## Estructura del proyecto

- `src/main/java`: Código fuente principal.
- `src/test/java`: Pruebas unitarias.
- `pom.xml`: Configuración de Maven y dependencias.
