# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine

# Copy war file
COPY /target/Spring-Boot-Logback-0.0.1-SNAPSHOT.jar /dockerLogbackDemo.war

# run the app
CMD ["/usr/bin/java", "-jar", "/dockerLogbackDemo.war"]
