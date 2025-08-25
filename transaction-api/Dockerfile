# Use the official OpenJDK image
FROM openjdk:17-jdk-slim

# Set environment variables
ENV PORT=8080
EXPOSE 8080

# Copy project files
COPY target/transaction-api-*.jar app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app.jar"]
