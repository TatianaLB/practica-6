package com.icai.practicas.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DNITest {

    @Test
    public void given_app_when_login_using_right_dni_then_ok()  {
        DNI dniValue1 = new DNI("12345678Z");
        DNI dniValue2 = new DNI("111111111");

        //Usamos la función public boolean validar()

        boolean dniValue1_validar = dniValue1.validar();
        boolean dniValue2_validar = dniValue2.validar();

        assertTrue(dniValue1_validar); //DNI en formato correcto, válido
        assertFalse(dniValue2_validar); //DNI en formato incorrecto, inválido
    }

    @Test
    public void given_app_when_login_using_right_dni_then_ok2() {

        //Given
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        //When
        DNI dniValue1 = new DNI("12345678Z");
        DNI dniValue2 = new DNI("111111111");
        Set<ConstraintViolation<DNI>> violations = validator.validate(dniValue1);

        //Then
        then(violations.size()).isEqualTo(0);
    }

}
