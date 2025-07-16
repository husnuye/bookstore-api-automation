package com.bookstore.data;

import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;

/**
 * Provides all POST/PUT body test data for Books API.
 * Arguments: id, title, description, pageCount, excerpt, publishDate, testCaseDesc, expectedStatus
 * Test cases are grouped by priority: P1 (must-have) first, P2 (should-have) below.
 */
public class TestDataProvider {

    public static Stream<Arguments> provideP1P2BookCases() {
        return Stream.concat(
                Stream.of(
                        // --- Priority: P1 ---

                        // TC02 - Create book, valid happy path
                        Arguments.of(5001, "TC02 Happy Path", "Valid Book", 120, "Excerpt", "2023-01-01T00:00:00Z",
                                "TC02 (P1): Create new book - all fields valid", 201),

                        // TC04 - Update book, valid
                        Arguments.of(5002, "TC04 Update", "Update Book", 130, "New excerpt", "2023-02-02T00:00:00Z",
                                "TC04 (P1): Update existing book - all fields valid", 200),

                        // TC16 - Create book missing required field (title)
                        Arguments.of(5003, null, "Missing title", 150, "Excerpt", "2022-03-01T00:00:00Z",
                                "TC16 (P1): Create book missing required title", 400),

                        // TC23 - Create book with blank/space title
                        Arguments.of(5004, "", "Blank title", 110, "Excerpt", "2023-04-04T00:00:00Z",
                                "TC23 (P1): Title empty string", 400),
                        Arguments.of(5005, "   ", "Title with spaces only", 110, "Excerpt", "2023-05-05T00:00:00Z",
                                "TC23 (P1): Title spaces only", 400),

                        // TC17 - Create book with empty body
                        Arguments.of(0, "", "", 0, "", "",
                                "TC17 (P1): All fields empty", 400),

                        // TC34 - All fields null
                        Arguments.of(0, null, null, 0, null, null,
                                "TC34 (P1): All fields null", 400)
                        // TC27, TC33 manuel JSON ile denenir
                ),
                Stream.of(
                        // --- Priority: P2 ---

                        // TC07 - Create new book, all fields valid
                        Arguments.of(5101, "TC07 All Valid", "All valid fields", 300, "Testing Excerpt", "2022-07-01T00:00:00Z",
                                "TC07 (P2): Create new book, all valid", 201),

                        // TC08 - Update existing book with new data
                        Arguments.of(5102, "TC08 Update", "New update data", 255, "Excerpt", "2022-08-01T00:00:00Z",
                                "TC08 (P2): Update with new data", 200),

                        // TC19 - Negative pageCount
                        Arguments.of(5103, "TC19 Negative", "pageCount < 0", -5, "Excerpt", "2022-09-01T00:00:00Z",
                                "TC19 (P2): Negative pageCount", 400),

                        // TC20 - pageCount = 0
                        Arguments.of(5104, "TC20 Zero", "pageCount = 0", 0, "Excerpt", "2022-10-01T00:00:00Z",
                                "TC20 (P2): pageCount zero", 400),

                        // TC24 - Invalid date format
                        Arguments.of(5105, "TC24 Date", "Invalid date format", 111, "Excerpt", "31-12-2024",
                                "TC24 (P2): Invalid publishDate", 400),

                        // TC26 - Duplicate ID (assume 5001 already exists)
                        Arguments.of(5001, "TC26 Duplicate", "Duplicate test", 200, "Excerpt", "2022-12-01T00:00:00Z",
                                "TC26 (P2): Duplicate ID", 409),

                        // TC28 - Update with non-existing ID
                        Arguments.of(99999, "TC28 NonExist", "Update non-existing", 100, "Excerpt", "2022-11-11T00:00:00Z",
                                "TC28 (P2): Update with non-existent ID", 404),

                        // TC29 - Update with empty body
                        Arguments.of(5106, "", "", 0, "", "",
                                "TC29 (P2): Update with empty body", 400),

                        // TC30 - Update with invalid fields
                        Arguments.of(5107, null, "Invalid title update", 100, "Excerpt", "2023-01-01T00:00:00Z",
                                "TC30 (P2): Null title update", 400),
                        Arguments.of(5108, "Invalid Field", "Negative pageCount update", -3, "Excerpt", "2023-02-01T00:00:00Z",
                                "TC30 (P2): Negative pageCount update", 400)
                )
        );
    }
}