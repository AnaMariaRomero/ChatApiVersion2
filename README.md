# MessagesApiRest
>Realizado con Eclipse IDE - Spring Boot - Postman

# Para correr
>Colocarse dentro del directorio /MessagesApiRest
	
>mvnw -DskipTests clean install

# Para correr los test
>mvnw test 

# Para correr la aplicación
>mvnw spring-boot:run

# Para probar el uso de la Api

1- Copie el siguiente Json:
>{
>    "sender" : "66232385-1a3e-48c9-ac4e-7a10a87a3acc",
>    "recipient" : "66232385-1a3e-48c9-ac4e-7a10a87a3acc",
>    "content" : "ya quisiera 2"
>}

ingresarlo en la siguente ruta como body:

>POST http://localhost:8082/api/messages/

2- Obtenga el mensaje creado anteriormente:
>GET http://localhost:8082/api/messages/1

3- Obtener todos los mensajes
>GET http://localhost:8082/api/messages/