package com.mycompany.studentapi.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


import com.mycompany.studentapi.repository.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class JwtUtilityTests {

    private static JwtUtility jwtUtility;

    /*
    The @BeforeAll annotation in Java is part of the JUnit 5 testing framework (JUnit Jupiter).
    It is used to designate a method that should be executed once before all test methods in
    the current test class. This is typically used for setting up resources that are shared
    across all test methods, such as initializing a database connection, starting a server,
    or loading configuration files.
     */

    @BeforeAll
    static void setup(){
        jwtUtility = new JwtUtility();
        jwtUtility.setIssuerName("My Company");
        jwtUtility.setAccessTokenExpiration(2);
        jwtUtility.setSecretKey("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuv+9-$!*&%");
    }


    @Test
    public void testGenerateFail(){
        assertThrows(IllegalArgumentException.class, new Executable() {

            @Override
            public void execute() throws Throwable {
                User user = null;
                jwtUtility.generateAccessToken(user);
            }
        });
    }

    @Test
    public void testGenerateSuccess(){
        User user = new User();
        user.setId(3);
        user.setUsername("johndoe");
        user.setRole("read");

        String token = jwtUtility.generateAccessToken(user);
        assertThat(token).isNotNull();
        System.out.println(token);
    }

    @Test
    public void testValidateFail(){
        assertThrows(JwtValidationException.class, () -> {
           jwtUtility.validateAccessToken("a.b.c");
        });
    }

    @Test
    public void testValidatedSuccess(){
        User user = new User();
        user.setId(3);
        user.setUsername("johndoe");
        user.setRole("read");

        String token = jwtUtility.generateAccessToken(user);
        assertThat(token).isNotNull();

        assertDoesNotThrow(() -> {
            jwtUtility.validateAccessToken(token);
        });

    }

}
