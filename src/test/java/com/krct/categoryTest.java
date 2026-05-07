package com.krct;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class categoryTest
{
    private int id;
    @BeforeClass
    public void setUp()
    {
        RestAssured.baseURI="https://api.escuelajs.co/api/v1";
    }
    @Test(priority = 1)
    public void testPostCategries() // In the testcases we need to create an new data in thw particular path "/categories"
    {
        String name="user"+System.currentTimeMillis();
        String image="http://google.com";
        Map boby=Map.of(
                "name",name,
                "image",image
        );
        Response response=RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(boby)
                .when()
                .post("/categories");
        response
                .then()
                .statusCode(201)
                .body("name", Matchers.equalTo(name));
        id = response.jsonPath().getInt("id");   // In this line we need to create an id for creating an new id eg:id =190
    }
    @Test(priority = 2)
    public void testgetCategory() //In the testcases we need to check the new data is updated or not
    {
        RestAssured.given()
                .pathParams("id",id)
                .when()
                .get("/categories/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", Matchers.equalTo(id));
    }
    @Test(priority = 3)
    public void testPutCategory() //In the testcases we need to update an data from the testcases1
    {
        String name1="user" +System.currentTimeMillis();
        String image1="www.amazon.com";
        Map body=Map.of(
                "name",name1,
                "image",image1
        );
        RestAssured.given()
                .contentType(ContentType.JSON)
                .pathParams("id",id)
                .body(body)
                .when()
                .put("/categories/{id}")
                .then()
                .statusCode(200)
                .body("name",Matchers.equalTo(name1))
                .body("image",Matchers.equalTo(image1));
    }
    @Test(priority = 4)
    public void testDeleteCategory()
    {
        RestAssured.given()
                .pathParams("id",id)
                .when()
                .delete("categories/{id}")
                .then()
                .statusCode(200);
    }
}
