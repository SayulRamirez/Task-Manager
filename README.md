# API RESTFULL de gestión de tareas

---

La aplicación es un gestor básico de tareas (CRUD).

## ¿Cuáles fueron las tecnologías usadas?

1. La aplicación se realiza usando el lenguaje `JAVA 21`
2. Además se uso el framework `SPRING-BOOT 3.2.4` 
3. Se uso la base de datos `MySQL` para almacenar la información.
4. Para la conexión con dicha base de datos se utilizo `SPRING DATA JPA` junto con `HIBERNATE`
5. Para reducir el código y que este estuviera más limpio se utilizo la libreria de `LOMBOK`
6. También se agrego seguridad `SPRING SECURITY` y autenticación con `JWT` usando la libreria de  `io.jsonwebtoken`
7. Para probar la api se utilizó `INSOMNIA`
---

## Seguridad.

Los endpoints de la API están protegidos mediante la autenticación del usuario, además se puede registrar un nuevo usuario para poder utilizar la API

___

## Endpoints

### Registrar un nuevo usuario

Método http: `POST`

Ruta: `http://localhost:8080/auth/register`

Body: 
```
{
"email": "ejemplo@email.com",
"password": "12345678",
"nick": "Nuevo"
}
```

### Login de la api

Método http: `POST`

Ruta: `http://localhost:8080/auth/login`

Body:
```
{
	"email": "ejemplo@email.com",
	"password": "12345678"
}
```
La ruta devolvera un token mismo que debera llevar en las siguientes peticiones del servicio

### Creación de una nueva tarea

Método http: `POST`

Ruta: `http://localhost:8080/api/v1/task`

Body:
```
{
	"title": "Empezar a deducir las nominas",
	"description": "Tengo que calcular el ISR de cada empleado y validar los días trabajados para poder deducir su nomina",
	"start_date": "2024-04-28T23:54:00",
	"id_author": 1
}
```

Token: El token generado en el login de la aplicación.

### Actualización de una tarea

Método http: `PUT`

Ruta: `http://localhost:8080/api/v1/task`

Body:
```
{
	"id_task": 1,
	"title": "Aplasar fecha",
	"description": "Tengo que calcular el ISR de cada empleado y validar los días trabajados para poder deducir su nomina y tomar un café",
	"status": "COMPLETADA",
	"start_date": "2024-04-28T23:54:00",
	"id_author": 1
}
```
Token: El token generado en el login de la aplicación.

### Obtener todas las tareas de un usuario

Método http: `GET`

Ruta: `http://localhost:8080/api/v1/task/all/{1}`

Body: No necesario

Valor en la ruta: dentro de {} debe de ir el identificador del autor.

Token: El token generado en el login de la aplicación.

### Obtener una tarea especifica de un usuario

Método http: `GET`

Ruta: `http://localhost:8080/api/v1/task`

Body: 
```
{
	"id_task": 1,
	"id_author": 1
}
```

Token: El token generado en el login de la aplicación.

### Eliminar una tarea

Método http: `DELETE`

Ruta: `http://localhost:8080/api/v1/task`

Body:
```
{
	"id_task": 9,
	"id_author": 1
}
```

Token: El token generado en el login de la aplicación.

### Actualizar el estado de una tarea en especifica

Método http: `PUT`

Ruta: `http://localhost:8080/api/v1/task/status`

Body:
```
{
	"id_task": 2,
	"id_author": 1,
	"status": "EN_PROGRESO"
}
```

Token: El token generado en el login de la aplicación.
