FROM eclipse-temurin:21-alpine AS builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package -Dmaven.test.skip=true

FROM eclipse-temurin:21-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar ./app.jar
ENTRYPOINT [ "java", "-jar", "app.jar" ]
