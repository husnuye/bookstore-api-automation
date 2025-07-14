# Bookstore API Automation Framework

A clean, scalable, enterprise-grade API automation framework for Books and Authors endpoints.  
**Tech stack:** Java 17, Maven, RestAssured, JUnit 5, Allure, Docker, Jenkins/GitHub Actions.

---

## Project Structure

bookstore-api-automation/
├── src/main/java/com/bookstore/api/        # API client classes
├── src/main/java/com/bookstore/model/      # Data models (POJO/DTO)
├── src/main/java/com/bookstore/utils/      # Utility classes
├── src/test/java/com/bookstore/base/       # Base test class
├── src/test/java/com/bookstore/tests/      # Test classes
├── src/test/java/com/bookstore/data/       # Test data providers (optional)
├── src/test/resources/                     # config.properties, testdata.json
├── pom.xml                                 # Maven build file
├── Dockerfile                              # Docker containerization
├── Jenkinsfile                             # Jenkins pipeline
├── .github/workflows/ci.yml                # GitHub Actions pipeline
├── README.md                               # This file

---

## How to Run

**Locally:**
```sh
mvn clean test

	•	Edit src/test/resources/config.properties for the API base URL if needed.

Allure Report:

allure serve target/allure-results

or
allure generate target/allure-results --clean -o test-report
open test-report/index.html

Docker:

docker build -t bookstore-api-automation .
docker run --rm bookstore-api-automation

CI/CD:
	•	Tests run automatically on every push/PR in Jenkins/GitHub Actions.
	•	Allure report is uploaded as build artifact.

Tagging & Coverage

## Tagging & Coverage

| Tag        | Purpose                                              |
|------------|-----------------------------------------------------|
| api        | All API endpoint tests                              |
| smoke      | Critical-path, fast regression (basic happy path)   |
| regression | Full functional suite (all happy/edge cases)        |
| happy      | Positive path test                                  |
| edge       | Negative/boundary/error case                        |


## Tagging & Coverage

| Tag        | Purpose                                              |
|------------|-----------------------------------------------------|
| api        | All API endpoint tests                              |
| smoke      | Critical-path, fast regression (basic happy path)   |
| regression | Full functional suite (all happy/edge cases)        |
| happy      | Positive path test                                  |
| edge       | Negative/boundary/error case                        |


## Books API Test Suite

| TC   | Test Name                                              | Type      | Tags                                | Steps / Summary                                   | Expected Result        |
|------|--------------------------------------------------------|-----------|-------------------------------------|---------------------------------------------------|-----------------------|
| S01  | Get all books (smoke, happy path)                      | Smoke     | api, smoke, regression, happy       | GET /Books                                        | 200, non-empty list   |
| S02  | Create new book (smoke, happy path)                    | Smoke     | api, smoke, regression, happy       | POST /Books with valid body                       | 201, book created     |
| S03  | Get book by ID (just created) (smoke, happy path)      | Smoke     | api, smoke, regression, happy       | GET /Books/{id} for just created book             | 200, correct book     |
| S04  | Update book (smoke, happy path)                        | Smoke     | api, smoke, regression, happy       | PUT /Books/{id} for created book                  | 200, book updated     |
| S05  | Delete book (smoke, happy path)                        | Smoke     | api, smoke, regression, happy       | DELETE /Books/{id} for created book               | 200 or 204            |
| TC06 | Get book by another valid ID (happy path)              | Happy     | api, regression, happy              | GET /Books/{id} with another valid ID             | 200, correct book     |
| TC07 | Create new book (all valid fields)                     | Happy     | api, regression, happy              | POST /Books with all fields filled                | 201, book created     |
| TC08 | Update existing book with new data                     | Happy     | api, regression, happy              | PUT /Books/{id} with updated title/desc           | 200, data updated     |
| TC09 | Delete a different existing book                       | Happy     | api, regression, happy              | DELETE /Books/{id} for existing book              | 200 or 204            |
| TC10 | List all books after CRUD operations                   | Happy     | api, regression, happy              | GET /Books                                        | 200, up-to-date list  |
| TC11 | Get book by non-existing ID                            | Edge      | api, regression, edge               | GET /Books/{id} with non-existent ID              | 404                   |
| TC12 | Get book by zero ID (boundary, negative)               | Edge      | api, regression, edge               | GET /Books/0                                      | 400 or 404            |
| TC13 | Get book by negative ID (boundary, negative)           | Edge      | api, regression, edge               | GET /Books/-1                                     | 400 or 404            |
| TC14 | Get book by string/alphanumeric ID                     | Edge      | api, regression, edge               | GET /Books/abc123                                 | 400 or 422            |
| TC15 | Get book by special char ID                            | Edge      | api, regression, edge               | GET /Books/!@#%                                   | 400 or 422            |
| TC16 | Create book missing required field (title)             | Edge      | api, regression, edge               | POST /Books missing title in body                 | 400                   |
| TC17 | Create book with empty body                            | Edge      | api, regression, edge               | POST /Books with empty JSON object                | 400                   |
| TC18 | Create book with extra undefined property              | Edge      | api, regression, edge               | POST /Books with extra field                      | 201 or 400            |
| TC19 | Create book with negative pageCount (boundary)         | Edge      | api, regression, edge               | POST /Books with pageCount: -5                    | 400 or 422            |
| TC20 | Create book with pageCount = 0 (boundary)              | Edge      | api, regression, edge               | POST /Books with pageCount: 0                     | 400 or 422            |
| TC21 | Create book with very high pageCount (boundary)        | Edge      | api, regression, edge               | POST /Books with pageCount: 1000000               | 201 or 422            |
| TC22 | Create book with long string in title (boundary)       | Edge      | api, regression, edge               | POST /Books with title > 255 chars                | 400 or 422            |
| TC23 | Create book with blank/space title                     | Edge      | api, regression, edge               | POST /Books with title: "" or "    "              | 400                   |
| TC24 | Create book with invalid date format                   | Edge      | api, regression, edge               | POST /Books with publishDate: "31-12-2024"        | 400 or 422            |
| TC25 | Create book with future publishDate (boundary)         | Edge      | api, regression, edge               | POST /Books with future publishDate               | 201 or 400            |
| TC26 | Create book with duplicate ID                          | Edge      | api, regression, edge               | POST /Books with existing book ID                 | 409 or 400            |
| TC27 | Create book with string in pageCount                   | Edge      | api, regression, edge               | POST /Books with pageCount: "ten"                 | 400 or 422            |
| TC28 | Update book with non-existing ID                       | Edge      | api, regression, edge               | PUT /Books/{id} with non-existent ID              | 404                   |
| TC29 | Update book with empty body                            | Edge      | api, regression, edge               | PUT /Books/{id} with empty body                   | 400                   |
| TC30 | Update book with invalid field value                   | Edge      | api, regression, edge               | PUT /Books/{id} with invalid title/pageCount      | 400 or 422            |
| TC31 | Delete book with non-existing ID                       | Edge      | api, regression, edge               | DELETE /Books/{id} with non-existent ID           | 404                   |
| TC32 | Delete book by zero/negative ID (boundary)             | Edge      | api, regression, edge               | DELETE /Books/0, DELETE /Books/-1                 | 400 or 404            |
| TC33 | Send POST request with malformed JSON                  | Edge      | api, regression, edge               | POST /Books with invalid JSON format              | 400                   |
| TC34 | Create book with all fields null                       | Edge      | api, regression, edge               | POST /Books with all fields set to null           | 400                   |
| TC35 | Simultaneous POSTs with same data (race/duplicate)     | Edge      | api, regression, edge               | Send two identical POSTs at same time             | 409 or 400 for second |

**Toplam test case (Books API):** **35**
- **Smoke:** 5
- **Ek happy:** 5
- **Edge/Negative/Boundary:** 25

---



Best Practices
	•	SOLID, maintainable, readable code
	•	Tests are organized by API/resource and by happy/edge case
	•	Tagging enables flexible suite execution and reporting
	•	Configuration and test data externalized
	•	Clean separation between API client (request/response), test code (assertions), and utilities


Contributing & Contact

Feel free to open an issue or submit a pull request.
For questions or support, contact: [your-email@example.com]


