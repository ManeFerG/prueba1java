# Microservicio de Usuarios

## Descripción
Este microservicio forma parte de una arquitectura de microservicios y se encarga de la gestión de usuarios dentro del sistema.

Permite realizar operaciones básicas como la consulta de usuarios.

---

## Tecnologías utilizadas
Java
Spring Boot
REST API

---

## Endpoints disponibles

### Obtener usuarios
GET /usuarios

Respuesta:
"Lista de usuarios"

---

## Configuración
Puerto: 8081
Comunicación mediante HTTP/REST

---

## Rol dentro de la arquitectura
Este servicio cumple el principio de responsabilidad única, ya que solo gestiona la información de usuarios.

Se comunica con otros servicios a través del API Gateway.

---

## Arquitectura
Este microservicio forma parte de un sistema desplegado en la nube utilizando una arquitectura basada en microservicios, incluyendo:

API Gateway
Balanceador de carga
Registro de servicios (Eureka)
Seguridad con JWT

---

## Autores
Valentina Gomez
Consuelo Jerez