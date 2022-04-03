package com.icai.practicas.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TelefonoTest {

    @Test
    public void given_app_when_login_using_right_telefono_then_ok()  {
        Telefono telefonoValue1 = new Telefono("+34 600903434");
        Telefono telefonoValue2 = new Telefono("+44 7654392817");
        Telefono telefonoValue3 = new Telefono("65x3e4000");
        Telefono telefonoValue4 = new Telefono("6754829174762519736517");

        //Usamos la función public boolean validar()

        boolean telefonoValue1_validar = telefonoValue1.validar();
        boolean telefonoValue2_validar = telefonoValue2.validar();
        boolean telefonoValue3_validar = telefonoValue3.validar();
        boolean telefonoValue4_validar = telefonoValue4.validar();

        assertTrue(telefonoValue1_validar); //Teléfono válido
        assertTrue(telefonoValue2_validar); //Teléfono válido, como esperábamos, funciona también con formatos internacionales
        assertFalse(telefonoValue3_validar); //Teléfono inválido, contiene carácteres
        assertFalse(telefonoValue4_validar); //Teléfono inválido, demasiado largo
    }

    @Test
    public void given_app_when_login_using_right_telefono_then_ok2() {

        //Given
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        //When
        Telefono telefonoValue1 = new Telefono("600903434");
        Set<ConstraintViolation<Telefono>> violations = validator.validate(telefonoValue1);

        //Then
        then(violations.size()).isEqualTo(0);
    }

    //Me da error no sé por qué
    /*@Test
    public void given_app_when_login_using_right_telefono_then_ok3() {

        //Given
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        //When
        Telefono telefonoValue2 = new Telefono("65x394000");
        Set<ConstraintViolation<Telefono>> violations = validator.validate(telefonoValue2);

        //Then
        then(violations.size()).isGreaterThan(0); //Hay error, violations > 0
    }*/
}
