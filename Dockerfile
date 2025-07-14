# Use an official Maven + JDK 17 image as build
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /project

COPY . /project

RUN mvn clean test

# Optional: If you want to serve Allure report from inside container:
# RUN apt-get update && apt-get install -y allure
# RUN allure generate target/allure-results --clean -o /project/allure-report

# Set default command
CMD ["mvn", "clean", "test"]