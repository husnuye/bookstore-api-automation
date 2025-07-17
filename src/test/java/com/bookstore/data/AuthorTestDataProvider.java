package com.bookstore.data;

import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;

/**
 * Provides all POST/PUT body test data for Authors API.
 * Arguments: id, name, bio, birthDate, testCaseDesc, expectedStatus
 * Test cases are grouped by priority: P1 (must-have) first, P2 (should-have) below.
 */
public class AuthorTestDataProvider {

    public static Stream<Arguments> provideP1P2AuthorCases() {
        return Stream.concat(
            Stream.of(
                // --- Priority: P1 ---

                // TC02 - Create author, valid happy path
                Arguments.of(6001, "TC02 Happy Path", "Author bio", "1980-01-01",
                        "TC02 (P1): Create new author - all fields valid", 201),

                // TC16 - Create author missing required field (name)
                Arguments.of(6002, null, "No name field", "1990-02-02",
                        "TC16 (P1): Missing required name", 400),

                // TC22 - Create author with blank/space name
                Arguments.of(6003, "", "Blank name", "1985-05-05",
                        "TC22 (P1): Name is empty string", 400),
                Arguments.of(6004, "   ", "Name with spaces only", "1986-06-06",
                        "TC22 (P1): Name spaces only", 400)
            ),
            Stream.of(
                // --- Priority: P2 ---

                // TC07 - Create author, all valid fields
                Arguments.of(6101, "TC07 All Valid", "All valid fields", "1975-12-31",
                        "TC07 (P2): Create new author, all valid", 201),

                // TC08 - Update existing author with new data
                Arguments.of(6102, "TC08 Update", "Updated bio", "1970-03-15",
                        "TC08 (P2): Update with new data", 200),

                // TC19 - Negative id (should fail)
                Arguments.of(-1, "Negative ID", "Negative test", "1971-07-07",
                        "TC19 (P2): Negative id", 400),

                // TC20 - id = 0 (should fail)
                Arguments.of(0, "Zero ID", "Zero id test", "1972-08-08",
                        "TC20 (P2): id zero", 400),

                // TC23 - Duplicate ID (assume 6001 already exists)
                Arguments.of(6001, "TC23 Duplicate", "Duplicate ID test", "1973-09-09",
                        "TC23 (P2): Duplicate ID", 409)
            )
        );
    }
}