# Evaluación Parcial N°3 – Despliegue Cloud sobre el proyecto de la EP2

Esta versión conserva los microservicios de la **Evaluación Parcial N°2** y agrega los componentes mínimos solicitados para la EP3:

- `usuarios-service` desplegable en una instancia AWS EC2.
- PostgreSQL en Docker dentro de la misma EC2.
- Imagen Docker construida y publicada automáticamente.
- Pipeline CI/CD con GitHub Actions.
- API Gateway.
- Lambda Java productora.
- Cola Amazon SQS.
- Lambda Java consumidora.
- Evidencia del flujo asíncrono mediante CloudWatch Logs.

## Arquitectura usada

```text
Postman
   |
   | POST /api/auth/login
   v
AWS EC2 :8081
   |- usuarios-service (Docker)
   `- PostgreSQL (Docker)

Postman
   |
   | POST /mensajes
   v
API Gateway -> Lambda productora -> SQS -> Lambda consumidora -> CloudWatch Logs

GitHub push -> GitHub Actions -> Docker Hub -> actualización automática de EC2
```

## 1. Prueba local del proyecto original

```bash
cd infra-docker
docker compose up --build -d
docker compose ps
```

Health check:

```bash
curl http://localhost:8081/api/health
```

Login de demostración:

```http
POST http://localhost:8081/api/auth/login
Content-Type: application/json

{
  "correo": "admin@demo.cl",
  "password": "123456"
}
```

## 2. Preparación de EC2

Usar una instancia Ubuntu y abrir en el Security Group:

- Puerto 22: solamente desde la IP del estudiante.
- Puerto 8081: para la demostración del microservicio.

Copiar el script y ejecutarlo:

```bash
chmod +x scripts/instalar-docker-ubuntu.sh
./scripts/instalar-docker-ubuntu.sh
```

Después cerrar y volver a abrir la sesión SSH.

## 3. Secretos de GitHub Actions

En GitHub: `Settings -> Secrets and variables -> Actions -> New repository secret`.

Crear:

| Secreto | Ejemplo / propósito |
|---|---|
| `DOCKERHUB_USERNAME` | Nombre de usuario de Docker Hub |
| `DOCKERHUB_TOKEN` | Token de acceso de Docker Hub |
| `EC2_HOST` | IP pública o DNS público de EC2 |
| `EC2_USER` | `ubuntu` |
| `EC2_SSH_KEY` | Contenido completo del archivo `.pem` |
| `POSTGRES_PASSWORD` | Clave de PostgreSQL |
| `JWT_SECRET` | Clave JWT de 32 caracteres o más |

El workflow `.github/workflows/ci-cd-ec2.yml` realiza:

1. Checkout del repositorio.
2. Configuración de Java 17.
3. Compilación y pruebas Maven.
4. Construcción de la imagen Docker.
5. Publicación en Docker Hub.
6. Conexión SSH con EC2.
7. Actualización automática de los contenedores.
8. Prueba del endpoint `/api/health`.

## 4. Despliegue serverless con AWS SAM

Requisitos en el computador:

- AWS CLI configurada con las credenciales temporales del Learner Lab.
- AWS SAM CLI.
- Java 17 y Maven.

Comandos:

```bash
cd aws
sam build
sam deploy --guided
```

Valores recomendados durante `sam deploy --guided`:

```text
Stack Name: prueba1java-serverless
AWS Region: us-east-1
Confirm changes before deploy: Y
Allow SAM CLI IAM role creation: Y
Disable rollback: N
Save arguments to configuration file: Y
```

Al terminar, SAM mostrará `ApiUrl`.

## 5. Prueba de API Gateway, Lambda y SQS

Enviar desde Postman:

```http
POST https://URL_GENERADA/mensajes
Content-Type: application/json

{
  "mensaje": "Hola mundo desde la EP3"
}
```

La respuesta esperada es HTTP `202`:

```json
{
  "estado": "EN_COLA",
  "messageId": "..."
}
```

Después revisar en AWS:

```text
CloudWatch -> Log groups -> /aws/lambda/prueba1java-consumidor
```

Debe aparecer una línea parecida a:

```text
Hola mundo asíncrono. Mensaje recibido: {"mensaje":"Hola mundo desde la EP3"}
```

## 6. Demostración del CI/CD

1. Cambiar el texto del endpoint `/api/health`.
2. Ejecutar `git add`, `git commit` y `git push` a `main`.
3. Abrir la pestaña `Actions` de GitHub.
4. Mostrar las etapas de prueba, Docker y despliegue.
5. Abrir `http://IP_PUBLICA_EC2:8081/api/health` y demostrar el cambio.

## 7. Orden sugerido para el video

1. Explicar la arquitectura.
2. Mostrar los archivos Docker.
3. Mostrar el microservicio funcionando en EC2.
4. Hacer un push y mostrar el pipeline CI/CD.
5. Probar el endpoint actualizado.
6. Enviar un mensaje a API Gateway.
7. Mostrar la cola SQS y la Lambda consumidora.
8. Mostrar el mensaje en CloudWatch Logs.
9. Cerrar explicando que la cola desacopla al productor del consumidor.

## Importante

No subir a GitHub archivos `.pem`, contraseñas, tokens ni el archivo `.env` real. La llave privada de EC2 debe guardarse únicamente como secreto cifrado en GitHub Actions.
