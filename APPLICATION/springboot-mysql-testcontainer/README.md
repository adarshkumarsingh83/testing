# springboot-mysql-testcontainer

---

# to list the local docker images
* $ docker images

# to pull the mysql image to the local
* $ docker pull mysql

# to run the mysql docker image
* $ docker run --name <mysql-instance-name> -e MYSQL_ROOT_PASSWORD=<root-user-ped> -e MYSQL_DATABASE=<mysql-db-name> -d mysql:<5.6]>
* $ docker run --name espark-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=espark -d mysql:5.6

# docker container listing
* $ docker container ls


### To build 
* mvn clean package 

