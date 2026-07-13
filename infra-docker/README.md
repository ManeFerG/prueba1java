## Despliegue con Docker Swarm

Inicializar Swarm:
```bash
docker swarm init

```
Desplegar el stack:
```bash
docker stack deploy -c docker-compose.yml prueba1java

```
Ver los servicios:
```bash
docker service ls

```
Ver los nodos:
```bash
docker node ls

```
Escalar el microservicio de usuarios:
```bash
docker service scale prueba1java_usuarios-service=3

```
Reducir nuevamente las réplicas:
```bash
docker service scale prueba1java_usuarios-service=2
```