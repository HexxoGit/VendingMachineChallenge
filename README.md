# VendingMachineChallenge

**UNDER UPDATE**

## TechStack

Java
Springboot
postgresql
pgadmin4
docker

## Using Docker to simplify development(optional)

### Runing postgresql&pgAdmin4

To start a postgresql and pgAdmin container run:

```
docker-compose -f pATH/postgresql-pgadmin.yml up -d
```
to configure pgAdmin(acessible on http://localhost:5050), login with username admin & password admin. Register a server with any name, connection host adress **host.docker.internal**, user admin & password admin.

## OpenAPI

Under src/main/resources is a API doc that can be imported into postman or another user choice.