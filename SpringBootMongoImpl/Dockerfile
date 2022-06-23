# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine

# Copy war file
COPY /target/SpringBootMongoImpl-0.0.1-SNAPSHOT.jar /mongoDemo.jar

# run the app
CMD ["/usr/bin/java", "-jar", "/mongoDemo.jar"]
