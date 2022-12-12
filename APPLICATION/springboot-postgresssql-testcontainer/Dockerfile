FROM openjdk:8
ADD target/springboot-mysql-testcontainer.jar springboot-mysql-testcontainer.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","springboot-mysql-testcontainer.jar"]