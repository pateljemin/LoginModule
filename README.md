# LoginModule
JWT based Authentication using Spring Boot.

  # Minds-Medical Login Module
-	JWT based authentication for Minds-Medical.

# Installation
- Install JDK.
- Install MongoDb and run mongod instance.
- Go to mongo client terminal and run the following commands.
```
$ use global
$ db.user.save({
             "firstName":"Jemin",
              "lastName":"Patel",
              "emailAddress":"pateljeminb@gmail.com",                                                      "password":"HWtiTwpqVdelISMLSSIkLQ==$XVM4OqNJ+pVSsDoxdC/e7c++OfTkM4twMXKRyaqo1LM="})
```





- This will create new Database with name “GLOBAL” and create a dummy user entry in USER collections.
- Here password is  "hello123" . In database it is stored as salt hashed.
- Deploy application with spring boot jar. It will run with embedded tomcat.
``` 
$ java -jar LoginModule-0.0.1.jar
```

- Token expiry time can be configured from application.properties file with param `jwt.expiryTimeInMinutes = 5` . Here token expiry time is 5 minutes.
- Token refresh limit is also configurable from application.properties file `jwt.refreshLimit = 47`. Total possible refresh count is 47.

# Uses cases

Login with valid username and password. On successful this will return JWT. This JWT will be used in subsequent requests.
 
 
```
curl -X POST \
  http://localhost:8080/api/token \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/x-www-form-urlencoded' \
  -d 'username=pateljeminb%40gmail.com&password=hello123'
```
 Output.
 ```
 eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJNaW5kcy1tZWRpY2FsIiwiYXVkIjoiTWluZHMtbWVkaWNhbCIsInN1YiI6InBhdGVsamVtaW5iQGdtYWlsLmNvbSIsInJlZnJlc2hDb3VudCI6MCwiaWF0IjoxNTIzMzgxMDUxLCJleHAiOjE1MjMzODEzNTF9.iJLl3v11gcTLQthYkWDArOqQ_ho0pPpAn1ECqqyDEG0
```
 
Create User with token.
```
curl -X POST \
  http://localhost:8080/api/users/ \
  -H 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJNaW5kcy1tZWRpY2FsIiwiYXVkIjoiTWluZHMtbWVkaWNhbCIsInN1YiI6InBhdGVsamVtaW5iQGdtYWlsLmNvbSIsInJlZnJlc2hDb3VudCI6MCwiaWF0IjoxNTIzMzgyNDk4LCJleHAiOjE1MjMzODI3OTh9.uqQIiIlDEblmRMijP3XKQhKXq81cIiXil4M7ZcN3YGg' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{
   "firstName":"Minds",
   "lastName":"Medical",
   "emailAddress":"minds@medical.com",
   "password":"hello123"	   
  }'
  ```
Output
```
{
    "id": "5accf94f02245b09a19b73cc",
    "firstName": "Minds",
    "lastName": "Medical",
    "emailAddress": "minds@medical.com",
    "password": "Seq0R1VLEKPk6xb2l63/ew==$mnQD0mgztE/WvxnFM4+aulJqJIfGxt4h9tSaKVk7hQM="
}
```
 Access User full name with Username.
 ```
 curl -X GET \
  http://localhost:8080/api/users/pateljeminb@gmail.com \
  -H 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJNaW5kcy1tZWRpY2FsIiwiYXVkIjoiTWluZHMtbWVkaWNhbCIsInN1YiI6InBhdGVsamVtaW5iQGdtYWlsLmNvbSIsInJlZnJlc2hDb3VudCI6MCwiaWF0IjoxNTIzMzgyNDk4LCJleHAiOjE1MjMzODI3OTh9.uqQIiIlDEblmRMijP3XKQhKXq81cIiXil4M7ZcN3YGg' \
  -H 'cache-control: no-cache' \
 ```
 Output
 ```
 JeminPatel
```
 
 Access medical reports.
 ```
 curl -X GET \
  http://localhost:8080/api/report \
  -H 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJNaW5kcy1tZWRpY2FsIiwiYXVkIjoiTWluZHMtbWVkaWNhbCIsInN1YiI6InBhdGVsamVtaW5iQGdtYWlsLmNvbSIsInJlZnJlc2hDb3VudCI6MCwiaWF0IjoxNTIzMzgyOTY0LCJleHAiOjE1MjMzODMyNjR9.DIln0uRvk7MFXa8ZknHTWgDpHIpLi9dMTZYP0M36SFU' \
  -H 'cache-control: no-cache' \
  ```
  
 Output
 ```
 My Report
 ```
 Refresh token API. When token is about to expire then client can call this api to refresh the token and token validity will be increased. New token is return in response.
 ```
 curl -X POST \
  http://localhost:8080/api/token/refresh \
  -H 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJNaW5kcy1tZWRpY2FsIiwiYXVkIjoiTWluZHMtbWVkaWNhbCIsInN1YiI6InBhdGVsamVtaW5iQGdtYWlsLmNvbSIsInJlZnJlc2hDb3VudCI6MCwiaWF0IjoxNTIzMzgzMjc5LCJleHAiOjE1MjMzODM1Nzl9.gHCvj50OjKEmBPkbugxWTTLpCtEOH2WUzNaPusxV5uw' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJNaW5kcy1tZWRpY2FsIiwiYXVkIjoiTWluZHMtbWVkaWNhbCIsInN1YiI6InBhdGVsamVtaW5iQGdtYWlsLmNvbSIsInJlZnJlc2hDb3VudCI6MCwiaWF0IjoxNTIzMzgzMjc5LCJleHAiOjE1MjMzODM1Nzl9.gHCvj50OjKEmBPkbugxWTTLpCtEOH2WUzNaPusxV5uw
  ```
  Output
  ```
  eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJNaW5kcy1tZWRpY2FsIiwiYXVkIjoiTWluZHMtbWVkaWNhbCIsInN1YiI6InBhdGVsamVtaW5iQGdtYWlsLmNvbSIsInJlZnJlc2hDb3VudCI6MSwiaWF0IjoxNTIzMzgzMzE0LCJleHAiOjE1MjMzODM2MTR9.N_UK6bgLgLbTROV1yRwVyLGgpuNiBE8d4AHTmbxet40
  ```
 
 Get User without authorization header or with invalid token. Response will be UNAUTHORIZED.
 ```
 curl -X GET \
  http://localhost:8080/api/users/pateljeminb@gmail.com \
  -H 'cache-control: no-cache' \
  ```
  Output
  ```
  {"timestamp":"2018-04-10T18:05:53.980+0000","status":401,"error":"Unauthorized","message":"Unauthorized","path":"/api/users/pateljeminb@gmail.com"}
  ```
  
 
