# Stage 1: Build the backend app
FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app

# Copy only the backend folder content to build
COPY ProductCatalog/pom.xml ./pom.xml
COPY ProductCatalog/src ./src

# Build the backend jar
RUN mvn clean package -DskipTests

# Stage 2: Run the app
FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
