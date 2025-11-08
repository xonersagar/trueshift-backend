# ===============================================
# Stage 1: Build the application using Maven and JDK
# ===============================================
FROM maven:3.9.5-eclipse-temurin-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the project files needed for the build (pom.xml first for dependency caching)
COPY pom.xml .
COPY src ./src
COPY .mvn ./.mvn
COPY mvnw .

# Execute the Maven build command to create the executable JAR
RUN ./mvnw clean package -DskipTests

# ===============================================
# Stage 2: Create the final, small runtime image
# ===============================================
# Use a lightweight Java Runtime Environment (JRE) for production
FROM eclipse-temurin:21-jre-alpine

# Set the working directory
WORKDIR /app

# Copy the built JAR from the 'build' stage into the final image
# The JAR name is typically <artifactId>-<version>.jar (e.g., trueshift-backend-0.0.1-SNAPSHOT.jar)
# The wildcard (*) copies the single JAR and renames it to app.jar
COPY --from=build /app/target/*.jar app.jar

# The command to run the application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]

# Note: Render uses its own internal port (10000) defined by an environment variable,
# but EXPOSE 8080 is standard practice for Spring Boot containers.
EXPOSE 8080