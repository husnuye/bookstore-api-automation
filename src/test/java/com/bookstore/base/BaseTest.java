package com.bookstore.base;

import com.bookstore.utils.ConfigReader;
import io.restassured.RestAssured;
import io.qameta.allure.restassured.AllureRestAssured; // Allure filter import
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

/**
 * <b>BaseTest</b> <br>
 * Abstract test base class for Bookstore API automation tests.<br>
 * <ul>
 *   <li>Loads configuration from config.properties (e.g., base URL)</li>
 *   <li>Sets RestAssured's baseURI for all HTTP requests</li>
 *   <li>Provides request/response spec reset for clean test state</li>
 *   <li>Configures AllureRestAssured filter for reporting</li>
 * </ul>
 * <p>
 * <b>Usage:</b> All API test classes should extend this class to inherit setup logic.
 * </p>
 */
public abstract class BaseTest {

    /**
     * Loads configuration, sets RestAssured base URI,
     * and enables AllureRestAssured filter for reporting.
     * This method runs once before all tests in the test class.
     */
    @BeforeAll
    public static void globalSetup() {
        // Set the base URL for all API requests
        RestAssured.baseURI = ConfigReader.get("base.url");
        // Enable Allure reporting for every request/response
        RestAssured.filters(new AllureRestAssured());
    }

    /**
     * Resets RestAssured's static request/response specifications before each test.
     * Guarantees test isolation and prevents cross-test state leakage.
     */
    @BeforeEach
    public void resetRestAssuredSpecs() {
        RestAssured.requestSpecification = null;
        RestAssured.responseSpecification = null;
    }
}