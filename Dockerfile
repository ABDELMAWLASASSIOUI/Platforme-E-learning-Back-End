# Use the official OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY myapp.jar /app/myapp.jar

# Expose the port on which the app will run (Change if needed)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "myapp.jar"]