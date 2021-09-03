FROM openjdk:16-jdk

COPY ./*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]