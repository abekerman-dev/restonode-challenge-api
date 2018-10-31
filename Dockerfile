FROM openjdk:8-jre-alpine
WORKDIR /usr/src/restonode-api
COPY ./target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
