# Use the official Maven image to build the project
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Use a smaller JDK image to run the jar
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/transaction-api-*.jar app.jar

# Set environment variables
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
