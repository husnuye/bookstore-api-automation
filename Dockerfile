FROM maven:3.9.6-eclipse-temurin-17


# Define proxy build args (useful if you build behind a proxy)
ARG http_proxy
ARG https_proxy
ARG HTTP_PROXY
ARG HTTPS_PROXY


WORKDIR /project
COPY . .

# Clear proxy settings to avoid network issues during build
ENV http_proxy="" \
    https_proxy="" \
    HTTP_PROXY="" \
    HTTPS_PROXY=""

# Base URL for tests
ENV BASE_URL=https://fakerestapi.azurewebsites.net/api/v1

# Install dependencies and CA certificates
RUN apt-get update \
 && apt-get install -y --no-install-recommends wget unzip ca-certificates \
 && rm -rf /var/lib/apt/lists/*

# Download and install Allure CLI
RUN wget https://github.com/allure-framework/allure2/releases/download/2.21.0/allure-2.21.0.zip \
 && unzip allure-2.21.0.zip -d /opt/allure \
 && ln -s /opt/allure/bin/allure /usr/local/bin/allure \
 && rm allure-2.21.0.zip

# Allow specifying a test group via build arg (default: all)
ARG TEST_GROUP=all

# Run tests and generate Allure report
RUN if [ "$TEST_GROUP" = "all" ]; then \
      mvn clean test -DbaseUrl=$BASE_URL -Dallure.results.directory=target/allure-results || true; \
    else \
      mvn clean test -Dgroups="$TEST_GROUP" -DbaseUrl=$BASE_URL -Dallure.results.directory=target/allure-results || true; \
    fi \
 && allure generate target/allure-results --clean -o target/allure-report || true

# Expose Allure report directory for artifact collection
VOLUME ["/project/target/allure-report"]

# Default: run tests automatically when the container starts
CMD ["mvn", "clean", "test", "-DbaseUrl=https://fakerestapi.azurewebsites.net/api/v1", "-Dallure.results.directory=target/allure-results"]

