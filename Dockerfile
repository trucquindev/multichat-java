## syntax=docker/dockerfile:1
#
## ---------- Build stage ----------
#FROM eclipse-temurin:21 AS build
#WORKDIR /app
#COPY . .
#RUN chmod +x ./mvnw
#RUN ./mvnw clean package -DskipTests
#
## ---------- Runtime stage ----------
#FROM eclipse-temurin:21-jre
#WORKDIR /app
#COPY --from=build /app/target/*.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]
FROM openjdk:21-jdk-slim AS builder

# Cài maven để build app
RUN apt-get update && apt-get install -y maven

WORKDIR /app
COPY . .
RUN ./mvnw clean install -DskipTests

# ---

FROM openjdk:21-jdk-slim

# Cài nginx
RUN apt-get update && apt-get install -y nginx && apt-get clean

WORKDIR /app

# Copy built jar từ stage trước
COPY --from=builder /app/target/*.jar /app/

# Copy nginx config và script
COPY nginx.conf /etc/nginx/nginx.conf
COPY start.sh /app/start.sh

EXPOSE 8080

CMD ["bash", "/app/start.sh"]
