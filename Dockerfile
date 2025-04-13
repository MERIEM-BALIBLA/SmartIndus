## Use OpenJDK 17 Alpine as base image
#FROM openjdk:17-alpine
#
## Set the working directory in the container
#WORKDIR /app
#
## Add the JAR file to the container
#ADD ./target/SmartIndus-*.jar smartindus.jar
#
## Expose port 8031
#EXPOSE 8031
#
## Specify the command to run the application
#ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar",  "/app/smartindus.jar"]