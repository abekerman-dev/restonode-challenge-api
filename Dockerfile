FROM maven:3.5.4-jdk-8-alpine
WORKDIR /usr/src/restonode-api
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests
WORKDIR /usr/bin/restonode-api
RUN cp /usr/src/restonode-api/target/*.jar ./app.jar
CMD ["java", "-jar", "app.jar"]