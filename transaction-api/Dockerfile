# Use the official Maven image to build the project
# --- Stage 1: Build the JAR file ---
FROM maven:3.9.2-eclipse-temurin-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# --- Stage 2: Run the JAR ---
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/transaction-api-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
