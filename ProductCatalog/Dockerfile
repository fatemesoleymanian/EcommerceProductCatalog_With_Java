# Stage 1: Build the application using Maven
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Package the app
RUN mvn clean package -DskipTests

# Stage 2: Run the application using a minimal Java image
FROM eclipse-temurin:21-jdk-alpine

# App directory
WORKDIR /app

# Copy the built jar file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port Spring Boot runs on
EXPOSE 8080

# Default command
ENTRYPOINT ["java", "-jar", "app.jar"]