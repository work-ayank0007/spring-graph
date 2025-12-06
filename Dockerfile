# Use OpenJDK 17 Alpine image for lightweight build
FROM eclipse-temurin:17-jdk-alpine

# Add a volume for temp files
VOLUME /tmp

# Copy truststore.jks to the container (needed for SSL connection)
COPY src/main/resources/truststore.jks /app/truststore.jks

# Copy the built jar file into the container
COPY target/demo-0.0.1-SNAPSHOT.jar /app/app.jar

# Set working directory
WORKDIR /app

# Run the jar with SSL truststore system properties
ENTRYPOINT ["java", "-Djavax.net.ssl.trustStore=/app/truststore.jks", "-Djavax.net.ssl.trustStorePassword=changeit", "-jar", "app.jar"]
