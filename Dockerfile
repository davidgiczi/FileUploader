# syntax=docker/dockerfile:1

FROM alpine
FROM openjdk:11-jre
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY incoming-files ./incoming-files
ENTRYPOINT ["java","-jar","/app.jar"]