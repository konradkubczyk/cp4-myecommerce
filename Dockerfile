FROM maven:3.9.4-eclipse-temurin-20-alpine AS build

WORKDIR /app

COPY . /app

RUN mvn clean install

FROM eclipse-temurin:20-jre-alpine

WORKDIR /app

COPY --from=build /app/target/myecommerce-*.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
