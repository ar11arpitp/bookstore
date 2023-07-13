###########Image############
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
ADD pom.xml /app
COPY . /app
RUN mvn clean package

FROM openjdk:17-alpine
COPY --from=build /app/target/*.jar /opt/online-bookstore.jar
EXPOSE 8082
CMD ["java", "-jar", "/opt/online-bookstore.jar"]
