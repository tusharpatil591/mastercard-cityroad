FROM openjdk:11
ADD target/city-navigation-0.0.1-SNAPSHOT.jar city-navigation-0.0.1-SNAPSHOT.jar
EXPOSE 8034
ENTRYPOINT ["java","-jar","city-navigation-0.0.1-SNAPSHOT.jar"]