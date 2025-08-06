# Start from an official Maven + JDK 17 image
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /project

COPY . .

ENV BASE_URL=https://fakerestapi.azurewebsites.net/api/v1

# Install dependencies for Allure CLI
RUN apt-get update && apt-get install -y wget unzip

# Download and install Allure CLI
RUN wget https://github.com/allure-framework/allure2/releases/download/2.21.0/allure-2.21.0.zip && \
    unzip allure-2.21.0.zip -d /opt/allure && \
    ln -s /opt/allure/bin/allure /usr/local/bin/allure

# Run tests, generate allure results; even if tests fail, continue
RUN mvn clean test -DbaseUrl=$BASE_URL -Dallure.results.directory=target/allure-results || true

# Generate Allure report inside the container
RUN allure generate target/allure-results --clean -o target/allure-report

# Default command to run tests (if container run edilirse)
CMD ["mvn", "test", "-DbaseUrl=$BASE_URL", "-Dallure.results.directory=target/allure-results"]
