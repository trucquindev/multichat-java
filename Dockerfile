FROM maven:3.9.6-eclipse-temurin-21 as builder

WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# -------------------

FROM openjdk:21-jdk-slim

# Cài nginx
RUN apt-get update && apt-get install -y nginx && apt-get clean

WORKDIR /app

# Copy file jar đã build từ stage builder
COPY --from=builder /app/target/*.jar /app/app.jar

# Copy nginx config và start script
COPY nginx.conf /etc/nginx/nginx.conf
COPY start.sh /app/start.sh

# Cấp quyền chạy cho script
RUN chmod +x /app/start.sh

EXPOSE 8080

CMD ["bash", "/app/start.sh"]
