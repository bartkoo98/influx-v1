FROM eclipse-temurin:17

WORKDIR /app

COPY target/influx-v1-0.0.1-SNAPSHOT.jar /app/influx-v1.jar

ENTRYPOINT ["java", "-jar", "influx-v1.jar"]
