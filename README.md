# Practica 6: Testing de una aplicacion de Spring Boot

##DNITest
Pruebo DNIs con letra, sin letra, excediendo el número y con los valores correctos. <br>
Todos funcionan.

##TelefonoTest 
He probado con teléfonos españoles y extranjeros, y luego con teléfonos incorrectos.

##ProcessControllerTest
Primero compruebo el endpoint process-step1 creando un DataRequest. <br>
El endpoint de process-step1-legacy se comprobará creando un MultiValueMap y usando un MediaType de tipo APPLICATION_FORM_URLENCODED.