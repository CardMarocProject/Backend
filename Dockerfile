 
#install maven to run all commands build is just a name 
FROM maven:3.8.6 AS build
#directory that i will work in 
WORKDIR  /app
#copy the pom inside the app 
COPY pom.xml /app
#read the pom file and install all the dependencies
RUN mvn dependency:resolve
#copy the rest of the files insid the folder
COPY . /app
RUN mvn clean 
#run mvn package and skip all the tests and obtain an jar file 
RUN mvn package -DskipTests

FROM openjdk:17-jdk-alpine
COPY --from=build /app/target/*.jar app.jar
EXPOSE  8080
CMD  ["java","-jar","app.jar"]

