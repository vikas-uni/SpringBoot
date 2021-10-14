# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine

# Copy war file
COPY dockerBootDemo-0.0.1-SNAPSHOT.jar /dockerBootDemo.war

# run the app
CMD ["/usr/bin/java", "-jar", "/dockerBootDemo.war"]
