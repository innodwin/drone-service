# DroneDr-API

##Drones [test]

###Programming language
Java Spring Boot
### Database
H2
### Application Security
JWT

### Introduction

The application was developed using java spring with JWT enabled;  and with documentation using swagger and postman;
URL: http://localhost:8080/swagger-ui  .
Postman: https://www.getpostman.com/collections/612749715183e44f6d4f
The application runs on port 8080


---

### Task Modules
The application is devided into modules; from account creation to order;

### Authentication
- Authenticate user[api/v1/auth/login] this gives access / tokens for access to the other modules
- Register user [api/v1/auth/register] This endpoint register users
- Refresh Token[api/v1/auth/refreshtoken] This refresh the JWT Token

### Drone Modules
-	Register Drone[api/v1/drone/create] this endpoint registers drone based on provided details
-	Get All Drones Pager[api/v1/drone/droneData?page=0&per_page=10] This returns all registered drones with their status
-	Get Single Drone [api/v1/drone/droneData/1] get single drone details
-	Get Drone by State[api/v1/drone/droneData/1/1] This endpoint returns all drone details based on provided state Id
-	Check Available Drone[api/v1/drone/checkAvailability] This endpoint returns available drones for order placement
-	Check drone battery level [api/v1/drone/droneBattery/1] This endpoint provides all drones opened for placement
-	Update drone battery level [api/v1/drone/droneBattery/update] This endpoint updates the drone battery level
-	View All Drone battery level Audit trace[api/v1/audit/history?page=0&per_page=10] This endpoint returns all battery states
-	View Drone Battery record by drone ID [api/v1/audit/history/1]
-	Activate an order for delivery[api/v1/order/enable/1] This endpoint actives the drone for delivery mode of each item in the order pack
-	Activate Drone Idle state[api/v1/drone/changeState/1] This endpoint actives drone idle state after order returned flag

### Medication;products
-	Register [api/v1/medication/register] This endpoint registers a medication based on provided details
-	Flag delievery report[api/v1/product/delivered/2] This endpoint flag item in an order pack delievered
-	Get All medication[api/v1/medication]


### Product Order
-	Place an order [api/v1/order/register] This endPoint helps to place multiple order based on the provided details
-	Get All Order record [api/v1/order?page=0&per_page=10] This endpoint provides all order placed
-	Get Order By Drone Id [api/v1/order/drone?droneId=1] This endpoint returns all order placed by a particular drone
-	Get Order By Drone And State [api/v1/order/drone/1/2] This returns order by the criteria 

### Drone Mode
-	Get all Mode[api/v1/model] This endpoint returns all preloaded modes of drones


### Drone State
-	Get all state[api/v1/state] This endpoint returns all predloaded states of drones
