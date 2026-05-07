package com.krct;

import io.restassured.RestAssured;
import io.restassured.specification.Argument;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.core.Every;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiTest
{
    @BeforeClass
    public void setUp()
    {
        RestAssured.baseURI="https://api.escuelajs.co/api/v1";
    }
    //common usages
    @Test
    public void ApiTest1()
    {
        RestAssured.given()
            .when()
            .get("/products")
            .then()
            .statusCode(200)
                .body("size()", Matchers.greaterThan(0));
    }
    //query parameters for specific conditions for all elements soo we use $-> all elements
    @Test
    public void ApiTest2()
    {
        RestAssured.given()
                .queryParam("price",100)
                .when()
                .get("/products")
                .then()
                .statusCode(200);
               // .body("$", everyItem(equalTo(100)));
    }

    //categories with id so we using path parameter
    @Test
    public void ApiTest3()
    {
        RestAssured.given()
                .pathParams("id","1")
                .when()
                .get("/categories/{id}")  //
                .then()
                .statusCode(200)
                .body("id",Matchers.equalTo(1));
    }
    //categories with get all elements
    @Test
    public void ApiTest4()
    {
        RestAssured.given()
                .when()
                .get("/categories")
                .then()
                .statusCode(200)
                .body("$",Matchers.instanceOf(List.class));  //$ means It denotes all .The values comes under list format
    }

    //Filter for single elements
    @Test
    public void ApiTest5()
    {
        RestAssured.given()
                .queryParam("price","100")
                .when()
                .get("/products")
                .then()

                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(0));
    }


}
