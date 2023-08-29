# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the executable JAR file into the container
COPY target/your-spring-boot-app.jar app.jar

# Expose the port that your application listens on
EXPOSE 8080

# Define the command to run your application
CMD ["java", "-jar", "app.jar"]
