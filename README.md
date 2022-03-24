# Practica 6: Testing de una aplicacion de Spring Boot

## Objetivo de la práctica

Dado un desarrollo de Spring Boot, es necesario anhadir tests a las siguientes clases:

- DNI & Telefono (Unit Tests) (Cada clase tiene un metodo y varias casuisticas para probar)
- ProcessController (E2E Tests) (2 endpoints)

```
mvn clean spring-boot:run

curl -v -X POST http://localhost:8080/api/v1/process-step1-legacy \
   -H "Content-Type: application/x-www-form-urlencoded" \
   -d "fullName=Juan%20Antonio%20Brena%20Moral&dni=12345678Z&telefono=%2B34%20600903434"

curl -v -X POST http://localhost:8080/api/v1/process-step1 \
   -H 'Content-Type: application/json' \
   -d '{"fullName":"Juan Antonio Brena Moral","dni":"12345678Z", "telefono":"+34 600903434"}'
```

## Entrega

Sube la practica solucionada a un repositorio de Github.
y crea un documento en formato Markdown, explicando las casuisticas que se van a probar.

## References

- https://docs.spring.io/spring-boot/docs/1.5.16.RELEASE/reference/html/boot-features-testing.html
- https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/web/client/TestRestTemplate.html
- https://www.urlencoder.org/