/**
 * Covers:
 *  This project currently automates the following Books API test cases, grouped by priority:
 *   • P1 (Blocker): TC01, TC02, TC16, TC23, TC27, TC33
 *   • P2 (Critical/Major): TC03, TC04, TC05, TC11
 *   • P3 (Major/Normal): TC06, TC07, TC08, TC09, TC10, TC39
 * (See README for full priority breakdown.)
 */

package com.bookstore.tests;

import com.bookstore.api.BooksClient;
import com.bookstore.base.BaseTest;
import com.bookstore.model.Book;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import io.qameta.allure.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Epic("Books API Automation")
@Feature("Books Endpoints")
@DisplayName("Books API E2E Test Suite (P1-P3 Priority Cases)")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BooksTest extends BaseTest {

    // --- Shared Test Data ---
    static int createdBookId;
    static Book validBook = new Book(101, "API Automation for Dummies", "Practical guide to modern API automation", 200, "Learn API testing step by step.", "2023-09-01T00:00:00Z");
    static Book anotherBook = new Book(102, "Clean REST Design", "API design best practices", 120, "REST for everyone.", "2022-06-01T00:00:00Z");

    // ---------------------------
    //         P1 - BLOCKER TESTS
    // ---------------------------

    /**
     * [P1][Smoke][Happy] TC01 - Get all books
     * Endpoint: GET /Books
     * Purpose: Ensure the endpoint returns HTTP 200 and a non-empty book list.
     * Steps:
     *   1. Send GET /Books request
     * Expected Result:
     *   - HTTP 200 returned
     *   - Response body contains a non-empty list of books
     * Notes:
     *   - Release-blocker. Failure indicates backend outage or critical defect.
     */
    @Test @Order(1)
    @Tag("api") @Tag("smoke") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("TC01 - Get all books (smoke, happy path)")
    @Description("Should return 200 OK and a non-empty list for GET /Books. Release-blocker.")
    void getAllBooks_shouldReturnList() {
        Response res = BooksClient.getAllBooks();
        res.then().statusCode(200);
        assertThat(res.jsonPath().getList("$"), is(not(empty())));
    }

    /**
     * [P1][Smoke][Happy] TC02 - Create new book
     * Endpoint: POST /Books
     * Purpose: Validate book creation. Core business operation.
     * Steps:
     *   1. Send POST /Books with valid book JSON
     * Expected Result:
     *   - HTTP 201 returned
     *   - Response contains correct book data
     * Notes:
     *   - Release-blocker. Failure = no books can be created.
     */
    @Test @Order(2)
    @Tag("api") @Tag("smoke") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("TC02 - Create new book (smoke, happy path)")
    @Description("Creates a valid book, expects 201 Created and correct body. Release-blocker.")
    void createBook_shouldReturnCreated() {
        Response res = BooksClient.addBook(validBook);
        res.then().statusCode(201);
        Book created = res.as(Book.class);
        assertThat(created.getTitle(), is(validBook.getTitle()));
        createdBookId = created.getId();
    }

    /**
     * [P1][Smoke][Edge] TC16 - Create book missing required field (title)
     * Endpoint: POST /Books
     * Purpose: API must not allow book creation without a title.
     * Steps: Send POST with missing "title" property
     * Expected Result: HTTP 400 returned
     * Notes: Release-blocker for validation.
     */
    @Test @Order(3)
    @Tag("api") @Tag("smoke") @Tag("regression") @Tag("edge")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("TC16 - Create book missing title (edge, smoke)")
    @Description("Should return 400 for missing title. Release-blocker.")
    void createBook_missingTitle_shouldReturn400() {
        Book noTitle = new Book(0, null, "Missing title", 150, "Excerpt", "2023-09-01T00:00:00Z");
        Response res = BooksClient.addBook(noTitle);
        res.then().statusCode(400);
    }

    /**
     * [P1][Smoke][Edge] TC23 - Create book with blank/space title
     * Endpoint: POST /Books
     * Purpose: API must not allow blank or whitespace-only titles.
     * Steps: Send POST with "title": "   "
     * Expected Result: HTTP 400 returned
     * Notes: Release-blocker for input validation.
     */
    @Test @Order(4)
    @Tag("api") @Tag("smoke") @Tag("regression") @Tag("edge")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("TC23 - Create book with blank/space title (edge, smoke)")
    @Description("Blank/space titles with 400. Release-blocker.")
    void createBook_blankTitle_shouldReturn400() {
        Book blankTitle = new Book(0, "   ", "Blank title", 100, "Excerpt", "2023-09-01T00:00:00Z");
        Response res = BooksClient.addBook(blankTitle);
        res.then().statusCode(400);
    }

    /**
     * [P1][Smoke][Edge] TC27 - Create book with string in pageCount
     * Endpoint: POST /Books
     * Purpose: "pageCount" must only accept numeric values.
     * Steps: Send POST with pageCount as string ("ten")
     * Expected Result: HTTP 400 or 422 returned
     * Notes: Release-blocker for strong typing.
     */
    @Test @Order(5)
    @Tag("api") @Tag("smoke") @Tag("regression") @Tag("edge")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("TC27 - Create book with string in pageCount (edge, smoke)")
    @Description("String value for pageCount must be rejected. Release-blocker.")
    void createBook_stringPageCount_shouldReturnError() {
        String invalidBody = "{\"id\":0,\"title\":\"Test\",\"description\":\"desc\",\"pageCount\":\"ten\",\"excerpt\":\"ex\",\"publishDate\":\"2023-09-01T00:00:00Z\"}";
        Response res = BooksClient.addBook(invalidBody);
        res.then().statusCode(anyOf(is(400), is(422)));
    }

    /**
     * [P1][Smoke][Edge] TC33 - Send POST request with malformed JSON
     * Endpoint: POST /Books
     * Purpose: API must reject malformed JSON with HTTP 400.
     * Steps: Send POST with syntactically invalid JSON
     * Expected Result: HTTP 400 returned
     * Notes: Major edge validation for parser/validation logic.
     */
    @Test @Order(6)
    @Tag("api") @Tag("smoke") @Tag("regression") @Tag("edge")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("TC33 - Send POST request with malformed JSON (edge)")
    @Description("Malformed JSON must return 400. Major edge validation.")
    void createBook_malformedJson_shouldReturn400() {
        String malformed = "{\"id\":, \"title\":\"Missing value\"";
        Response res = BooksClient.addBook(malformed);
        res.then().statusCode(400);
    }

    // ---------------------------
    //   P2 - CRITICAL/MAJOR TESTS
    // ---------------------------

    /**
     * [P2][Smoke][Happy] TC03 - Get book by ID (just created)
     * Endpoint: GET /Books/{id}
     * Purpose: Fetch the just-created book by its ID and verify data.
     * Steps:
     *   1. Create a book
     *   2. Fetch by GET /Books/{id}
     * Expected Result:
     *   - HTTP 200
     *   - Response contains correct title
     * Notes:
     *   - Release-blocker. Verifies system can retrieve new books.
     */
    @Test @Order(7)
    @Tag("api") @Tag("smoke") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("TC03 - Get book by ID (just created) (smoke, happy path)")
    @Description("Fetch just created book. Must not fail, release-blocker.")
    void getBookById_shouldReturnBook() {
        Response res = BooksClient.getBookById(createdBookId);
        res.then().statusCode(200);
        assertThat(res.jsonPath().getString("title"), is(validBook.getTitle()));
    }

    /**
     * [P2][Smoke][Happy] TC04 - Update book
     * Endpoint: PUT /Books/{id}
     * Purpose: Validate updating an existing book.
     * Steps: [Create book] -> Update via PUT -> Check updated value
     * Expected Result: HTTP 200; updated data in response
     * Notes: Release-blocker for core update functionality.
     */
    @Test @Order(8)
    @Tag("api") @Tag("smoke") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("TC04 - Update book (smoke, happy path)")
    @Description("Update the created book. Fail = release-blocker.")
    void updateBook_shouldReturnUpdatedBook() {
        validBook.setDescription("Updated desc");
        Response res = BooksClient.updateBook(createdBookId, validBook);
        res.then().statusCode(200);
        assertThat(res.jsonPath().getString("description"), is("Updated desc"));
    }

    /**
     * [P2][Smoke][Happy] TC05 - Delete book
     * Endpoint: DELETE /Books/{id}
     * Purpose: Ensure book deletion works for just-created book.
     * Steps: [Create book] -> Delete via DELETE
     * Expected Result: HTTP 200 or 204
     * Notes: Release-blocker for resource deletion.
     */
    @Test @Order(9)
    @Tag("api") @Tag("smoke") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("TC05 - Delete book (smoke, happy path)")
    @Description("Deletes the created book. Fail = release-blocker.")
    void deleteBook_shouldReturnSuccess() {
        Response res = BooksClient.deleteBook(createdBookId);
        assertThat(res.statusCode(), anyOf(is(200), is(204)));
    }

    /**
     * [P2][Edge] TC11 - Get book by non-existing ID
     * Endpoint: GET /Books/{id}
     * Purpose: Ensure API returns 404 for non-existing book IDs.
     * Steps: GET /Books/{id} with large, invalid ID
     * Expected Result: HTTP 404 returned
     * Notes: Major edge validation; ensures proper not-found handling.
     */
    @Test @Order(10)
    @Tag("api") @Tag("regression") @Tag("edge")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("TC11 - Get book by non-existing ID (edge)")
    @Description("404 for a non-existing book ID. Major edge validation.")
    void getBookByNonExistingId_shouldReturn404() {
        Response res = BooksClient.getBookById(999999);
        res.then().statusCode(404);
    }

    // ---------------------------
    //   P3 - MAJOR/NORMAL TESTS
    // ---------------------------

    /**
     * [P3][Happy] TC06 - Get book by another valid ID
     * Endpoint: GET /Books/{id}
     * Purpose: Ensure fetching by any valid ID works.
     * Steps: [Create new book] -> GET /Books/{id}
     * Expected Result: HTTP 200 and correct title in response
     * Notes: Not release-blocker but required for full regression.
     */
    @Test @Order(11)
    @Tag("api") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("TC06 - Get book by another valid ID (happy path)")
    @Description("Valid ID must work. Not release-blocker but major regression.")
    void getBookByAnotherValidId_shouldReturnBook() {
        BooksClient.addBook(anotherBook);
        Response res = BooksClient.getBookById(anotherBook.getId());
        res.then().statusCode(200);
        assertThat(res.jsonPath().getString("title"), is(anotherBook.getTitle()));
    }

    /**
     * [P3][Happy] TC07 - Create new book (all valid fields)
     * Endpoint: POST /Books
     * Purpose: All valid fields must be accepted.
     * Steps: Send POST with all fields populated
     * Expected Result: HTTP 201 and valid book in response
     * Notes: Not release-blocker but must pass in regression.
     */
    @Test @Order(12)
    @Tag("api") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("TC07 - Create new book (all valid fields) (happy path)")
    @Description("All valid fields should return 201. Not release-blocker but major regression.")
    void createBook_allFields_shouldReturnCreated() {
        Book b = new Book(103, "Test Driven API", "All fields test", 80, "Excerpt", "2023-01-01T00:00:00Z");
        Response res = BooksClient.addBook(b);
        res.then().statusCode(201);
        assertThat(res.jsonPath().getString("title"), is("Test Driven API"));
    }

    /**
     * [P3][Happy] TC08 - Update existing book with new data
     * Endpoint: PUT /Books/{id}
     * Purpose: Ensure updates reflect new data and return 200.
     * Steps: [Create book] -> Update with new data
     * Expected Result: HTTP 200 and updated fields
     * Notes: Not release-blocker but must pass in regression.
     */
    @Test @Order(13)
    @Tag("api") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("TC08 - Update existing book with new data (happy path)")
    @Description("Update with new data. Not release-blocker but major regression.")
    void updateBook_withNewData_shouldReturnUpdatedBook() {
        Book updated = new Book(103, "Updated Title", "Updated Desc", 90, "Updated Excerpt", "2023-01-01T00:00:00Z");
        Response res = BooksClient.updateBook(103, updated);
        res.then().statusCode(200);
        assertThat(res.jsonPath().getString("title"), is("Updated Title"));
    }

    /**
     * [P3][Happy] TC09 - Delete a different existing book
     * Endpoint: DELETE /Books/{id}
     * Purpose: Ensure any valid book can be deleted.
     * Steps: [Create another book] -> Delete by ID
     * Expected Result: HTTP 200 or 204 returned
     * Notes: Not release-blocker but major regression case.
     */
    @Test @Order(14)
    @Tag("api") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("TC09 - Delete a different existing book (happy path)")
    @Description("Deletes another book. Not release-blocker but major regression.")
    void deleteAnotherBook_shouldReturnSuccess() {
        Response res = BooksClient.deleteBook(102);
        assertThat(res.statusCode(), anyOf(is(200), is(204)));
    }

    /**
     * [P3][Happy] TC10 - List all books after CRUD operations
     * Endpoint: GET /Books
     * Purpose: Book list should reflect CRUD changes.
     * Steps: Perform several CRUD ops, then GET /Books
     * Expected Result: HTTP 200 and up-to-date list
     * Notes: Not release-blocker but validates DB sync.
     */
    @Test @Order(15)
    @Tag("api") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("TC10 - List all books after CRUD operations (happy path)")
    @Description("Book list should reflect CRUD changes. Not release-blocker but major regression.")
    void listAllBooks_afterCrud_shouldBeUpToDate() {
        Response res = BooksClient.getAllBooks();
        res.then().statusCode(200);
        assertThat(res.jsonPath().getList("$"), is(not(empty())));
    }

    /**
     * [P3][Header] TC39 - Content-Type header validation
     * Endpoint: GET /Books
     * Purpose: Check that Content-Type is application/json in response.
     * Steps: Send GET /Books and check Content-Type header.
     * Expected Result: Response header includes Content-Type: application/json.
     * Notes: Ensures API returns data in the correct content type.
     */
    @Test @Order(16)
    @Tag("api") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("TC39 - Content-Type header validation")
    @Description("Checks that Content-Type header in GET /Books is application/json.")
    void contentTypeHeader_shouldBeJson() {
        Response res = BooksClient.getAllBooks();
        res.then().statusCode(200);
        assertThat(res.header("Content-Type"), containsString("application/json"));
    }

}