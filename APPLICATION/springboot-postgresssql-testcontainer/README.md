# springboot-postgresssql-testcontainer

---

# to list the local docker images
* $ docker images

# to pull the mysql image to the local
* $ docker pull postgres

# to run the postgress docker image
* $ docker run --name espark-postgress -e POSTGRES_USER=root -e POSTGRES_PASSWORD=root -e POSTGRES_DB=esparkdb -p 5432:5432 postgres

# docker container listing
* $ docker container ls


### To build 
* mvn clean package 



