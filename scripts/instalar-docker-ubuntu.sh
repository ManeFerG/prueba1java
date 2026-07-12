#!/usr/bin/env bash
set -euo pipefail

sudo apt-get update
sudo DEBIAN_FRONTEND=noninteractive apt-get install -y docker.io docker-compose-v2 curl
sudo systemctl enable --now docker
sudo usermod -aG docker "$USER"

sudo mkdir -p /opt/prueba1java
sudo chown -R "$USER":"$USER" /opt/prueba1java

echo "Docker instalado. Cierra la sesión SSH y vuelve a entrar para usar Docker sin sudo."
