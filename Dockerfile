# Используем официальный образ Maven для сборки
FROM maven:3-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn clean package -DskipTests

# Используем JDK образ для запуска
FROM openjdk:21
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Указываем порт, который будет открыт в контейнере
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
