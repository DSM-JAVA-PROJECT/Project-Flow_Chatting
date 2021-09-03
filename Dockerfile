FROM openjdk:16-jdk
COPY ./*.jar app.jar
ENV MONGO_URL ${MONGO_URL}
ENV SECRET_KEY ${SECRET_KEY}
ENTRYPOINT ["java", "-jar", "/app.jar"]