# Evaluación Parcial N°2 - Microservicios Spring Boot

Proyecto desarrollado para la asignatura **Java: Diseño y Construcción de Soluciones Nativas en Nube (JVY0101)**.

El sistema implementa microservicios en Java con Spring Boot, utilizando arquitectura por capas, persistencia con PostgreSQL, autenticación JWT, Docker Compose y pruebas mediante Postman.

---

## Integrantes

* Consuelo Jerez
* Valentina Gomez

---

## Repositorio

Link del repositorio GitHub:

https://github.com/ManeFerG/prueba1java

---

## Tecnologías utilizadas

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Security
* JWT
* PostgreSQL
* Docker
* Docker Compose
* Maven
* Postman
* Docker Swarm
* GitHub Actions
* AWS Lambda
* Amazon SQS
* AWS SAM
* Amazon EC2

---

## Estructura del proyecto

```bash
prueba1java/
├── usuarios-service/
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
│
├── pedidos-service/
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
│
└── infra-docker/
    ├── docker-compose.yml
    └── init.sql
```

---

## Microservicios implementados

### 1. usuarios-service

Microservicio encargado de la gestión de usuarios y autenticación.

Puerto local:

```bash
http://localhost:8081
```

Funcionalidades principales:

* Registro de usuarios.
* Login de usuarios.
* Generación de token JWT.
* CRUD de usuarios.
* Validaciones básicas.
* Persistencia en base de datos PostgreSQL.

---

### 2. pedidos-service

Microservicio encargado de la gestión de pedidos.

Puerto local:

```bash
http://localhost:8082
```

Funcionalidades principales:

* Crear pedidos.
* Listar pedidos.
* Buscar pedidos por ID.
* Buscar pedidos por usuario.
* Actualizar pedidos.
* Eliminar pedidos.
* Validación de usuario asociado.
* Persistencia en base de datos PostgreSQL.
* Protección de endpoints mediante JWT.

---

## Arquitectura interna

Cada microservicio utiliza una arquitectura por capas:

```bash
controller  -> Expone endpoints REST
service     -> Contiene la lógica de negocio
repository  -> Acceso a datos mediante Spring Data JPA
model       -> Entidades JPA/Hibernate
dto         -> Objetos para solicitudes y respuestas
security    -> Configuración de seguridad y JWT
```

Esta estructura permite separar responsabilidades, facilitar el mantenimiento del código y seguir buenas prácticas de desarrollo con Spring Boot.

---

## Base de datos

El proyecto utiliza PostgreSQL ejecutado mediante Docker.

Bases de datos utilizadas:

```bash
usuarios_db
pedidos_db
```

Tablas principales:

```bash
usuarios
pedidos
```

El archivo `init.sql` se encarga de crear las bases de datos necesarias al levantar el contenedor de PostgreSQL.

---

## Requisitos previos

Antes de ejecutar el proyecto, se debe tener instalado:

* Docker Desktop
* Git
* Postman
* Java 17, si se desea ejecutar localmente sin Docker
* Maven, si se desea compilar manualmente

---

## Ejecución con Docker Compose

Ingresar a la carpeta `infra-docker`:

```bash
cd infra-docker
```

Levantar todos los servicios:

```bash
docker compose up --build -d
```

Verificar que los contenedores estén corriendo:

```bash
docker ps
```

Deben aparecer los siguientes contenedores:

```bash
prueba-postgres
usuarios-service
pedidos-service
```

---

## Verificación de PostgreSQL

Entrar al contenedor de PostgreSQL:

```bash
docker exec -it prueba-postgres psql -U postgres
```

Listar bases de datos:

```sql
\l
```

Conectarse a la base de usuarios:

```sql
\c usuarios_db
```

Ver tablas:

```sql
\dt
```

Conectarse a la base de pedidos:

```sql
\c pedidos_db
```

Ver tablas:

```sql
\dt
```

Salir de PostgreSQL:

```sql
\q
```

---

## Compilación con Maven

Para compilar un microservicio manualmente, ingresar a su carpeta:

```bash
cd usuarios-service
```

Ejecutar:

```bash
mvn clean package
```

Para el microservicio de pedidos:

```bash
cd pedidos-service
```

Ejecutar:

```bash
mvn clean package
```

Esto genera el archivo `.jar` dentro de la carpeta `target`.

---

## Endpoints principales

### usuarios-service

URL base:

```bash
http://localhost:8081
```

### Registrar usuario

```http
POST /api/auth/register
```

Body:

```json
{
  "nombre": "Consuelo Jerez",
  "correo": "consu@test.cl",
  "password": "123456",
  "rol": "ADMIN"
}
```

---

### Login

```http
POST /api/auth/login
```

Body:

```json
{
  "correo": "admin@demo.cl",
  "password": "123456"
}
```

Respuesta esperada:

```json
{
  "token": "TOKEN_JWT",
  "tipo": "Bearer"
}
```

El token recibido debe utilizarse en Postman en:

```bash
Authorization -> Bearer Token
```

---

### Listar usuarios

```http
GET /api/usuarios
```

---

### Buscar usuario por ID

```http
GET /api/usuarios/{id}
```

Ejemplo:

```http
GET /api/usuarios/1
```

---

### Crear usuario

```http
POST /api/usuarios
```

Body:

```json
{
  "nombre": "Pedro Perez",
  "correo": "pedro@correo.cl",
  "password": "123456",
  "rol": "USER",
  "activo": true
}
```

---

### Actualizar usuario

```http
PUT /api/usuarios/{id}
```

Body:

```json
{
  "nombre": "Pedro Perez Actualizado",
  "correo": "pedro@correo.cl",
  "password": "123456",
  "rol": "USER",
  "activo": true
}
```

---

### Eliminar usuario

```http
DELETE /api/usuarios/{id}
```

---

## Endpoints de pedidos-service

URL base:

```bash
http://localhost:8082
```

Para los endpoints protegidos se debe enviar el token JWT obtenido desde el login del microservicio de usuarios.

En Postman:

```bash
Authorization -> Type -> Bearer Token
```

---

### Listar pedidos

```http
GET /api/pedidos
```

---

### Buscar pedido por ID

```http
GET /api/pedidos/{id}
```

Ejemplo:

```http
GET /api/pedidos/1
```

---

### Crear pedido

```http
POST /api/pedidos
```

Body:

```json
{
  "descripcion": "notebook",
  "estado": "PENDIENTE",
  "total": 250500,
  "usuarioId": 2
}
```

---

### Buscar pedidos por usuario

```http
GET /api/pedidos/usuario/{usuarioId}
```

Ejemplo:

```http
GET /api/pedidos/usuario/2
```

---

### Actualizar pedido

```http
PUT /api/pedidos/{id}
```

Body:

```json
{
  "descripcion": "notebook actualizado",
  "estado": "PAGADO",
  "total": 300000,
  "usuarioId": 2
}
```

---

### Eliminar pedido

```http
DELETE /api/pedidos/{id}
```

---

## Pruebas en Postman

Orden recomendado para probar el sistema:

1. Levantar los servicios con Docker Compose.
2. Verificar los contenedores con `docker ps`.
3. Registrar un usuario o utilizar el usuario demo.
4. Realizar login en `usuarios-service`.
5. Copiar el token JWT retornado.
6. Configurar el token en Postman como Bearer Token.
7. Probar CRUD de usuarios.
8. Probar CRUD de pedidos.
9. Verificar los registros en PostgreSQL.
10. Realizar una prueba de error, por ejemplo enviar un pedido sin token o con datos inválidos.

---

## Usuario demo

El sistema cuenta con un usuario demo para realizar pruebas:

```json
{
  "correo": "admin@demo.cl",
  "password": "123456"
}
```

---

## Comandos útiles de Docker

Levantar todos los servicios:

```bash
docker compose up --build -d
```

Ver contenedores activos:

```bash
docker ps
```

Ver logs de usuarios-service:

```bash
docker logs usuarios-service
```

Ver logs de pedidos-service:

```bash
docker logs pedidos-service
```

Detener servicios:

```bash
docker compose down
```

Detener servicios y eliminar volúmenes:

```bash
docker compose down -v
```

---

## Evidencias de funcionamiento

Durante las pruebas se evidencia:

* Contenedores activos mediante Docker.
* Conexión correcta a PostgreSQL.
* Creación de tablas mediante JPA/Hibernate.
* Registro y login de usuarios.
* Generación y uso de token JWT.
* CRUD completo de usuarios.
* CRUD completo de pedidos.
* Persistencia de datos en PostgreSQL.
* Manejo de errores en solicitudes incorrectas.

---

## Control de versiones

El proyecto fue versionado con Git y subido a GitHub.

Se trabajó utilizando commits descriptivos para evidenciar el avance del desarrollo, por ejemplo:

```bash
feat: implementar usuarios-service con autenticacion JWT
feat: crear pedidos-service con CRUD
feat: configurar PostgreSQL con Docker Compose
fix: corregir creacion de pedidos con usuario asociado
docs: actualizar README con instrucciones de ejecucion
```

---

## Consideraciones finales

Este proyecto demuestra la implementación de microservicios funcionales con Spring Boot, persistencia en PostgreSQL, autenticación mediante JWT, ejecución con Docker Compose y pruebas mediante Postman.

Los microservicios fueron construidos siguiendo buenas prácticas de arquitectura por capas y utilizando Maven para la gestión del ciclo de vida del proyecto.
