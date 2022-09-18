# VendingMachineChallenge

**UNDER UPDATE**

## Project

Used stack
- Java
- Springboot
- Maven
- Postgresql
- pgAdmin4
- Docker

## How to operate the vending machine

Afrer registering a user("/api/user"), it is necessary to log in trough ("/api/login") since most of all endpoints require authentication username that comes from JWT token.
By default, the roles BUYER and SELLER are registered on the database.

### OpenAPI

Under src/main/resources is a API doc that can be imported into postman or another user choice.

## Using Docker to simplify development(optional)

### Runing postgresql&pgAdmin4

To start a postgresql and pgAdmin container run:

```
docker-compose -f src/main/resources/postgresql-pgadmin.yml up -d
```
to configure pgAdmin(acessible on http://localhost:5050), login with username admin & password admin. Register a server with any name, set connection host adress to **host.docker.internal**, user admin & password admin.