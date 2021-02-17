# java-api-docker
This is an example project how to dockerize the java API project

https://medium.com/bb-tutorials-and-thoughts/how-to-dockerize-java-rest-api-3d55ad36b914

Once you clone the project and run the following commands to build the project
// clone the project
git clone https://github.com/bbachi/java-api-docker.git
// change the directory
cd java-api-docker
// clean and install
mvn clean install
// Run the application
java -jar target/echo-0.0.1-SNAPSHOT.jar


Change your directory if you are in the root location and build the docker image and verify with the following commands
// change directory
cd docker
// build the image
docker build -t java-api .
// list the image
docker images
// login into your registry (Docker Hub)
docker login
// tag the image
docker tag java-api <repository name>/java-api
// push the image
docker push <repository name>/java-api

Running The API on Docker
Now, we have the docker image and let's run the container and once it is up and running you can access the api at http://localhost:8080/name/myname.
// run the container
docker run -d -p 8080:8080 --name javaapi java-api
// list the container
docker ps
// logs
docker logs javaapi
// exec into running container
docker exec -it javaapi /bin/sh
