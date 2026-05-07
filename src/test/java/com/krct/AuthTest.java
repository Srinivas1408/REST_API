package com.krct;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class AuthTest
{
    private String email;
    private String password;

    private String accesstoken;

    @BeforeClass
    public void setUp()
    {
        RestAssured.baseURI="https://api.escuelajs.co/api/v1";
    }

    @Test(priority = 1)
    public void testRegister()
    {
        String name = "user_" + System.currentTimeMillis();
        String email = "user"+System.currentTimeMillis() +"@gmail.com";
        String password = "123456";
        String avatar = "https://picsum.photos/700";

        Map body = Map.of(
                "name",name,
                "email",email,
                "password",password,
                "avatar",avatar
        );
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/users");
        response
                .then()
                .log().all()
                .statusCode(201)
                .body("id", Matchers.notNullValue());
    }
    @Test(priority = 2)
    public void testLogin()
    {
        email = "john@mail.com";
        password = "changeme";
        Map data=Map.of(
                "email",email,
                "password",password
        );

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/auth/login");
        response
                .then()
                .log().all()
                .statusCode(201)
                .body("access_token",Matchers.notNullValue())
                .body("refresh_token",Matchers.notNullValue());
        accesstoken = response.jsonPath().getString("access_token");
    }
    @Test(priority = 3)
    public void testGetUser()
    {
        RestAssured.given()
                .header("Authorization","Bearer " +accesstoken)
                .when()
                .get("/auth/profile")
                .then()
                .log().all()
                .statusCode(200)
                .body("id",Matchers.notNullValue())
                .body("email",Matchers.equalTo(email));

    }


}
