FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/*.jar msbanque.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "msbanque.jar"]