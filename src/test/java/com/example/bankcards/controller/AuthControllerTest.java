package com.example.bankcards.controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.notNullValue;

public class AuthControllerTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:4000";
    }

    @Test
    public void shouldReturnOkWithValidToken() {

        String loginPayload = """
                {
                  "email": "admin2@example.com",
                  "password": "1"
                }
                """;

        Response response = RestAssured.given()     // arrange
                .contentType("application/json")
                .body(loginPayload)
                .when()                             // act
                .post("/auth")
                .then()                             // assert
                .statusCode(200)
                .body("token", notNullValue())
                .extract().response();

        System.out.println("Generated token: " + response.jsonPath().getString("token"));
    }
}
