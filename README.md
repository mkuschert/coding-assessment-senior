# coding-assessment-senior



## Running the application in dev mode for testing

```shell script
./mvnw compile quarkus:dev
```

## Swagger documentation

A Swagger documentation is accessable at

http://localhost:8080/q/openapi

## api endpoints (unsecure)

Get list of transformed shoes:

http://localhost:8080/transformed-shoes


## user management (secured by basic auth)

following endpoints are accessable by users with role "admin"

Get user by username (GET):

http://localhost:8080/user/{username}

Create new user (POST):

http://localhost:8080/user

Update user (PUT):

http://localhost:8080/user/{username}

Update password (PUT)

http://localhost:8080/user/{username}/password


Delete user (DELETE): 

http://localhost:8080/user/{username}

## initial users

Usename: admin
Password: password
Role: admin

Username: demo
Password: password123
Role: user 