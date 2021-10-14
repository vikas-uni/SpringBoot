# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine

# Copy war file
COPY /target/Boot-kafka-0.0.1-SNAPSHOT.jar /kafkaBootDemo.war

# run the app
CMD ["/usr/bin/java", "-jar", "/kafkaBootDemo.war"]
