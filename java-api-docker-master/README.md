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
java -jar target/dockerBootDemo-0.0.1-SNAPSHOT.jar


Change your directory if you are in the root location and build the docker image and verify with the following commands
// change directory
cd docker
// build the image (the dot . in below command specifies the current directory to look for Dockerfile)
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
Now, we have the docker image and let's run the container and once it is up and running you can access the api at http://localhost:3000/name/myname.
Internal port inside container 8080 maps to external port of machine os 3000
// run the container
docker run -d -p 3000:8080 --name javaapi java-api
// list the container
docker ps
// logs
docker logs javaapi
// exec into running container
docker exec -it javaapi /bin/sh


=================================================================================================

PS D:\Java codes\proj\GitProjects\SpringBoot\java-api-docker-master\docker> docker build -t java-api-vik .
Sending build context to Docker daemon  19.42MB
Step 1/3 : FROM openjdk:8-jre-alpine
8-jre-alpine: Pulling from library/openjdk
e7c96db7181b: Pull complete
f910a506b6cb: Pull complete
b6abafe80f63: Pull complete
Digest: sha256:f362b165b870ef129cbe730f29065ff37399c0aa8bcab3e44b51c302938c9193
Status: Downloaded newer image for openjdk:8-jre-alpine
 ---> f7a292bbb70c
Step 2/3 : COPY echo-0.0.1-SNAPSHOT.jar /echo.war
 ---> 4ed6019d9977
Step 3/3 : CMD ["/usr/bin/java", "-jar", "/echo.war"]
 ---> Running in 7e5b55f8786f
Removing intermediate container 7e5b55f8786f
 ---> b38a1c90ef40
Successfully built b38a1c90ef40
Successfully tagged java-api-vik:latest
SECURITY WARNING: You are building a Docker image from Windows against a non-Windows Docker host. All files and directories added to build context will have '-rwxr-xr-x' permissions. It is recommended to double check and reset permissions for sensitive files and directories.
PS D:\Java codes\proj\GitProjects\SpringBoot\java-api-docker-master\docker> docker images
REPOSITORY          TAG                 IMAGE ID            CREATED              SIZE
java-api-vik        latest              b38a1c90ef40        About a minute ago   104MB
busybox             latest              22667f53682a        2 weeks ago          1.23MB
hello-world         latest              bf756fb1ae65        13 months ago        13.3kB
openjdk             8-jre-alpine        f7a292bbb70c        21 months ago        84.9MB
PS D:\Java codes\proj\GitProjects\SpringBoot\java-api-docker-master\docker> docker tag java-api-vik vikasuni007/java-api-vik
PS D:\Java codes\proj\GitProjects\SpringBoot\java-api-docker-master\docker> docker push vikasuni007/java-api-vik        The push refers to repository [docker.io/vikasuni007/java-api-vik]
32a5ce05edd4: Preparing
edd61588d126: Preparing
9b9b7f3d56a0: Preparing
f1b5933fe4b5: Preparing
denied: requested access to the resource is denied
PS D:\Java codes\proj\GitProjects\SpringBoot\java-api-docker-master\docker> docker push vikasuni007/java-api-vik
The push refers to repository [docker.io/vikasuni007/java-api-vik]
32a5ce05edd4: Pushed
edd61588d126: Mounted from library/openjdk
9b9b7f3d56a0: Mounted from library/openjdk
f1b5933fe4b5: Mounted from library/openjdk
latest: digest: sha256:dd7b0eefdda0699b31382aff86f0cb3c6ac75adb9fbd02b119805f6140e699ec size: 1159
PS D:\Java codes\proj\GitProjects\SpringBoot\java-api-docker-master\docker>

----

to stop-
docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                    NAMES
7a84e9abc38a        java-api-vik        "/usr/bin/java -jar …"   4 minutes ago       Up 4 minutes        0.0.0.0:3000->3000/tcp   javaapi

docker container stop 7a84e9abc38a          7a84e9abc38a
PS D:\Java codes\proj\GitProjects\SpringBoot\java-api-docker-master\docker> docker ps                                   CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES
PS D:\Java codes\proj\GitProjects\SpringBoot\java-api-docker-master\docker> docker run -dp 3000:8080 --name javaapi java-api-vik
C:\Program Files\Docker\Docker\resources\bin\docker.exe: Error response from daemon: Conflict. The container name "/javaapi" is already in use by container "7a84e9abc38a2455282bbf30d47f99e652bdb418d813cbc6751dbd2e58cca028". You have to remove (or rename) that container to be able to reuse that name.
See 'C:\Program Files\Docker\Docker\resources\bin\docker.exe run --help'.
PS D:\Java codes\proj\GitProjects\SpringBoot\java-api-docker-master\docker> docker container rm 7a84e9abc38a
7a84e9abc38a

to follow logs-
 docker logs javaapi --follow
 
to look whats inside an image-
docker run -it java-api-vik:1.0 sh


