FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/User-Management-0.0.1-SNAPSHOT.jar app.jar

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*


EXPOSE 8537

ENTRYPOINT ["java", "-jar", "/app/app.jar"]