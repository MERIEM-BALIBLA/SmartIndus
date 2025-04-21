FROM eclipse-temurin:17-jdk-alpine

# Définir le timezone (ex: Europe/Paris)
RUN apk add --no-cache tzdata
ENV TZ=Europe/Paris

WORKDIR /app
COPY ./target/SmartIndus-0.0.1-SNAPSHOT.jar smartindus.jar

# Utiliser le port cohérent (8031 dans EXPOSE mais 8080 dans les logs)
EXPOSE 8031

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/smartindus.jar"]