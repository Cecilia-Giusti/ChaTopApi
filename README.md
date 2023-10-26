#  ChatopApi

Chatopapi is a project with essential dependencies such as Spring Boot, JPA, security, validation, and testing. It also uses libraries like SLF4J, Logback, S3 Transfer Manager, Swagger, MySQL Connector, Lombok, and JJWT.


## Description

ChâTop is developing an online portal to facilitate the connection between potential tenants and property owners for seasonal rentals. The initial phase will focus on the Basque coast region, with plans to expand coverage throughout France.

## ️ Settings

### Step 1 - Prerequistes :

Make sure the following softs are installed

- Java JDK 17
- Maven
- MySQL >= 8


### Step 2 - Database 
- Start MySql
- Create the BDD by importing the SQL script located in ./resources/script.sql
- Add in your properties :
  - spring.datasource.username: (username)
  - spring.datasource.password: (password)
  - spring.datasource.url : (url of database)

### Step 3 - Spring Security

For use JWT and create token, add in your properties :
jwt.secret= (secret code)

### Step 4 - AWS S3

- Create an accout AWS
- Use S3 service 
- Create a "compartiment" and an user with all permissions
- Add all variables in your properties : 
  - aws.accessKeyId = (id user)
  - aws.secretKey = (secret key user)
  - aws.region = (region of 'compartiment')
  - aws.s3.bucket= (name of "compartiment")


##  Run Locally
1.Clone the chatopapi repository:
```sh
git clone https://github.com/Cecilia-Giusti/chatopapi
```
2.Install the dependencies with one of the package managers listed below:
```bash
mvn install
```
3.Start the development mode:
```bash
java -jar app.jar
```

## Test 

Use Postman collection to test several routes. 
```bash
├── ressources
│   └── postman
│       ├── rental.postman_collection.json
```

## Swagger

Refer to Swagger to discover all available routes and view all API endpoints

Visible at : 

yourlocalhost:port/swagger-ui/index.html

