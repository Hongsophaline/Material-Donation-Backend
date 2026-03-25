# Use stable Java 21 image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy project files
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src ./src

# Make mvnw executable
RUN chmod +x mvnw

# Build the app
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 8081

# Run the app
ENTRYPOINT ["java","-jar","target/demo-0.0.1-SNAPSHOT.jar"]