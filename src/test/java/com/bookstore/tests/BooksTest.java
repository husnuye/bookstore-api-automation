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
@DisplayName("Books API E2E Test Suite (P1 + P2)")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BooksTest extends BaseTest {

    // --- Shared Test Data ---
    static int createdBookId;
    static Book validBook = new Book(101, "API Automation for Dummies", "Practical guide to modern API automation", 200, "Learn API testing step by step.", "2023-09-01T00:00:00Z");
    static Book anotherBook = new Book(102, "Clean REST Design", "API design best practices", 120, "REST for everyone.", "2022-06-01T00:00:00Z");

    // ---------------------------
    //         P1 TEST CASES
    // ---------------------------

    /**
     * [P1][Smoke] TC01 - Get all books
     * Endpoint: GET /Books
     * Purpose: Ensure the endpoint returns HTTP 200 and a non-empty book list.
     * If this test fails, it indicates a potential backend outage or data retrieval bug.
     */
    @Test @Order(1)
    @Tag("api") @Tag("smoke") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("TC01 - Get all books (smoke, happy path)")
    @Description("Should return 200 OK and a non-empty list for GET /Books. If fails, report as backend/API bug.")
    void getAllBooks_shouldReturnList() {
        Response res = BooksClient.getAllBooks();
        res.then().statusCode(200);
        assertThat(res.jsonPath().getList("$"), is(not(empty())));
    }

    /**
     * [P1][Smoke] TC02 - Create new book
     * Endpoint: POST /Books
     * Purpose: Validate book creation returns 201 Created and correct response.
     * Failure here means a blocker bug, since book creation is core.
     */
    @Test @Order(2)
    @Tag("api") @Tag("smoke") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("TC02 - Create new book (smoke, happy path)")
    @Description("Creates a valid book, expects 201 Created and correct body. Failure means critical bug - must be reported.")
    void createBook_shouldReturnCreated() {
        Response res = BooksClient.addBook(validBook);
        res.then().statusCode(201);
        Book created = res.as(Book.class);
        assertThat(created.getTitle(), is(validBook.getTitle()));
        createdBookId = created.getId();
    }

    /**
     * [P1][Smoke] TC03 - Get book by ID (just created)
     * Endpoint: GET /Books/{id}
     * Purpose: Verify that the newly created book can be fetched by its ID.
     * Failure indicates data inconsistency or API GET bug.
     */
    @Test @Order(3)
    @Tag("api") @Tag("smoke") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("TC03 - Get book by ID (just created) (smoke, happy path)")
    @Description("Fetch the book just created. Expects 200 and correct book data. Fail = API bug, must be reported.")
    void getBookById_shouldReturnBook() {
        Response res = BooksClient.getBookById(createdBookId);
        res.then().statusCode(200);
        assertThat(res.jsonPath().getString("title"), is(validBook.getTitle()));
    }

    /**
     * [P1][Smoke] TC04 - Update book
     * Endpoint: PUT /Books/{id}
     * Purpose: Test updating book works and reflects new description.
     * Failure is an update logic bug.
     */
    @Test @Order(4)
    @Tag("api") @Tag("smoke") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("TC04 - Update book (smoke, happy path)")
    @Description("Update the created book, expect 200 OK and updated data. Failures should be reported as product bug.")
    void updateBook_shouldReturnUpdatedBook() {
        validBook.setDescription("Updated desc");
        Response res = BooksClient.updateBook(createdBookId, validBook);
        res.then().statusCode(200);
        assertThat(res.jsonPath().getString("description"), is("Updated desc"));
    }

    /**
     * [P1][Smoke] TC05 - Delete book
     * Endpoint: DELETE /Books/{id}
     * Purpose: Check deleting a book returns 200/204 (success).
     * Fail means resource is not being deleted (product bug).
     */
    @Test @Order(5)
    @Tag("api") @Tag("smoke") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("TC05 - Delete book (smoke, happy path)")
    @Description("Deletes the created book, expects 200/204. Failure = delete logic bug, must report.")
    void deleteBook_shouldReturnSuccess() {
        Response res = BooksClient.deleteBook(createdBookId);
        assertThat(res.statusCode(), anyOf(is(200), is(204)));
    }

    /**
     * [P1][Edge] TC11 - Get book by non-existing ID
     * Endpoint: GET /Books/{id}
     * Purpose: Ensure API returns 404 for IDs that do not exist.
     * If API does not return 404, this is a backend validation bug.
     */
    @Test
    @Tag("api") @Tag("regression") @Tag("edge")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("TC11 - Get book by non-existing ID (edge)")
    @Description("Expect 404 for a non-existing book ID. Otherwise, the system is leaking data or not handling edge case.")
    void getBookByNonExistingId_shouldReturn404() {
        Response res = BooksClient.getBookById(999999);
        res.then().statusCode(404);
    }

    /**
     * [P1][Edge] TC16 - Create book missing required field (title)
     * Endpoint: POST /Books
     * Purpose: Must reject requests without title (expect 400).
     * If API allows creation, this is a critical validation bug.
     */
    @Test
    @Tag("api") @Tag("regression") @Tag("edge")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("TC16 - Create book missing title (edge)")
    @Description("Should return 400 for missing title. If not, backend allows invalid data (blocker bug).")
    void createBook_missingTitle_shouldReturn400() {
        Book noTitle = new Book(0, null, "Missing title", 150, "Excerpt", "2023-09-01T00:00:00Z");
        Response res = BooksClient.addBook(noTitle);
        res.then().statusCode(400);
    }

    /**
     * [P1][Edge] TC23 - Create book with blank/space title
     * Endpoint: POST /Books
     * Purpose: Blank titles should be rejected with 400.
     */
    @Test
    @Tag("api") @Tag("regression") @Tag("edge")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("TC23 - Create book with blank/space title (edge)")
    @Description("Reject blank/space titles with 400. If accepted, product validation bug.")
    void createBook_blankTitle_shouldReturn400() {
        Book blankTitle = new Book(0, "   ", "Blank title", 100, "Excerpt", "2023-09-01T00:00:00Z");
        Response res = BooksClient.addBook(blankTitle);
        res.then().statusCode(400);
    }

    /**
     * [P1][Edge] TC27 - Create book with string in pageCount
     * Endpoint: POST /Books
     * Purpose: String pageCount must be rejected (400/422).
     * If allowed, product accepts invalid data.
     */
    @Test
    @Tag("api") @Tag("regression") @Tag("edge")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("TC27 - Create book with string in pageCount (edge)")
    @Description("String value for pageCount must be rejected. Otherwise, API validation bug.")
    void createBook_stringPageCount_shouldReturnError() {
        String invalidBody = "{\"id\":0,\"title\":\"Test\",\"description\":\"desc\",\"pageCount\":\"ten\",\"excerpt\":\"ex\",\"publishDate\":\"2023-09-01T00:00:00Z\"}";
        Response res = BooksClient.addBook(invalidBody);
        res.then().statusCode(anyOf(is(400), is(422)));
    }

    /**
     * [P1][Edge] TC33 - Send POST request with malformed JSON
     * Endpoint: POST /Books
     * Purpose: Invalid JSON body should return 400.
     */
    @Test
    @Tag("api") @Tag("regression") @Tag("edge")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("TC33 - Send POST request with malformed JSON (edge)")
    @Description("Malformed JSON must return 400. Otherwise, system is not handling invalid payload.")
    void createBook_malformedJson_shouldReturn400() {
        String malformed = "{\"id\":, \"title\":\"Missing value\"";
        Response res = BooksClient.addBook(malformed);
        res.then().statusCode(400);
    }

    // ---------------------------
    //         P2 TEST CASES
    // ---------------------------

    /**
     * [P2][Happy Path] TC06 - Get book by another valid ID
     * Endpoint: GET /Books/{id}
     * Purpose: Validate fetching by any valid ID works.
     * Wrong title returned means API data bug.
     */
    @Test
    @Tag("api") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("TC06 - Get book by another valid ID (happy path)")
    @Description("Creates another book, expects correct title on fetch. Fail means returned wrong data, must be reported.")
    void getBookByAnotherValidId_shouldReturnBook() {
        BooksClient.addBook(anotherBook);
        Response res = BooksClient.getBookById(anotherBook.getId());
        res.then().statusCode(200);
        assertThat(res.jsonPath().getString("title"), is(anotherBook.getTitle()));
    }

    /**
     * [P2][Happy Path] TC07 - Create new book (all valid fields)
     * Endpoint: POST /Books
     * Purpose: All fields must be accepted, 201 Created must be returned.
     */
    @Test
    @Tag("api") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("TC07 - Create new book (all valid fields) (happy path)")
    @Description("All valid fields should return 201 and correct data. Otherwise, creation logic bug.")
    void createBook_allFields_shouldReturnCreated() {
        Book b = new Book(103, "Test Driven API", "All fields test", 80, "Excerpt", "2023-01-01T00:00:00Z");
        Response res = BooksClient.addBook(b);
        res.then().statusCode(201);
        assertThat(res.jsonPath().getString("title"), is("Test Driven API"));
    }

    /**
     * [P2][Happy Path] TC08 - Update existing book with new data
     * Endpoint: PUT /Books/{id}
     * Purpose: Ensure updates reflect in API and return 200.
     */
    @Test
    @Tag("api") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("TC08 - Update existing book with new data (happy path)")
    @Description("Updates should reflect new data and return 200. Fail = update bug.")
    void updateBook_withNewData_shouldReturnUpdatedBook() {
        Book updated = new Book(103, "Updated Title", "Updated Desc", 90, "Updated Excerpt", "2023-01-01T00:00:00Z");
        Response res = BooksClient.updateBook(103, updated);
        res.then().statusCode(200);
        assertThat(res.jsonPath().getString("title"), is("Updated Title"));
    }

    /**
     * [P2][Happy Path] TC09 - Delete a different existing book
     * Endpoint: DELETE /Books/{id}
     * Purpose: Deleting any existing book should work.
     */
    @Test
    @Tag("api") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("TC09 - Delete a different existing book (happy path)")
    @Description("Deletes another book. Expects 200 or 204. Fail = delete bug.")
    void deleteAnotherBook_shouldReturnSuccess() {
        Response res = BooksClient.deleteBook(102);
        assertThat(res.statusCode(), anyOf(is(200), is(204)));
    }

    /**
     * [P2][Happy Path] TC10 - List all books after CRUD operations
     * Endpoint: GET /Books
     * Purpose: Book list should reflect CRUD changes.
     */
    @Test
    @Tag("api") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("TC10 - List all books after CRUD operations (happy path)")
    @Description("Book list should reflect all CRUD changes. Otherwise, list is stale (bug).")
    void listAllBooks_afterCrud_shouldBeUpToDate() {
        Response res = BooksClient.getAllBooks();
        res.then().statusCode(200);
        assertThat(res.jsonPath().getList("$"), is(not(empty())));
    }

    /**
     * [P2][Edge] TC12 - Get book by zero ID (boundary)
     * Endpoint: GET /Books/0
     * Purpose: System must handle boundary value (0) properly - expect 400 or 404.
     */
    @Test
    @Tag("api") @Tag("regression") @Tag("edge")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("TC12 - Get book by zero ID (boundary, edge)")
    @Description("Boundary value zero must return 400/404. Otherwise, backend not handling boundary.")
    void getBookByZeroId_shouldReturnError() {
        Response res = BooksClient.getBookById(0);
        res.then().statusCode(anyOf(is(400), is(404)));
    }

    /**
     * [P2][Edge] TC13 - Get book by negative ID (boundary)
     * Endpoint: GET /Books/-1
     * Purpose: Negative IDs must not be accepted.
     */
    @Test
    @Tag("api") @Tag("regression") @Tag("edge")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("TC13 - Get book by negative ID (boundary, edge)")
    @Description("Negative ID must be rejected with 400/404. Otherwise, validation bug.")
    void getBookByNegativeId_shouldReturnError() {
        Response res = BooksClient.getBookById(-1);
        res.then().statusCode(anyOf(is(400), is(404)));
    }

    /**
     * [P2][Edge] TC14 - Get book by string/alphanumeric ID
     * Endpoint: GET /Books/{id}
     * Purpose: API must reject string IDs with 400/422.
     */
    @Test
    @Tag("api") @Tag("regression") @Tag("edge")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("TC14 - Get book by string/alphanumeric ID (edge)")
    @Description("String/alphanumeric IDs must not be accepted. If accepted, validation missing (bug).")
    void getBookByStringId_shouldReturnError() {
        Response res = BooksClient.getBookById("abc123");
        res.then().statusCode(anyOf(is(400), is(422)));
    }

    /**
     * [P2][Edge] TC15 - Get book by special char ID
     * Endpoint: GET /Books/{id}
     * Purpose: Special chars as IDs must not be accepted.
     */
    @Test
    @Tag("api") @Tag("regression") @Tag("edge")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("TC15 - Get book by special char ID (edge)")
    @Description("Special char IDs must be rejected with 400/422. Otherwise, input validation bug.")
    void getBookBySpecialCharId_shouldReturnError() {
        Response res = BooksClient.getBookById("!@#%");
        res.then().statusCode(anyOf(is(400), is(422)));
    }

    /**
     * [P2][Edge] TC17 - Create book with empty body
     * Endpoint: POST /Books
     * Purpose: Creating with empty JSON body must return 400.
     */
    @Test
    @Tag("api") @Tag("regression") @Tag("edge")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("TC17 - Create book with empty body (edge)")
    @Description("Empty JSON body must return 400. Otherwise, validation bug.")
    void createBook_emptyBody_shouldReturn400() {
        Response res = BooksClient.addBook("{}");
        res.then().statusCode(400);
    }

        /**
     * [P2][Edge] TC19 - Create book with negative pageCount (boundary)
     * Endpoint: POST /Books
     * Purpose: Negative pageCount should not be accepted. 
     * If the API accepts negative numbers, this is a validation bug and must be reported.
     */
    @Test
    @Tag("api") @Tag("regression") @Tag("edge")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("TC19 - Create book with negative pageCount (edge)")
    @Description("Sends a book with negative pageCount. Expects 400 or 422. If not, validation is missing.")
    void createBook_negativePageCount_shouldReturnError() {
        Book b = new Book(0, "Negative PageCount", "Negative page", -5, "Excerpt", "2023-09-01T00:00:00Z");
        Response res = BooksClient.addBook(b);
        res.then().statusCode(anyOf(is(400), is(422)));
    }}