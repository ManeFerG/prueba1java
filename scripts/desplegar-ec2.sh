#!/usr/bin/env bash
set -euo pipefail

APP_DIR="${APP_DIR:-/opt/prueba1java}"
cd "$APP_DIR"

if [[ ! -f .env ]]; then
  echo "Falta $APP_DIR/.env"
  exit 1
fi

if [[ ! -f docker-compose.ec2.yml ]]; then
  echo "Falta $APP_DIR/docker-compose.ec2.yml"
  exit 1
fi

docker compose --env-file .env -f docker-compose.ec2.yml pull
docker compose --env-file .env -f docker-compose.ec2.yml up -d --remove-orphans
docker image prune -f

docker compose --env-file .env -f docker-compose.ec2.yml ps

# Espera hasta 90 segundos por el endpoint.
for intento in {1..18}; do
  if curl --fail --silent http://localhost:8081/api/health >/dev/null; then
    echo "Despliegue correcto: usuarios-service responde en /api/health"
    curl --silent http://localhost:8081/api/health
    echo
    exit 0
  fi
  echo "Esperando al microservicio... intento $intento/18"
  sleep 5
done

echo "El contenedor se inició, pero el health endpoint no respondió a tiempo."
docker compose --env-file .env -f docker-compose.ec2.yml logs --tail=120 usuarios-service
exit 1
