FROM maven:3.9.6-eclipse-temurin-17

WORKDIR /project

COPY . .

ENV BASE_URL=https://fakerestapi.azurewebsites.net/api/v1

# Install Allure CLI dependencies
RUN apt-get update && apt-get install -y wget unzip

# Download and install Allure CLI
RUN wget https://github.com/allure-framework/allure2/releases/download/2.21.0/allure-2.21.0.zip \
  && unzip allure-2.21.0.zip -d /opt/allure \
  && ln -s /opt/allure/bin/allure /usr/local/bin/allure

# Allow passing test group via build arg; default to all tests if not provided
ARG TEST_GROUP=all

# Run tests (with group if set), generate Allure report, always continue even if tests fail
RUN if [ "$TEST_GROUP" = "all" ]; then \
      mvn clean test -DbaseUrl=$BASE_URL -Dallure.results.directory=target/allure-results || true ; \
    else \
      mvn clean test -Dgroups="$TEST_GROUP" -DbaseUrl=$BASE_URL -Dallure.results.directory=target/allure-results || true ; \
    fi \
    && allure generate target/allure-results --clean -o target/allure-report || true

# Expose allure-report directory for artifact copy
VOLUME ["/project/target/allure-report"]

# Default command: keep container running if needed for debugging
CMD ["tail", "-f", "/dev/null"]
