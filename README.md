## Introduction
This document outlines the details as well as best practices in developing spring boot based REST API application that evaluates if 2 cities are connected based on origin and destination inputs. This project also includes UNIT testSuite, Docker containerization to be used in cloud based container environments such as docker, kubernetes, pivotal cloud foundry etc.

This dcoument outlines 
> 1. Design Solution
> 2. Implementation
> 3. Test
> 4. Run
> 5. Docker Containerization to be run with Container engines


 
## Prerequisites
To run this application, below software installations are required

> 1. *Java* 11
> 2. *Maven* 

**NOTE** *To run Docker images, then Docker engine installation is required*
 
## Design Solution

> The Proposed solution is using Graph traversal mechanism to identify if a path exists between two vertices. 
> The solution has 2 parts
> 1. Read the input data from a file stored in classpath and load into an application scoped Map. The input filename is mentioned in resource file application.properties
> 2. By using the Breadth First Search(BFS) graph traversal algorithm, iteratively find if 2 cities are connected 


## Implementation
this sample application is a  maven project with implementations for below.

> 1. Application Exception handling
> 2. Unit test cases
> 3. Docker image creation to be used via docker engine or orchestrated via kubernetes

## Installation & Run
  
 1. To install and run goto the directory in which you want to install the project.
clone the github project via URL

```git
git clone https://github.com/tusharpatil591/mastercard-cityroad.git

```
 2. do  a maven clean install to build the source code
```maven
mvn clean install

```
3. The UNIT testsuite can be executed by
```maven
mvn test

```

4. Run the application using maven Spring Boot plugin
```maven
mvn spring-boot:run 
```
  OR if you are going to run the executable jar from the command line 

```command line
java -jar target/city-connections-0.0.1-SNAPSHOT.jar

```
 
## API Testing
The API can be tested using an REST API testing tools such as RESTClient, POSTMANetc. or as it is a HTTP GET operation it can also be tested via browser
 
```
'origin' and 'destination' query parameters can be of any case.

http://localhost:8080/connected?origin=NEW YORK&destination=newark

OR 

http://localhost:8080/connected?origin=boston&destination=newark
```

## Dockerization

the *DockerFile* is included as part of the project files, to create a docker image run
```docker
docker build -t <image name> .
```

to the run the docker image from docker console at port 8888
```docker
docker run -p 8888:8080 <image name>
```


**NOTE** *For illustration purposes the log4j logs are written to STD console log..*, but can be isolated and directed to respective file Appenders
