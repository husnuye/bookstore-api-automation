/**
 * Covers:
 *  This suite automates the following Authors API test cases, grouped by priority:
 *    • P1 (Blocker): TC01, TC02, TC16, TC22
 *    • P2 (Critical): TC03, TC04, TC05
 *    • P3 (Normal): TC06, TC07, TC08, TC09
 * (See README for full priority breakdown.)
 */

package com.bookstore.tests;

import com.bookstore.api.AuthorsClient;
import com.bookstore.base.BaseTest;
import com.bookstore.model.Author;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import io.qameta.allure.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Epic("Authors API Automation")
@Feature("Authors Endpoints")
@DisplayName("Authors API E2E Test Suite (P1 + P2 + P3)")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthorsTest extends BaseTest {

    // --- Shared Test Data ---
    static int createdAuthorId;
    static Author validAuthor = new Author(7001, "Jane Doe", "Test author", "1990-01-01");
    static Author anotherAuthor = new Author(7002, "John Smith", "Another test author", "1985-05-12");

    // ---------------------------
    //      P1 - BLOCKER TESTS 
    // ---------------------------

    /**
     * [P1][Smoke][Happy] TC01 - Get all authors
     * Endpoint: GET /Authors
     * Purpose: Ensure endpoint returns 200 and a non-empty author list.
     * Steps: Send GET /Authors request.
     * Expected Result: HTTP 200, non-empty list of authors in response.
     * Notes: Release-blocker.
     */
    @Test @Order(1)
    @Tag("api") @Tag("smoke") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("TC01 - Get all authors (smoke, happy path)")
    void getAllAuthors_shouldReturnList() {
        Response res = AuthorsClient.getAllAuthors();
        res.then().statusCode(200);
        assertThat(res.jsonPath().getList("$"), is(not(empty())));
    }

    /**
     * [P1][Smoke][Happy] TC02 - Create new author (valid)
     * Endpoint: POST /Authors
     * Purpose: Validate author creation with valid fields.
     * Steps: Send POST /Authors with valid author body.
     * Expected Result: HTTP 201, author returned.
     * Notes: Release-blocker.
     */
    @Test @Order(2)
    @Tag("api") @Tag("smoke") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("TC02 - Create new author (smoke, happy path)")
    void createAuthor_shouldReturnCreated() {
        Response res = AuthorsClient.addAuthor(validAuthor);
        res.then().statusCode(201);
        createdAuthorId = res.jsonPath().getInt("id");
        assertThat(res.jsonPath().getString("name"), is(validAuthor.getName()));
    }

    /**
     * [P1][Smoke/Edge] TC16 - Create author missing required field (name)
     * Endpoint: POST /Authors
     * Purpose: API must not allow author creation without a name.
     * Steps: Send POST with missing "name" property.
     * Expected Result: HTTP 400 returned.
     * Notes: Release-blocker for validation.
     */
    @Test @Order(3)
    @Tag("api") @Tag("smoke") @Tag("regression") @Tag("edge")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("TC16 - Create author missing name (edge, smoke)")
    void createAuthor_missingName_shouldReturn400() {
        Author noName = new Author(0, null, "Bio", "1990-01-01");
        Response res = AuthorsClient.addAuthor(noName);
        res.then().statusCode(400);
    }

    /**
     * [P1][Smoke/Edge] TC22 - Create author with blank/space name
     * Endpoint: POST /Authors
     * Purpose: API must not allow blank or whitespace-only names.
     * Steps: Send POST with "name": "   "
     * Expected Result: HTTP 400 returned.
     * Notes: Release-blocker for input validation.
     */
    @Test @Order(4)
    @Tag("api") @Tag("smoke") @Tag("regression") @Tag("edge")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("TC22 - Create author with blank/space name (edge, smoke)")
    void createAuthor_blankName_shouldReturn400() {
        Author blankName = new Author(0, "   ", "Bio", "1990-01-01");
        Response res = AuthorsClient.addAuthor(blankName);
        res.then().statusCode(400);
    }

    // ---------------------------
    //      P2 - CRITICAL TESTS 
    // ---------------------------

    /**
     * [P2][Smoke][Happy] TC03 - Get author by ID (just created)
     * Endpoint: GET /Authors/{id}
     * Purpose: Fetch the just-created author by ID.
     * Steps: GET /Authors/{id} after creation.
     * Expected Result: HTTP 200 and correct author data.
     */
    @Test @Order(5)
    @Tag("api") @Tag("smoke") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("TC03 - Get author by ID (just created)")
    void getAuthorById_shouldReturnAuthor() {
        Response res = AuthorsClient.getAuthorById(createdAuthorId);
        res.then().statusCode(200);
        assertThat(res.jsonPath().getInt("id"), is(createdAuthorId));
        assertThat(res.jsonPath().getString("name"), is(validAuthor.getName()));
    }

    /**
     * [P2][Smoke][Happy] TC04 - Update author (valid)
     * Endpoint: PUT /Authors/{id}
     * Purpose: Update author and check new bio.
     * Steps: PUT with updated bio.
     * Expected Result: HTTP 200, updated author info returned.
     */
    @Test @Order(6)
    @Tag("api") @Tag("smoke") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("TC04 - Update author")
    void updateAuthor_shouldReturnUpdatedAuthor() {
        Author updated = new Author(createdAuthorId, validAuthor.getName(), "Updated bio", validAuthor.getBirthDate());
        Response res = AuthorsClient.updateAuthor(createdAuthorId, updated);
        res.then().statusCode(200);
        assertThat(res.jsonPath().getString("bio"), is("Updated bio"));
    }

    /**
     * [P2][Smoke][Happy] TC05 - Delete author
     * Endpoint: DELETE /Authors/{id}
     * Purpose: Delete just-created author.
     * Steps: DELETE by id after creation.
     * Expected Result: HTTP 200 or 204.
     */
    @Test @Order(7)
    @Tag("api") @Tag("smoke") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("TC05 - Delete author")
    void deleteAuthor_shouldReturnSuccess() {
        Response res = AuthorsClient.deleteAuthor(createdAuthorId);
        assertThat(res.statusCode(), anyOf(is(200), is(204)));
    }

    // ---------------------------
    //      P3 - NORMAL TESTS 
    // ---------------------------

    /**
     * [P3][Happy Path] TC06 - Get author by another valid ID
     * Endpoint: GET /Authors/{id}
     * Purpose: Ensure GET works for any valid author.
     * Steps: 1. Create another author. 2. GET /Authors/{id}
     * Expected Result: HTTP 200, correct author data.
     */
    @Test @Order(8)
    @Tag("api") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("TC06 - Get author by another valid ID (happy path)")
    void getAuthorByAnotherValidId_shouldReturnAuthor() {
        AuthorsClient.addAuthor(anotherAuthor);
        Response res = AuthorsClient.getAuthorById(anotherAuthor.getId());
        res.then().statusCode(200);
        assertThat(res.jsonPath().getString("name"), is(anotherAuthor.getName()));
    }

    /**
     * [P3][Happy Path] TC07 - Create new author (all valid fields)
     * Endpoint: POST /Authors
     * Purpose: API should accept all valid fields.
     * Steps: POST with all fields populated.
     * Expected Result: HTTP 201 and correct data in response.
     */
    @Test @Order(9)
    @Tag("api") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("TC07 - Create new author (all valid fields)")
    void createAuthor_allFields_shouldReturnCreated() {
        Author a = new Author(7003, "E2E Author", "Full fields", "1995-03-03");
        Response res = AuthorsClient.addAuthor(a);
        res.then().statusCode(201);
        assertThat(res.jsonPath().getString("name"), is("E2E Author"));
    }

    /**
     * [P3][Happy Path] TC08 - Update existing author with new data
     * Endpoint: PUT /Authors/{id}
     * Purpose: Should update any author with new data.
     * Steps: PUT with new name/bio.
     * Expected Result: HTTP 200 and updated fields in response.
     */
    @Test @Order(10)
    @Tag("api") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("TC08 - Update existing author with new data")
    void updateAuthor_withNewData_shouldReturnUpdatedAuthor() {
        Author updated = new Author(7003, "E2E Author Updated", "Updated", "1995-03-03");
        Response res = AuthorsClient.updateAuthor(7003, updated);
        res.then().statusCode(200);
        assertThat(res.jsonPath().getString("name"), is("E2E Author Updated"));
    }

    /**
     * [P3][Happy Path] TC09 - Delete a different existing author
     * Endpoint: DELETE /Authors/{id}
     * Purpose: Should delete any author by ID.
     * Steps: DELETE by ID for different author.
     * Expected Result: HTTP 200 or 204.
     */
    @Test @Order(11)
    @Tag("api") @Tag("regression") @Tag("happy")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("TC09 - Delete a different existing author")
    void deleteAnotherAuthor_shouldReturnSuccess() {
        Response res = AuthorsClient.deleteAuthor(anotherAuthor.getId());
        assertThat(res.statusCode(), anyOf(is(200), is(204)));
    }

}