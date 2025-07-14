package com.bookstore.api;

import io.restassured.response.Response;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;

/**
 * API client class for all Book-related endpoints.
 * All methods here should only handle HTTP request/response.
 * No assertions here! Keep this layer reusable for all types of tests.
 */
public class BooksClient {
    /**
     * GET all books from the API.
     * @return HTTP response object containing all books.
     */
    public static Response getAllBooks() {
        return given()
                .contentType(ContentType.JSON)
                .log().all() // Logs the request details for debugging
                .when()
                .get("/api/v1/Books");
    }

    /**
     * GET a single book by its ID.
     * @param id Book ID to retrieve
     * @return HTTP response object containing the book
     */
    public static Response getBookById(int id) {
        return given()
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("/api/v1/Books/" + id);
    }

    /**
     * POST a new book to the API.
     * @param book Book object to add
     * @return HTTP response object with created book
     */
    public static Response addBook(Object book) {
        return given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(book)
                .when()
                .post("/api/v1/Books");
    }

    /**
     * PUT update an existing book by ID.
     * @param id Book ID to update
     * @param book Updated book object
     * @return HTTP response object with updated book
     */
    public static Response updateBook(int id, Object book) {
        return given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(book)
                .when()
                .put("/api/v1/Books/" + id);
    }

    /**
     * DELETE a book by ID.
     * @param id Book ID to delete
     * @return HTTP response object
     */
    public static Response deleteBook(int id) {
        return given()
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .delete("/api/v1/Books/" + id);
    }
}