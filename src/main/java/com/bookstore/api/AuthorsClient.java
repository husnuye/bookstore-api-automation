package com.bookstore.api;

import io.restassured.response.Response;
import io.restassured.http.ContentType;
import io.qameta.allure.restassured.AllureRestAssured;

import static io.restassured.RestAssured.*;

/**
 * API client class for all Author-related endpoints.
 * All methods here should only handle HTTP request/response.
 * No assertions here! Keep this layer reusable for all types of tests.
 */
public class AuthorsClient {

    /**
     * GET all authors from the API.
     * @return HTTP response object containing all authors.
     */
    public static Response getAllAuthors() {
        return given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("/api/v1/Authors");
    }

    /**
     * GET a single author by its ID.
     * @param id Author ID to retrieve
     * @return HTTP response object containing the author
     */
    public static Response getAuthorById(int id) {
        return given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("/api/v1/Authors/" + id);
    }

    /**
     * GET a single author by string ID (for edge cases).
     * @param id Author ID as string (can be number, string, special char, etc.)
     * @return HTTP response object containing the author/error
     */
    public static Response getAuthorById(String id) {
        return given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("/api/v1/Authors/" + id);
    }

    /**
     * POST a new author to the API.
     * @param author Author object to add
     * @return HTTP response object with created author
     */
    public static Response addAuthor(Object author) {
        return given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .log().all()
                .body(author)
                .when()
                .post("/api/v1/Authors");
    }

    /**
     * PUT update an existing author by ID.
     * @param id Author ID to update
     * @param author Updated author object
     * @return HTTP response object with updated author
     */
    public static Response updateAuthor(int id, Object author) {
        return given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .log().all()
                .body(author)
                .when()
                .put("/api/v1/Authors/" + id);
    }

    /**
     * DELETE an author by ID.
     * @param id Author ID to delete
     * @return HTTP response object
     */
    public static Response deleteAuthor(int id) {
        return given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .delete("/api/v1/Authors/" + id);
    }

    /**
     * DELETE an author by string ID (for edge case tests).
     * @param id Author ID as string (number, string, special char, etc.)
     * @return HTTP response object
     */
    public static Response deleteAuthor(String id) {
        return given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .delete("/api/v1/Authors/" + id);
    }
}