# pedidos-service

Microservicio Spring Boot encargado del CRUD de pedidos. Se protege con JWT usando la misma llave secreta del `usuarios-service`.

## Endpoints principales

Todos requieren header:

```text
Authorization: Bearer TOKEN_GENERADO_EN_USUARIOS_SERVICE
```

- `POST /api/pedidos` crea pedido.
- `GET /api/pedidos` lista pedidos.
- `GET /api/pedidos/{id}` busca pedido.
- `GET /api/pedidos?usuarioId=1` lista pedidos por usuario.
- `PUT /api/pedidos/{id}` actualiza pedido.
- `DELETE /api/pedidos/{id}` elimina pedido.

## Variables de entorno

```bash
JWT_SECRET=clave-super-secreta-de-prueba-para-firmar-jwt-2026
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/pedidos_db
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
```

## Maven

Ejecutar test:

```bash
mvn test
```

Compilar y generar JAR:

```bash
mvn clean package
```

## Docker

Generar imagen Docker:

```bash
docker build -t pedidos-service:1.0 .
```

Ejecutar imagen:

```bash
docker run -p 8082:8082 pedidos-service:1.0
```
