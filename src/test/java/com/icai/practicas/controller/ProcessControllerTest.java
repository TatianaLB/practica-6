package com.icai.practicas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icai.practicas.service.ProcessService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.core.ParameterizedTypeReference;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.icai.practicas.service.ProcessService;
import com.icai.practicas.service.impl.ProcessServiceImpl;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProcessControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void given_endpoint_when_login_then_ok(){

        //Given
        String address = "http://localhost:" + port + "/api/v1/process-step1";

        //Son MultiValueMap
        String fullNameRaw = "Tatiana Lopez";
        String dniRaw = "45476738F";
        String telefonoRaw = "+34 657678965";

        ProcessController.DataRequest data1 = new ProcessController.DataRequest(fullNameRaw, dniRaw, telefonoRaw);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProcessController.DataRequest> request = new HttpEntity<>(data1, headers);

        //When
        ResponseEntity<String> result = this.restTemplate.postForEntity(address, request, String.class);

        //Then
        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }



    @Test
    public void given_endpoint_when_login_using_bad_credentials_then_ko() {

        //Given
        String address = "http://localhost:" + port + "/api/v1/process-step1";

        //Son MultiValueMap
        String fullNameRaw = "Tatiana Lopez";
        String dniRaw = "4547673";
        String telefonoRaw = "+34 657678965";

        ProcessController.DataRequest data1 = new ProcessController.DataRequest(fullNameRaw, dniRaw, telefonoRaw);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProcessController.DataRequest> request = new HttpEntity<>(data1, headers);

        //When
        ResponseEntity<String> result = this.restTemplate.postForEntity(address, request, String.class);

        //Then
        //then(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        String expectedResult = "{\"result\":\"KO\"}";
        then(result.getBody()).isEqualTo(expectedResult);
    }


    //IMPORTANTE: al estar todos los caracteres codificados antes de ser enviados:
    // action="/api/v1/process-step1-legacy"
    // enctype="application/x-www-form-urlencoded"
    //Hay que usar como nombre de las variables el id que usamos en el formulario html ("fullName" y no fulNameRaw)
    //Y además usar el tipo de MediaType: APPLICATION_FORM_URLENCODED

    @Test
    public void given_endpoint_legacy_when_login_then_ok(){

        //Given
        String address = "http://localhost:" + port + "/api/v1/process-step1-legacy";

        //Los datos son MultiValueMap
        //Con todos los datos correctamente
        MultiValueMap<String, String> data1 = new LinkedMultiValueMap<>();
        data1.add("fullName", "Tatiana Lopez");
        data1.add("dni", "123456789F");
        data1.add("telefono", "+34657678965");

        //Probamos que con los posibles errores, también se procesa bien el endpoint
        //Con el nombre incorrecto
        MultiValueMap<String, String> data2 = new LinkedMultiValueMap<>();
        data2.add("fullName", "TatianaxLopez");
        data2.add("dni", "123456789F");
        data2.add("telefono", "+34 657678965");

        //Con el dni incorrecto
        MultiValueMap<String, String> data3 = new LinkedMultiValueMap<>();
        data3.add("fullName", "TatianaxLopez");
        data3.add("dni", "123456789000");
        data3.add("telefono", "+34 657678965");

        //Con el teléfono incorrecto
        MultiValueMap<String, String> data4 = new LinkedMultiValueMap<>();
        data4.add("fullName", "TatianaxLopez");
        data4.add("dni", "123456789F");
        data4.add("telefono", "bbg7678965");

        //Request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //Para cada conjunto de datos que probamos:
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(data1, headers);
        HttpEntity<MultiValueMap<String, String>> request2 = new HttpEntity<>(data2, headers);
        HttpEntity<MultiValueMap<String, String>> request3 = new HttpEntity<>(data2, headers);
        HttpEntity<MultiValueMap<String, String>> request4 = new HttpEntity<>(data2, headers);

        //When
        ResponseEntity<String> result = this.restTemplate.postForEntity(address, request, String.class);
        ResponseEntity<String> result2 = this.restTemplate.postForEntity(address, request2, String.class);
        ResponseEntity<String> result3 = this.restTemplate.postForEntity(address, request3, String.class);
        ResponseEntity<String> result4 = this.restTemplate.postForEntity(address, request4, String.class);


        //Then, comprobamos que con todas las posibilidades se ejecuta bien el endpoint.
        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(result2.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(result3.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(result4.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
