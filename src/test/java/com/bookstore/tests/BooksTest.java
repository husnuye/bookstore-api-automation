package com.bookstore.tests;

import com.bookstore.api.BooksClient;
import com.bookstore.base.BaseTest;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

/**
 * Test class for Book endpoints.
 * Contains both happy and edge/negative case tests.
 * Always use clear and descriptive method names and comments.
 */
@Epic("Books API")
@Feature("Books Endpoint")
public class BooksTest extends BaseTest {

    /**
     * Happy path: Get all books.
     * Should return 200 and a non-empty list.
     */
    @Test
    @Story("List all books (happy path)")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("happy")
    @Description("Should return 200 OK and at least one book in the response")
    public void testGetAllBooks_Happy() {
        BooksClient.getAllBooks()
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    /**
     * Edge case: Try to get a non-existing book.
     * Should return 404 Not Found.
     */
    @Test
    @Story("Get book by non-existing ID (edge case)")
    @Severity(SeverityLevel.NORMAL)
    @Tag("edge")
    @Description("Should return 404 Not Found for a non-existing book ID")
    public void testGetBookById_NonExisting_Edge() {
        int nonExistingId = 99999;
        BooksClient.getBookById(nonExistingId)
                .then()
                .statusCode(404);
    }
}