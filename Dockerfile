FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/meli-geolocation-0.0.1.jar
COPY ${JAR_FILE} meli-geolocation.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "meli-geolocation.jar"]