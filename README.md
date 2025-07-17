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

### Locally
```sh
mvn clean test
# (Optional) Edit src/test/resources/config.properties for the API base URL if needed.
## How to Run


Allure Report (Local)
allure serve target/allure-results
# or
allure generate target/allure-results --clean -o test-report
open test-report/index.html

Docker
	•	The project includes a Dockerfile using Maven and JDK 17.
	•	API base URL can be overridden with the BASE_URL environment variable (default: FakeRestAPI).
	•	Docker image builds even if tests fail (for easier debugging).

Build and Run:

docker build -t bookstore-api-automation .
docker run --rm bookstore-api-automation

	•	Allure results will be in /project/target/allure-results inside the container.

CI/CD (GitHub Actions & Jenkins)
	•	Every push or PR to main triggers smoke tests for fast feedback.
	•	Every night at 3:00 AM UTC, the full regression suite runs automatically.
	•	Allure test results are uploaded as artifacts after each run, even if tests fail.
	•	See .github/workflows/ci.yml for full details.


Test Tagging Examples

mvn test -Dgroups="smoke"
mvn test -Dgroups="regression"

Best Practices
	•	SOLID, maintainable, readable code.
	•	Tests are organized by API/resource and by happy/edge case.
	•	Tagging enables flexible suite execution and reporting.
	•	Configuration and test data externalized.
	•	Clean separation between API client (request/response), test code (assertions), and utilities.

⸻

Contributing & Contact

Feel free to open an issue or submit a pull request.
For questions or support, open an issue or contact the maintainer.








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
                  |


## Books API Test Suite
| TC   | Test Name                                              | Type      | Priority | Tags                                | Steps / Summary                                   | Expected Result        |
|------|--------------------------------------------------------|-----------|----------|-------------------------------------|---------------------------------------------------|-----------------------|
| TC01 | Get all books                                          | Smoke     | P1       | api, smoke, regression, happy       | GET /Books                                        | 200, non-empty list   |
| TC02 | Create new book                                        | Smoke     | P1       | api, smoke, regression, happy       | POST /Books with valid body                       | 201, book created     |
| TC03 | Get book by ID (just created)                          | Smoke     | P1       | api, smoke, regression, happy       | GET /Books/{id} for just created book             | 200, correct book     |
| TC04 | Update book                                            | Smoke     | P1       | api, smoke, regression, happy       | PUT /Books/{id} for created book                  | 200, book updated     |
| TC05 | Delete book                                            | Smoke     | P1       | api, smoke, regression, happy       | DELETE /Books/{id} for created book               | 200 or 204            |
| TC06 | Get book by another valid ID                           | Happy     | P2       | api, regression, happy              | GET /Books/{id} with another valid ID             | 200, correct book     |
| TC07 | Create new book (all valid fields)                     | Happy     | P2       | api, regression, happy              | POST /Books with all fields filled                | 201, book created     |
| TC08 | Update existing book with new data                     | Happy     | P2       | api, regression, happy              | PUT /Books/{id} with updated title/description    | 200, data updated     |
| TC09 | Delete a different existing book                       | Happy     | P2       | api, regression, happy              | DELETE /Books/{id} for an existing book           | 200 or 204            |
| TC10 | List all books after CRUD operations                   | Happy     | P2       | api, regression, happy              | GET /Books after a set of CRUD operations         | 200, up-to-date list  |
| TC11 | Get book by non-existing ID                            | Edge      | P1       | api, regression, edge               | GET /Books/{id} with non-existent ID              | 404                   |
| TC12 | Get book by zero ID (boundary, negative)               | Edge      | P2       | api, regression, edge               | GET /Books/0                                      | 400 or 404            |
| TC13 | Get book by negative ID (boundary, negative)           | Edge      | P2       | api, regression, edge               | GET /Books/-1                                     | 400 or 404            |
| TC14 | Get book by string/alphanumeric ID                     | Edge      | P2       | api, regression, edge               | GET /Books/abc123                                 | 400 or 422            |
| TC15 | Get book by special char ID                            | Edge      | P2       | api, regression, edge               | GET /Books/!@#%                                   | 400 or 422            |
| TC16 | Create book missing required field (title)             | Edge      | P1       | api, regression, edge               | POST /Books with missing title in request body    | 400                   |
| TC17 | Create book with empty body                            | Edge      | P2       | api, regression, edge               | POST /Books with empty JSON object                | 400                   |
| TC18 | Create book with extra undefined property              | Edge      | P3       | api, regression, edge               | POST /Books with extra/undefined property         | 201 or 400            |
| TC19 | Create book with negative pageCount (boundary)         | Edge      | P2       | api, regression, edge               | POST /Books with pageCount: -5                    | 400 or 422            |
| TC20 | Create book with pageCount = 0 (boundary)              | Edge      | P2       | api, regression, edge               | POST /Books with pageCount: 0                     | 400 or 422            |
| TC21 | Create book with very high pageCount (boundary)        | Edge      | P3       | api, regression, edge               | POST /Books with pageCount: 1000000               | 201 or 422            |
| TC22 | Create book with long string in title (boundary)       | Edge      | P3       | api, regression, edge               | POST /Books with title longer than 255 characters | 400 or 422            |
| TC23 | Create book with blank/space title                     | Edge      | P1       | api, regression, edge               | POST /Books with title as "" or "    "            | 400                   |
| TC24 | Create book with invalid date format                   | Edge      | P2       | api, regression, edge               | POST /Books with publishDate: "31-12-2024"        | 400 or 422            |
| TC25 | Create book with future publishDate (boundary)         | Edge      | P3       | api, regression, edge               | POST /Books with publishDate in the future        | 201 or 400            |
| TC26 | Create book with duplicate ID                          | Edge      | P2       | api, regression, edge               | POST /Books with existing book ID                 | 409 or 400            |
| TC27 | Create book with string in pageCount                   | Edge      | P1       | api, regression, edge               | POST /Books with pageCount as a string ("ten")    | 400 or 422            |
| TC28 | Update book with non-existing ID                       | Edge      | P2       | api, regression, edge               | PUT /Books/{id} with non-existent ID              | 404                   |
| TC29 | Update book with empty body                            | Edge      | P2       | api, regression, edge               | PUT /Books/{id} with empty body                   | 400                   |
| TC30 | Update book with invalid field value                   | Edge      | P2       | api, regression, edge               | PUT /Books/{id} with invalid title/pageCount      | 400 or 422            |
| TC31 | Delete book with non-existing ID                       | Edge      | P2       | api, regression, edge               | DELETE /Books/{id} with non-existent ID           | 404                   |
| TC32 | Delete book by zero/negative ID (boundary)             | Edge      | P2       | api, regression, edge               | DELETE /Books/0 or /Books/-1                      | 400 or 404            |
| TC33 | Send POST request with malformed JSON                  | Edge      | P1       | api, regression, edge               | POST /Books with malformed JSON                   | 400                   |
| TC34 | Create book with all fields null                       | Edge      | P2       | api, regression, edge               | POST /Books with all fields set to null           | 400                   |
| TC35 | Simultaneous POSTs with same data (race/duplicate)     | Edge      | P3       | api, regression, edge               | Send two identical POST requests simultaneously   | 409 or 400 for second |
| TC36 | Rate limiting / Too many requests                      | Edge      | P3       | api, regression, edge               | Send 20+ rapid POST or GET /Books requests        | 429 or all succeed    |
| TC37 | Unsupported HTTP method                                | Edge      | P3       | api, regression, edge               | Use PUT/PATCH/OPTIONS on /Books endpoint          | 405/501               |
| TC38 | Large payload                                          | Edge      | P3       | api, regression, edge               | POST /Books with very large body (1MB+)           | 413/400/201           |
| TC39 | Content-Type header validation                         | Happy     | P2       | api, regression, happy              | Check Content-Type in response headers of all endpoints | application/json  |
| TC40 | Invalid Accept header                                  | Edge      | P3       | api, regression, edge               | GET /Books with Accept: application/xml           | 406 or default json   |
| TC41 | XSS/SQL injection payload                              | Edge      | P3       | api, regression, edge               | POST /Books with XSS or SQL injection in title    | 400/422 or sanitized  |
| TC42 | Empty query params                                     | Edge      | P3       | api, regression, edge               | GET /Books?title=&author= (empty query params)    | 200/all or error      |
| TC43 | Case sensitivity (endpoint/field)                      | Edge      | P3       | api, regression, edge               | GET /api/v1/books or /api/v1/BOOKS endpoints      | 404 or valid response |
| TC44 | Malformed URL / extra slash                            | Edge      | P3       | api, regression, edge               | GET /api/v1/Books// or /api/v1/Books//123         | 404 or valid response |
| TC45 | Unauthorized/Forbidden (if auth required)              | Edge      | P3       | api, regression, edge               | Access protected endpoint without token           | 401/403               |
                                     
--## Test Case Priority Breakdown

| Priority | Count | Test Cases                                                                                  |
|----------|-------|--------------------------------------------------------------------------------------------|
| **P1**   | 11    | TC01, TC02, TC03, TC04, TC05, TC11, TC16, TC23, TC27, TC33                                 |
| **P2**   | 20    | TC06, TC07, TC08, TC09, TC10, TC12, TC13, TC14, TC15, TC17, TC19, TC20, TC24, TC26, TC28, TC29, TC30, TC31, TC32, TC34 |
| **P3**   | 14    | TC18, TC21, TC22, TC25, TC35, TC36, TC37, TC38, TC39, TC40, TC41, TC42, TC43, TC44, TC45   |
| **TOTAL**| **45**|                                                                                            |

> _P1: Critical/smoke, P2: Regression/happy/edge, P3: Extended edge cases_-
> **Priority Legend:**
> - **P1 (Critical):** Must-pass before any release. Includes core business functionality and essential edge/negative cases.
> - **P2 (Major):** Important scenarios covering additional functionality, validation, and key error handling. Executed in every regression cycle.
> - **P3 (Nice-to-have):** Advanced, rarely encountered, or exploratory scenarios. Useful for robustness, but not release-blocking.

> **Type Legend:**
> - **Smoke:** Fast checks for core, business-critical workflows (health-check, main flows).
> - **Happy:** Positive scenarios with valid data reflecting expected/typical user behavior.
> - **Edge:** Negative, boundary, invalid, or error-prone scenarios, designed to test API robustness and error handling.


Best Practices
	•	SOLID, maintainable, readable code
	•	Tests are organized by API/resource and by happy/edge case
	•	Tagging enables flexible suite execution and reporting
	•	Configuration and test data externalized
	•	Clean separation between API client (request/response), test code (assertions), and utilities


Contributing & Contact

Feel free to open an issue or submit a pull request.
For questions or support, contact: [your-email@example.com]


