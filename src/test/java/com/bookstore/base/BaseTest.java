package com.bookstore.base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import java.util.Properties;
import java.io.InputStream;

/**
 * Base test class.
 * Handles global test setup and configuration loading.
 * Extend this in all test classes to inherit setup logic.
 */
public abstract class BaseTest {
    protected static Properties properties = new Properties();

    /**
     * Loads configuration from config.properties and sets RestAssured baseURI.
     * Runs once before all tests.
     */
    @BeforeAll
    public static void setup() throws Exception {
        // Always use config file for environment settings; no hardcoded URLs!
        try (InputStream input = BaseTest.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
            RestAssured.baseURI = properties.getProperty("base.url");
        }
    }
}