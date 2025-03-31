FROM maven:3.9.6-eclipse-temurin-22 AS builder

WORKDIR /app

COPY ../pom.xml ./
COPY ../src ./src

RUN --mount=type=cache,target=/root/.m2/repository \
    mvn clean package -DskipTests --no-transfer-progress

FROM eclipse-temurin:22-jre-alpine

WORKDIR /app

RUN adduser -D appuser

COPY --from=builder /app/target/*.jar app.jar

USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]