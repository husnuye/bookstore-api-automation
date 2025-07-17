
# Start from an official Maven + JDK 17 image
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /project

COPY . .

ENV BASE_URL=https://fakerestapi.azurewebsites.net/api/v1

# Even if tests fail during the build stage, let the image be created!
RUN mvn clean test -DbaseUrl=$BASE_URL -Dallure.results.directory=target/allure-results || true

# (Optional) If only build and test results are required, a production image can be created here.
# But for demo purposes, we can leave it as below.

CMD ["mvn", "test", "-DbaseUrl=$BASE_URL", "-Dallure.results.directory=target/allure-results"]