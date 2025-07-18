# Bookstore API Automation Framework

A clean, scalable, enterprise-grade API automation framework for Books and Authors endpoints.

**Tech Stack:** Java 17, Maven, RestAssured, JUnit 5, Allure, Docker, GitHub Actions.

> Comprehensive end-to-end automated API testing for a sample Bookstore application.  
> Covers all critical, regression, and edge scenarios with modular, maintainable, and CI/CD-ready code.  
> Designed for rapid onboarding and easy extension by QA teams and backend developers.

---
## 📺 Demo Video

**👉 [Bookstore API Automation Demo (Vimeo)](https://vimeo.com/1102456987)**




## Table of Contents

1. [Project Structure](#project-structure)
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




2. [Features](#features)

	•	Modern Java Tech Stack:
Uses Java 17, Maven, RestAssured, JUnit 5, and Allure for robust and maintainable test automation.
	•	Modular & Scalable Structure:
Clean separation of API client, data models, test logic, and utilities for easy scaling and maintenance.
	•	Powerful API Coverage:
Covers both Books and Authors endpoints, with full support for CRUD, validation, edge, and regression tests.
	•	Test Prioritization & Tagging:
Test cases grouped by business priority (P1–P5), with JUnit tags for flexible suite execution (smoke, regression, happy, edge, etc).
	•	Allure Reporting:
Produces interactive, visual Allure test reports with rich metadata (severity, steps, attachments, etc).
	•	Docker-Ready:
Includes a Dockerfile for fully isolated test runs—no local setup needed.
	•	CI/CD Integration:
GitHub Actions support for automated runs on every push/PR; nightly full regression; Allure results archived as build artifacts.
	•	Configurable & Extensible:
API base URL, test data, and config are externalized for quick environment switching and easier maintenance.
	•	Best Practices by Default:
Enforces SOLID, DRY, and clean code principles—easy for new contributors and scales for large teams.


3. [Getting Started](#getting-started)

# 1. Clone the repository
git clone https://github.com/husnuye/bookstore-api-automation.git
cd bookstore-api-automation

# 2. Install prerequisites
#    (Java 17+, Maven 3.8+, optionally Docker & Allure CLI)

# 3. Build the project
mvn clean install

# 4. (Optional) Update API endpoint in:
#    src/test/resources/config.properties
#    or set BASE_URL as env variable (for Docker)

# 5. Run all tests
mvn test

# 6. View Allure report (interactive)
allure serve target/allure-results

#    or generate static HTML report
allure generate target/allure-results --clean -o test-report
open test-report/index.html   # On Mac
# (On Windows: open test-report/index.html in your browser)

# 7. Run with Docker
docker build -t bookstore-api-automation .
docker run --rm -v $(pwd)/target/allure-results:/project/target/allure-results bookstore-api-automation

# --- OPTIONAL: Push your local code to your own GitHub repository ---

# a) Create a new GitHub repo at https://github.com/new
# b) Link your local project to your GitHub repo:
git remote add origin https://github.com/<your-username>/<your-repo>.git

# c) Stage and commit your changes:
git add .
git commit -m "Initial commit: Setup Bookstore API automation framework"

# d) Push your changes to GitHub (main branch, or your branch name):
git push -u origin main

# e) For updates, pull remote changes before pushing again:
git pull origin main

# f) If working on a branch:
git checkout -b feature/my-feature
git push -u origin feature/my-feature

# g) Open a pull request on GitHub to merge your branch (if using feature branches).
4. [Running the Test Suite](#running-the-test-suite)

# Run the full test suite locally with Maven:
mvn clean test

# (Optional) To run specific groups using JUnit tags:
mvn test -Dgroups="smoke"
mvn test -Dgroups="regression"
# or
mvn test -Dgroups="happy"
mvn test -Dgroups="edge"

# View the Allure report interactively:
allure serve target/allure-results

# Or generate a static HTML report and open it:
allure generate target/allure-results --clean -o test-report
open test-report/index.html   # (For Mac)
# On Windows: Double-click test-report/index.html to open in browser

# Run the tests inside Docker:
docker build -t bookstore-api-automation .
docker run --rm -v $(pwd)/target/allure-results:/project/target/allure-results bookstore-api-automation

# (After Docker run, view the allure-results as above.)

5. [Viewing Allure Reports](#viewing-allure-reports)

   ##  Viewing Allure Reports

You can view the latest test execution report here:  
👉 [Allure Live Report (GitHub Pages)](https://husnuye.github.io/bookstore-api-automation/)

# After running the test suite, you can generate and view Allure reports as follows:

# 1. To view an interactive report in your browser (recommended for local exploration):
allure serve target/allure-results

# 2. To generate a static HTML report (for sharing or storing in CI):
allure generate target/allure-results --clean -o test-report

# 3. Open the generated report:
open test-report/index.html      # (Mac)
# On Windows: Double-click test-report/index.html to open in your browser.

# Note:
# - The "allure-results" directory is automatically populated after each test run.
# - If running tests via Docker, results are mapped to your local "target/allure-results" folder for easy access.
# - In CI pipelines (GitHub Actions, Jenkins, etc.), the report can be archived or published as a build artifact.


6. [Test Tagging & Filtering](#test-tagging--filtering)

# You can filter and run specific groups of tests using JUnit 5 tags.
# Example usages:

# 1. Run only smoke tests (critical path, fast checks):
mvn test -Dgroups="smoke"
# or using JUnit tags:
mvn test -Dtest=* -Dgroups=smoke

# 2. Run all regression tests (full functional suite):
mvn test -Dgroups="regression"

# 3. Run only happy path (positive) scenarios:
mvn test -Dgroups="happy"

# 4. Run only edge cases (negative, boundary):
mvn test -Dgroups="edge"

# 5. Combine tags for custom suites (example: all API regression and edge tests):
mvn test -Dgroups="api,regression,edge"

# Notes:
# - All test methods are tagged (e.g. @Tag("smoke"), @Tag("regression"), etc.) for flexible filtering.
# - You can create your own tag combinations as needed.
# - Use these filters in your local runs, CI pipelines, or Docker.



7. [Test Coverage & Prioritization](#test-coverage--prioritization)

> **Test Coverage & Prioritization**

This framework provides automated coverage for the most critical Books and Authors API scenarios, prioritized by business risk and impact. Test cases are grouped by priority according to industry standards and Allure reporting conventions:

- **P1 (Blocker):** Must-pass, release-stopper tests (e.g., core CRUD operations, mandatory validation)
- **P2 (Critical):** High-risk, core feature coverage (e.g., critical paths, high-impact errors)
- **P3 (Normal):** Main regression, business-as-usual flows (positive & negative, but not blocking)
- **P4 (Minor):** Validation, optional, or edge scenarios (not blocking for release)
- **P5 (Trivial):** Exploratory, UI/cosmetic, or rare edge cases

**Current Automated Test Coverage:**

_Books API:_
- **P1 (Blocker):** TC01, TC02, TC16, TC23, TC27, TC33
- **P2 (Critical):** TC03, TC04, TC05, TC11
- **P3 (Normal):** TC06, TC07, TC08, TC09, TC10, TC39  
- **Total Automated:** 16 high-priority scenarios (all critical and main regression flows covered)

_Authors API:_
- **P1 (Blocker):** TC01, TC02, TC16, TC22
- **P2 (Critical):** TC03, TC04, TC05
- **P3 (Normal):** TC06, TC07, TC08, TC09  
- **Total Automated:** 11 test cases (all P1 & P2, plus main P3 scenarios)

> Lower-priority (P4, P5) cases can be added easily thanks to the modular, tag-driven structure.

| Priority | Description             | Allure Severity | Example                          |
|----------|------------------------|-----------------|-----------------------------------|
| P1       | Blocker, must pass     | blocker         | API down, cannot create/read      |
| P2       | Critical, high risk    | critical        | Core workflow fails               |
| P3       | Normal (regression)    | normal          | Validation, main flows            |
| P4       | Minor                  | minor           | Edge, optional, boundary          |
| P5       | Trivial                | trivial         | Cosmetic, exploratory             |

*See README for detailed test case breakdown.*


8. [Test Scenarios](#test-scenarios)  

All key API flows, validation checks, and regression scenarios are automated and tracked below for both Books and Authors endpoints.  
The table summarizes test case names, priorities, Allure severity, tags, high-level steps, and expected results.  
(For full traceability and test documentation, refer to the codebase and Allure reports.)



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




9. [CI/CD Integration](#cicd-integration)  

##  CI/CD Integration

> The framework is designed for seamless integration with modern CI/CD pipelines:
>
> - **GitHub Actions:**  
>   - All pushes and pull requests automatically trigger the smoke suite (`.github/workflows/ci.yml`).
>   - Nightly full regression runs are scheduled (configurable).
>   - Allure reports are archived as build artifacts and can be downloaded after each run.
>

> - **Docker:**  
>   - Tests can be executed in isolated containers.
>   - Ensures clean, reproducible environments for every build.
>
> - **Environment Configuration:**  
>   - API endpoints and credentials are externalized in properties files and environment variables.
>
> _See `.github/workflows/ci.yml` and `Dockerfile` for ready-to-use templates and further details._


10. [Best Practices](#best-practices)  

##  Best Practices

> - Follows **SOLID** and clean code principles for all test and client code.
> - **Separation of concerns:**  
>   - API client, test logic, and test data are all in separate packages.
> - **Reusable code:**  
>   - Common setup/teardown is in a shared base class.
>   - Test data and configurations are externalized.
> - **Descriptive tagging:**  
>   - Uses tags for smoke, regression, happy, and edge cases.
> - **Maintainable structure:**  
>   - Project structure is modular, clear, and easy to extend.
> - **Reporting & Traceability:**  
>   - Allure reporting integrated for easy test traceability.
> - **Configurable environments:**  
>   - API URLs, credentials, and other settings can be quickly switched.
> - **Ready for CI/CD:**  
>   - Runs headless in Docker or any pipeline (no UI/IDE needed).
> - **Easy onboarding:**  
>   - Clear README and directory structure for fast onboarding.

11. [Contributing](#contributing)  

##  Contributing

> - **Pull requests are welcome!**  
>   - Please open an issue first to discuss any major changes.
> - **For bug fixes, feature suggestions, or documentation updates:**  
>   - Fork the repository, create a new branch, and submit a pull request.
> - **Follow the project’s code style and structure.**
> - **Write clear commit messages and comments.**
> - **All new features or test cases should include relevant unit or integration tests.**
> - For any questions, feel free to open an issue or reach out (see Contact below).


12. [Contact](#contact)
---
## Contact

 For any questions about this case study or the framework, please contact the project owner via GitHub issues or email.

---

## Test Results & API Limitations

**Note:**  
Some test cases may fail due to the limitations or inconsistent behavior of the public demo API (fakerestapi.com) used for automation.  
- For example, the API may return HTTP `200` instead of the standard `201` for resource creation, or not enforce validation rules for required fields.
- All assertions are implemented according to RESTful best practices and official documentation.
- These failures highlight discrepancies in the demo API, not issues with the test code.
- With a real, fully stateful production API, all tests are expected to pass.

See Allure Report for full details.

