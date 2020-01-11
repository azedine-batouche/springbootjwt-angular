# springbootjwt-angular
===
#Stack
===


#How to use this code?
===
## I. app-server
===
1. Make sure you have database, java 1.8 
2. Clone this repository
```
git clone https://github.com/batouche-dev/springbootjwt-angular.git
```
3. Navigate into the folder app-serve
```
cd app-serve
```
4. Open app-serve folder with your IDE (Eclipse, InterlliJ,...)
5. Go to application.properties and configure datasource, username and password to connect into you database
```
spring.datasource.username=username
spring.datasource.password=username
```
> Note: We using liquibase in this project

6. run the project as java application.
7. url providers: 
```
BASE_URL = 'http://localhost:8080/api/
```
8. you can postman or others for your test.
===
## II. app-client
===
1. Navigate into the folder app-client
```
cd app-client
```
2. Install NPM dependencies
```
npm install
```
3. Run the project
```
ng serve
```
4. URL lunch:
```
localhost:4200
```






