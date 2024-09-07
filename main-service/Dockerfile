FROM amazoncorretto:11-alpine-jdk
LABEL authors="Андрей"
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]