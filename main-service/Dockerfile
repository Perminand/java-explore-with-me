FROM amazoncorretto:21-alpine-jdk
LABEL authors="Андрей"
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]