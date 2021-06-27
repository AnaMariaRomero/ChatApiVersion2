# ChatApiVersion2
>Realizado con Eclipse IDE - Spring Boot - Postman - Docker - Gitlab CI

# Estado
>En desarrollo. Falta realizar la detección del idioma y subir el test realizado a User.class

# Para correr
>Colocarse dentro del directorio /ChatApiVersion2
	
>mvnw -DskipTests clean install

# Para correr los test
>mvnw test 

# Para correr la aplicación
>mvnw spring-boot:run

# Para probar el uso de la Api: cURL

POST
>curl -d {\"name\":\"juan\",\"email\":\"asj@gmail.com\",\"alias\":\"fatos\"} -H "Content-Type: application/json" http://localhost:8082/api/users/create/ -v

>curl -d {\"name\":\"juan\",\"email\":\"aj@gmail.com\",\"alias\":\"patos\"} -H "Content-Type: application/json" http://localhost:8082/api/users/create/ -v

GET
>curl http://localhost:8082/api/users/  

PUT 
>curl -d {\"status\":\"Inactivo\"} -H "Content-Type: application/json" -X PUT http://localhost:8082/api/users/fatos/status/ -v

PUT
>curl -d {\"recipient\":\"876aaf41-54c5-40cf-a1da-934d728cf3ff\",\"content\":\"ya quisiera 2"} -H "Content-Type: application/json" -X PUT http://localhost:8082/api/users/fato/chat/

Pero acabamos de inactivar el usuario, probemos:

>curl -d {\"recipient\":\"876aaf41-54c5-40cf-a1da-934d728cf3ff\",\"content\":\"ya quisiera 2"} -H "Content-Type: application/json" -X PUT http://localhost:8082/api/users/patos/chat/

# Para probar el uso de la Api: cURL

1- Copie el siguiente Json:
>{
>    "name" : "juan",
>    "email" : "jkjk@gmail.com",
>    "alias" : "lalo"
>}

ingresarlo en la siguente ruta como body:

>POST http://localhost:8082/api/users/create/

2- Obtenga el perfil del usuario creado anteriormente:
>GET http://localhost:8082/api/users/lalo

3- Obtener todos los usuarios
>GET http://localhost:8082/api/users/

4- Enviar un mensaje:
>(para ello, copie el siguiente json y copielo como body: 
>{
>    "recipient" : "66232385-8c9-ac4e-7a10a87a3acc",
>    "content" : "ya quisiera 2"
})

>PUT http://localhost:8082/api/users/lalo/chat/

5- Para modificar el estado del usuario:
>(para ello, copie el siguiente json y copielo como body: 
>{
>    "status" : "66232385-8c9-ac4e-7a10a87a3acc"
})

>PUT http://localhost:8082/api/users/lalo/status

# Usar Docker

1- Vamos a construir la imagen, abrimos consola desde el directorio /ChatApiVersion2:
>docker build -t tagdelaimagen .

2- Van a aparecer dos imagenes, la propia del proyecto y del jdk 
>Para comenzar a correr nuestro proyecto
>docker run -e DATABASE_SERVER=jdbc:h2:mem:tmpdb -p 8080:8082 tagmelaimagen -d