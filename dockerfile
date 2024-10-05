FROM openjdk:17-alpine
COPY target/lciii-scaffolding-0.0.1-SNAPSHOT.jar user-app.jar
ENTRYPOINT ["java", "-jar", "user-app.jar"]