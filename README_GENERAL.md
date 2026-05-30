# Prueba microservicios Spring Boot + JWT + PostgreSQL + Docker

Proyecto basado en el diagrama de arquitectura de microservicios de la prueba 1.

## Microservicios implementados

Se implementan 2 microservicios backend:

1. `usuarios-service`: usuarios, registro y login JWT.
2. `pedidos-service`: CRUD de pedidos protegido con JWT.

Esto cubre el 80% o más de los microservicios principales del diagrama, considerando que API Gateway no es obligatorio.

## Seguridad JWT con llave secreta compartida

El `usuarios-service` genera el token JWT durante el login. El `pedidos-service` valida ese token usando la misma llave secreta:

```text
JWT_SECRET=clave-super-secreta-de-prueba-para-firmar-jwt-2026
```

Por eso, el token generado en usuarios permite acceder a pedidos.

## Levantar con Docker Compose

Desde la carpeta `infra-docker`:

```bash
docker compose up --build
```

Servicios disponibles:

- usuarios-service: `http://localhost:8081`
- pedidos-service: `http://localhost:8082`
- PostgreSQL: `localhost:5432`

## Prueba rápida en Postman

### 1. Login

POST `http://localhost:8081/api/auth/login`

```json
{
  "correo": "admin@demo.cl",
  "password": "123456"
}
```

Copiar el valor de `token`.

### 2. Crear usuario con JWT

POST `http://localhost:8081/api/usuarios`

Header:

```text
Authorization: Bearer TOKEN
```

Body:

```json
{
  "nombre": "Consuelo Jerez",
  "correo": "consuelo@demo.cl",
  "password": "123456",
  "rol": "USER",
  "activo": true
}
```

### 3. Listar usuarios con JWT

GET `http://localhost:8081/api/usuarios`

Header:

```text
Authorization: Bearer TOKEN
```

### 4. Crear pedido con JWT

POST `http://localhost:8082/api/pedidos`

Header:

```text
Authorization: Bearer TOKEN
```

Body:

```json
{
  "descripcion": "Pedido de prueba",
  "estado": "PENDIENTE",
  "total": 25990,
  "usuarioId": 1
}
```

### 5. Listar pedidos con JWT

GET `http://localhost:8082/api/pedidos`

Header:

```text
Authorization: Bearer TOKEN
```

## Comandos Maven

En cada microservicio:

```bash
mvn test
mvn clean package
```

## Generar imágenes Docker manualmente

En `usuarios-service`:

```bash
docker build -t usuarios-service:1.0 .
```

En `pedidos-service`:

```bash
docker build -t pedidos-service:1.0 .
```

## Repositorios Git recomendados

Como el requisito indica 1 repositorio por microservicio, se recomienda subir cada carpeta a un repositorio separado:

- `usuarios-service` → repositorio `usuarios-service`
- `pedidos-service` → repositorio `pedidos-service`

La carpeta `infra-docker` puede quedar en un tercer repositorio llamado `infra-docker` o se puede adjuntar como apoyo para ejecutar todo localmente.

## Commits sugeridos en diferentes días

Ejemplo de historial para cada repositorio:

1. `feat: crear estructura inicial del microservicio`
2. `feat: agregar modelo repository service y controller`
3. `feat: integrar JWT y seguridad`
4. `feat: configurar persistencia PostgreSQL`
5. `docs: agregar instrucciones Maven Docker y Postman`
