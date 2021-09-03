FROM openjdk:16-jdk

COPY /home/ubuntu/build/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]