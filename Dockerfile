# -------- BUILD STAGE --------
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

# Copy Maven wrapper files
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Give permission to mvnw
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src src

# Build Spring Boot application
RUN ./mvnw clean package -DskipTests


# -------- RUN STAGE --------
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy generated jar file
COPY --from=build /app/target/*.jar app.jar

# Railway / Render compatible port
ENV PORT=8080

# Expose application port
EXPOSE 8080

# Run application
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=$PORT"]