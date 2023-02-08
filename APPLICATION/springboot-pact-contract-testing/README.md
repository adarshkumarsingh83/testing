# SPRINGBOOT CONTRACT TESTING 


---
> server will have dsl file which contains the input and output and param and all details of the api exposed from the server 
> those will be help for the testing 

### Build the contract server application 
* mvn clean install 
> this will build the application and artifact will be stored in the .m2 directory of the maven 
> once artifact is stored in .m2 so that client for this can pull the artifact to run the stub server locally on the client side 
> it will download the stub and contract to validate against it 

### Run the server
* navigate inside the server application then execute the below cmd 
* mvn spring-boot:run 
> stub at the client side will only work when contract-server application is running 
> this will link the client and server via stub 


### Build the contract client application
* navigate inside the client application then execute the below cmd
* mvn clean package
> at the time of build the client test cases will be fired and validated against the contract at the server 
> and connect to the server via stub 

### Run the Client
* mvn spring-boot:run 


