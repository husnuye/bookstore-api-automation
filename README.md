# Bookstore API Automation Framework

A clean, scalable, enterprise-grade API automation framework for Books and Authors endpoints.  
**Tech stack:** Java 17, Maven, RestAssured, JUnit 5, Allure, Docker, Jenkins/GitHub Actions.

---

## 📁 Project Structure

bookstore-api-automation/
├── src/main/java/com/bookstore/api/        # API client classes (HTTP requests)
├── src/main/java/com/bookstore/model/      # Data models (POJO/DTO)
├── src/main/java/com/bookstore/utils/      # Utility classes (config, helpers)
├── src/test/java/com/bookstore/base/       # Base test class (common setup/teardown)
├── src/test/java/com/bookstore/tests/      # Test classes (grouped by API/resource)
├── src/test/java/com/bookstore/data/       # Test data providers (optional)
├── src/test/resources/                     # Config files, test data, etc.
├── pom.xml                                 # Maven build config
├── Dockerfile                              # Docker container support
├── .github/workflows/ci.yml                # GitHub Actions workflow (CI/CD)
├── README.md                               # Project documentation

---

**Quick Guide:**  
- **api/**: REST client logic (all HTTP calls to API endpoints)
- **model/**: Request/response objects (data contracts/DTOs)
- **utils/**: Utilities, config management, helpers
- **base/**: Common test setup/teardown for all tests
- **tests/**: Main test classes/scenarios, grouped by API/resource
- **data/**: Reusable test data and parameter providers (optional)
- **resources/**: Properties/config, static test data
- **CI/CD & Docker**: For automation, portability, and pipeline support

> Each part is separated for maintainability and clarity. Easy for both juniors and experienced engineers to navigate and extend.


## How to Run the API Test Suite

### 1. Local Execution

- **Run All Tests:**
  ```sh
  mvn clean test

	•	(Optional) Update API endpoint by editing:
src/test/resources/config.properties

Viewing Allure Reports Locally
	•	Serve Interactive Report:

allure serve target/allure-results

	•	Generate Static HTML Report:

allure generate target/allure-results --clean -o test-report
open test-report/index.html   # Mac

(On Windows: Open test-report/index.html in your browser.)

2. Running with Docker
	•	This project ships with a ready-to-use Dockerfile (Maven + JDK 17).
	•	The API base URL can be customized at runtime using the BASE_URL environment variable.
	•	The image will be built even if some tests fail, so you can always access the results for troubleshooting.
	
Build & Run:

docker build -t bookstore-api-automation .
docker run --rm \
  -v $(pwd)/target/allure-results:/project/target/allure-results \
  bookstore-api-automation

	•	The test results (allure-results) will be available in your local target/allure-results folder.

⸻

3. Continuous Integration (CI/CD)
	•	Every push or pull request runs the smoke test suite automatically (see .github/workflows/ci.yml).
	•	Full regression runs nightly at 3:00 AM UTC.
	•	Allure reports are saved as CI build artifacts and can be downloaded for review.

⸻

4. Test Tagging & Filtering
	•	You can run specific groups of tests, for example:
mvn test -Dgroups="smoke"
mvn test -Dgroups="regression"

	•	(Or use JUnit 5 tags with @Tag("smoke"), etc.)

⸻

5. Best Practices
	•	Codebase follows SOLID and clean code principles.
	•	Test cases are organized by resource and scenario type (happy path, edge case).
	•	Tags allow flexible suite execution.
	•	Configuration and test data are externalized for easy maintenance.
	•	API client, test logic, and utilities are kept separate for clarity.

⸻

Additional Notes
	•	Docker builds and Allure results are always generated, even if some tests fail.
	•	API environments can be switched quickly via config.properties or the BASE_URL environment variable.



Tagging & Coverage

## Tagging & Coverage

| Tag        | Purpose                                              |
|------------|-----------------------------------------------------|
| api        | All API endpoint tests                              |
| smoke      | Critical-path, fast regression (basic happy path)   |
| regression | Full functional suite (all happy/edge cases)        |
| happy      | Positive path test                                  |
| edge       | Negative/boundary/error case                        |



✅ Automated Books API Test Coverage

This project currently automates the following Books API test cases, grouped by priority:
	•	P1 (Blocker): TC01, TC02, TC16, TC23, TC27,TC33 
	•	P2 (Critical): TC03, TC04, TC05,TC11  
	.   P3 (Normal):  TC06, TC07, TC08, TC09, TC10,TC39  


Total: 16 automated critical test cases, including all smoke and main regression flows.

⸻

✅ Automated Authors API Test Coverage

This suite automates the following Authors API test cases:
	•	P1 (Blocker): TC01, TC02, TC16, TC22
	•	P2 (Critical): TC03, TC04, TC05
	•	P3 (Normal): TC06, TC07, TC08, TC09

Total: 11 automated test cases (all P1 and P2 covered, plus main P3 scenarios).

⸻
             |

| Priority         | Description                                               | Allure Severity | Example Issues                                      |
|------------------|----------------------------------------------------------|-----------------|-----------------------------------------------------|
| P1 (Blocker)     | Release cannot proceed. Total system or business outage. | blocker         | API down, login fails, cannot create/read core data |
| P2 (Critical)    | Major failure in core functionality, must fix to release.| critical        | Book not created, core workflow fails               |
| P3 (Normal/Major)| Important but not blocking main flow.                    | normal          | Validation errors, secondary features, alternate flow bugs |
| P4 (Minor)       | Minor issue, does not block release.                     | minor           | Small bug, edge case, optional field                |
| P5 (Trivial)     | Cosmetic only, no business impact.                       | trivial         | UI typo, whitespace, label misalignment             |


> Tags are used to organize and selectively run different suites (smoke, regression, etc.).
> Priority/Severity defines business impact and release/blocking status.


## Books API Test Suite
| TC   | Test Name                                         | Priority | Allure Severity | Tags                                | Steps / Summary                                 | Expected Result           |
|------|---------------------------------------------------|----------|-----------------|-------------------------------------|-------------------------------------------------|--------------------------|
| TC01 | Get all books                                     | P1       | blocker         | api, smoke, regression, happy       | GET /Books                                      | 200, non-empty list      |
| TC02 | Create new book                                   | P1       | blocker         | api, smoke, regression, happy       | POST /Books with valid body                     | 201, book created        |
| TC03 | Get book by ID (just created)                     | P2       | critical        | api, smoke, regression, happy       | GET /Books/{id} (new)                           | 200, correct book        |
| TC04 | Update book                                       | P2       | critical        | api, smoke, regression, happy       | PUT /Books/{id} for created book                | 200, updated book        |
| TC05 | Delete book                                       | P2       | critical        | api, smoke, regression, happy       | DELETE /Books/{id} for created book             | 200 or 204               |
| TC06 | Get book by another valid ID                      | P3       | normal          | api, regression, happy              | GET /Books/{id} (another valid)                 | 200, correct book        |
| TC07 | Create new book (all valid fields)                | P3       | normal          | api, regression, happy              | POST /Books with all fields                     | 201, book created        |
| TC08 | Update existing book with new data                | P3       | normal          | api, regression, happy              | PUT /Books/{id} updated title/desc              | 200, updated book        |
| TC09 | Delete a different existing book                  | P3       | normal          | api, regression, happy              | DELETE /Books/{id} for another book             | 200 or 204               |
| TC10 | List all books after CRUD                         | P3       | normal          | api, regression, happy              | GET /Books after CRUD ops                       | 200, up-to-date list     |
| TC11 | Get book by non-existing ID                       | P2       | critical        | api, regression, edge               | GET /Books/{id} (non-existent)                  | 404                      |
| TC12 | Get book by zero ID                               | P4       | minor           | api, regression, edge               | GET /Books/0                                    | 400 or 404               |
| TC13 | Get book by negative ID                           | P4       | minor           | api, regression, edge               | GET /Books/-1                                   | 400 or 404               |
| TC14 | Get book by string/alphanumeric ID                | P4       | minor           | api, regression, edge               | GET /Books/abc123                               | 400 or 422               |
| TC15 | Get book by special char ID                       | P4       | minor           | api, regression, edge               | GET /Books/!@#%                                 | 400 or 422               |
| TC16 | Create book missing required field (title)        | P1       | blocker         | api, smoke, regression, edge        | POST /Books missing title                       | 400                      |
| TC17 | Create book with empty body                       | P4       | minor           | api, regression, edge               | POST /Books with empty JSON                     | 400                      |
| TC18 | Create book with extra undefined property         | P5       | trivial         | api, regression, edge               | POST /Books with extra property                 | 201 or 400               |
| TC19 | Create book with negative pageCount               | P4       | minor           | api, regression, edge               | POST /Books pageCount: -5                       | 400 or 422               |
| TC20 | Create book with pageCount = 0                    | P4       | minor           | api, regression, edge               | POST /Books pageCount: 0                        | 400 or 422               |
| TC21 | Create book with very high pageCount              | P5       | trivial         | api, regression, edge               | POST /Books pageCount: 1_000_000                | 201 or 422               |
| TC22 | Create book with long string in title             | P5       | trivial         | api, regression, edge               | POST /Books title > 255 chars                   | 400 or 422               |
| TC23 | Create book with blank/space title                | P1       | blocker         | api, smoke, regression, edge        | POST /Books blank/space title                   | 400                      |
| TC24 | Create book with invalid date format              | P4       | minor           | api, regression, edge               | POST /Books publishDate invalid                 | 400 or 422               |
| TC25 | Create book with future publishDate               | P5       | trivial         | api, regression, edge               | POST /Books future publishDate                  | 201 or 400               |
| TC26 | Create book with duplicate ID                     | P4       | minor           | api, regression, edge               | POST /Books duplicate ID                        | 409 or 400               |
| TC27 | Create book with string in pageCount              | P1       | blocker         | api, smoke, regression, edge        | POST /Books pageCount as string ("ten")         | 400 or 422               |
| TC28 | Update book with non-existing ID                  | P4       | minor           | api, regression, edge               | PUT /Books/{id} non-existent                    | 404                      |
| TC29 | Update book with empty body                       | P4       | minor           | api, regression, edge               | PUT /Books/{id} empty body                      | 400                      |
| TC30 | Update book with invalid field value              | P4       | minor           | api, regression, edge               | PUT /Books/{id} invalid title/pageCount          | 400 or 422               |
| TC31 | Delete book with non-existing ID                  | P4       | minor           | api, regression, edge               | DELETE /Books/{id} non-existent                 | 404                      |
| TC32 | Delete book by zero/negative ID                   | P4       | minor           | api, regression, edge               | DELETE /Books/0 or /Books/-1                    | 400 or 404               |
| TC33 | Send POST request with malformed JSON             | P1       | blocker         | api, smoke, regression, edge        | POST /Books with malformed JSON                 | 400                      |
| TC34 | Create book with all fields null                  | P4       | minor           | api, regression, edge               | POST /Books all fields null                     | 400                      |
| TC35 | Simultaneous POSTs with same data (race/duplicate)| P5       | trivial         | api, regression, edge               | Two POSTs with same data simultaneously         | 409 or 400 for second    |
| TC36 | Rate limiting / Too many requests                 | P5       | trivial         | api, regression, edge               | 20+ rapid POST or GET requests                  | 429 or all succeed       |
| TC37 | Unsupported HTTP method                           | P5       | trivial         | api, regression, edge               | Use PUT/PATCH/OPTIONS on /Books                 | 405/501                  |
| TC38 | Large payload                                     | P5       | trivial         | api, regression, edge               | POST /Books very large body (1MB+)              | 413/400/201              |
| TC39 | Content-Type header validation                    | P3       | normal          | api, regression, happy              | Check Content-Type in response headers          | application/json         |
| TC40 | Invalid Accept header                             | P5       | trivial         | api, regression, edge               | GET /Books Accept: application/xml              | 406 or default json      |
| TC41 | XSS/SQL injection payload                         | P5       | trivial         | api, regression, edge               | POST /Books with XSS/SQL injection in title     | 400/422 or sanitized     |
| TC42 | Empty query params                                | P5       | trivial         | api, regression, edge               | GET /Books?title=&author=                       | 200/all or error         |
| TC43 | Case sensitivity (endpoint/field)                 | P5       | trivial         | api, regression, edge               | GET /api/v1/books or /api/v1/BOOKS              | 404 or valid response    |
| TC44 | Malformed URL / extra slash                       | P5       | trivial         | api, regression, edge               | GET /api/v1/Books// or /Books//123              | 404 or valid response    |
| TC45 | Unauthorized/Forbidden (if auth required)         | P5       | trivial         | api, regression, edge               | Access protected endpoint without token         | 401/403                  |    |
                                     
--## Test Case Priority Breakdown

## Test Case Priority Breakdown

| Priority | Count | Test Cases                                                                                                   |
|----------|-------|-------------------------------------------------------------------------------------------------------------|
| **P1**   | 6     | TC01, TC02, TC16, TC23, TC27, TC33                                                                          |
| **P2**   | 4     | TC03, TC04, TC05, TC11                                                                                      |
| **P3**   | 6     | TC06, TC07, TC08, TC09, TC10, TC39                                                                          |
| **P4**   | 15    | TC12, TC13, TC14, TC15, TC17, TC19, TC20, TC24, TC26, TC28, TC29, TC30, TC31, TC32, TC34                    |
| **P5**   | 14    | TC18, TC21, TC22, TC25, TC35, TC36, TC37, TC38, TC40, TC41, TC42, TC43, TC44, TC45                          |
| **TOTAL**| **45**|                                                                                                             |                                                                                    |

> **Legend:**  
> - **P1:** Blocker (Release stopper, must pass)  
> - **P2:** Critical (Core features, high risk)  
> - **P3:** Major/Normal (Regression, main flows)  
> - **P4:** Minor (Validation, edge, optional)  
> - **P5:** Trivial (Cosmetic, rare edge, exploratory)  








## Authors API Test Suite

| TC   | Test Name                                         | Priority | Allure Severity | Tags                                | Steps / Summary                                 | Expected Result           |
|------|---------------------------------------------------|----------|-----------------|-------------------------------------|-------------------------------------------------|--------------------------|
| TC01 | Get all authors                                   | P1       | blocker         | api, smoke, regression, happy       | GET /Authors                                    | 200, non-empty list      |
| TC02 | Create new author                                 | P1       | blocker         | api, smoke, regression, happy       | POST /Authors with valid body                   | 201, author created      |
| TC03 | Get author by ID (just created)                   | P2       | critical        | api, smoke, regression, happy       | GET /Authors/{id} (new)                         | 200, correct author      |
| TC04 | Update author                                     | P2       | critical        | api, smoke, regression, happy       | PUT /Authors/{id} for created author            | 200, updated author      |
| TC05 | Delete author                                     | P2       | critical        | api, smoke, regression, happy       | DELETE /Authors/{id} for created author         | 200 or 204               |
| TC06 | Get author by another valid ID                    | P3       | normal          | api, regression, happy              | GET /Authors/{id} (another valid)               | 200, correct author      |
| TC07 | Create new author (all valid fields)              | P3       | normal          | api, regression, happy              | POST /Authors with all fields                   | 201, author created      |
| TC08 | Update existing author with new data              | P3       | normal          | api, regression, happy              | PUT /Authors/{id} updated name/desc             | 200, updated author      |
| TC09 | Delete a different existing author                | P3       | normal          | api, regression, happy              | DELETE /Authors/{id} for another author         | 200 or 204               |
| TC10 | List all authors after CRUD                       | P3       | normal          | api, regression, happy              | GET /Authors after CRUD ops                     | 200, up-to-date list     |
| TC11 | Get author by non-existing ID                     | P2       | critical        | api, regression, edge               | GET /Authors/{id} (non-existent)                | 404                      |
| TC12 | Get author by zero ID                             | P4       | minor           | api, regression, edge               | GET /Authors/0                                  | 400 or 404               |
| TC13 | Get author by negative ID                         | P4       | minor           | api, regression, edge               | GET /Authors/-1                                 | 400 or 404               |
| TC14 | Get author by string/alphanumeric ID              | P4       | minor           | api, regression, edge               | GET /Authors/abc123                             | 400 or 422               |
| TC15 | Get author by special char ID                     | P4       | minor           | api, regression, edge               | GET /Authors/!@#%                               | 400 or 422               |
| TC16 | Create author missing required field (name)       | P1       | blocker         | api, smoke, regression, edge        | POST /Authors missing name                      | 400                      |
| TC17 | Create author with empty body                     | P4       | minor           | api, regression, edge               | POST /Authors with empty JSON                   | 400                      |
| TC18 | Create author with extra undefined property       | P5       | trivial         | api, regression, edge               | POST /Authors with extra property               | 201 or 400               |
| TC19 | Create author with negative id (boundary)         | P4       | minor           | api, regression, edge               | POST /Authors id: -5                            | 400 or 422               |
| TC20 | Create author with id = 0 (boundary)              | P4       | minor           | api, regression, edge               | POST /Authors id: 0                             | 400 or 422               |
| TC21 | Create author with long name (boundary)           | P5       | trivial         | api, regression, edge               | POST /Authors name > 255 chars                  | 400 or 422               |
| TC22 | Create author with blank/space name               | P1       | blocker         | api, smoke, regression, edge        | POST /Authors blank/space name                  | 400                      |
| TC23 | Create author with duplicate ID                   | P4       | minor           | api, regression, edge               | POST /Authors duplicate ID                      | 409 or 400               |


## Authors API Test Case Priority Breakdown

| Priority | Count | Test Cases                                                      |
|----------|-------|-----------------------------------------------------------------|
| **P1**   | 4     | TC01, TC02, TC16, TC22                                          |
| **P2**   | 3     | TC03, TC04, TC05                                                |
| **P3**   | 4     | TC06, TC07, TC08, TC09                                          |
| **P4**   | 7     | TC10, TC11, TC12, TC13, TC14, TC15, TC23                        |
| **P5**   | 5     | TC17, TC18, TC19, TC20, TC21                                    |
| **TOTAL**| **23**|                                                                 |



Note: Core Books API test suite is fully implemented, including all P1 and P2 test cases (smoke, happy, edge).  
Additional edge cases and Author endpoint tests can be easily added due to the scalable, tag-driven design of the framework.



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


