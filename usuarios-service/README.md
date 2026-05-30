# usuarios-service

Microservicio Spring Boot encargado de usuarios, registro y login JWT.

## Endpoints principales

- `POST /api/auth/register` crea usuario.
- `POST /api/auth/login` valida correo/password y devuelve token JWT.
- `POST /api/usuarios` crea usuario. Requiere JWT.
- `GET /api/usuarios` lista usuarios. Requiere JWT.
- `GET /api/usuarios/{id}` busca usuario. Requiere JWT.
- `PUT /api/usuarios/{id}` actualiza usuario. Requiere JWT.
- `DELETE /api/usuarios/{id}` elimina usuario. Requiere JWT.

## Usuario inicial

Al iniciar el servicio se crea un usuario de prueba si no existe:

```json
{
  "correo": "admin@demo.cl",
  "password": "123456"
}
```

## Variables de entorno

```bash
JWT_SECRET=clave-super-secreta-de-prueba-para-firmar-jwt-2026
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/usuarios_db
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
docker build -t usuarios-service:1.0 .
```

Ejecutar imagen:

```bash
docker run -p 8081:8081 usuarios-service:1.0
```
