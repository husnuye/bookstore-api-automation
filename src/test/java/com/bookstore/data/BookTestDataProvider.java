package com.bookstore.data;

import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;

/**
 * Provides POST/PUT test data for Books API (P1, P2, P3 cases only).
 * Arguments: id, title, description, pageCount, excerpt, publishDate, testCaseDesc, expectedStatus
 */
public class BookTestDataProvider {

    public static Stream<Arguments> provideP1P2P3BookCases() {
        return Stream.of(
            // ---- Priority: P1 ----

            // TC01 - (No body needed, GET /Books returns non-empty, handled in test class not provider)

            // TC02 - Create book, valid happy path [P1]
            Arguments.of(6001, "TC02 Happy Path", "Valid Book", 120, "Excerpt", "2023-01-01T00:00:00Z",
                    "TC02 (P1): Create new book - all fields valid", 201),

            // TC16 - Create book missing required field (title) [P1]
            Arguments.of(6002, null, "Missing title", 150, "Excerpt", "2022-03-01T00:00:00Z",
                    "TC16 (P1): Missing required title", 400),

            // TC23 - Create book with blank/space title [P1]
            Arguments.of(6003, "", "Blank title", 110, "Excerpt", "2023-04-04T00:00:00Z",
                    "TC23 (P1): Blank title (empty string)", 400),
            Arguments.of(6004, "   ", "Title with spaces only", 110, "Excerpt", "2023-05-05T00:00:00Z",
                    "TC23 (P1): Title spaces only", 400),

            // TC27 - Create book with string in pageCount [P1]
            // (Bu case için testte body JSON olarak gönderilmeli, parametre ile değil!)

            // TC33 - Send POST with malformed JSON [P1]
            // (Bu case için testte manuel JSON body verilecek)

            // ---- Priority: P2 ----

            // TC03 - Get book by ID (handled in test class, id param olarak kullanılır)
            // TC04 - Update book, valid [P2]
            Arguments.of(6005, "TC04 Update", "Update Book", 130, "New excerpt", "2023-02-02T00:00:00Z",
                    "TC04 (P2): Update existing book - all fields valid", 200),

            // TC05 - Delete book (handled in test class, id param olarak kullanılır)

            // TC11 - Get book by non-existing ID (handled in test class)

            // ---- Priority: P3 ----

            // TC06 - Get book by another valid ID (handled in test class)
            // TC07 - Create new book, all fields valid [P3]
            Arguments.of(6006, "TC07 All Valid", "All valid fields", 300, "Testing Excerpt", "2022-07-01T00:00:00Z",
                    "TC07 (P3): Create book, all fields valid", 201),

            // TC08 - Update existing book with new data [P3]
            Arguments.of(6007, "TC08 Update", "Updated data", 255, "Excerpt", "2022-08-01T00:00:00Z",
                    "TC08 (P3): Update with new data", 200)

            // TC09 - Delete another existing book (handled in test class)

            // TC10 - List all books after CRUD (handled in test class)

            // TC39 - Content-Type header validation (handled in test class, body gerekmez)
        );
    }
}