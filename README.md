# vehicle-service

The vehicle-servie microservice is the primary service to retrieves vehicles resource across all warehouse. 
With the help of this service you can retrieve list of all vehicles or single vehicle by vehicle_id and vehicle location details by vehicle_id

A vehicle resource holds the following properties:

 - id;
 - make;
 - model;
 - yearModel;
 - price;
 - licensed;
 - dateAdded;
 
 Additionaly vehicleDetails subreource holds location details of vehicle

 - warehouseId;
 - warehouseName;
 - warehouseLocationLatitude;
 - warehouseLocationLongitude;
 - carLocation;

# API first design
This api is build using api first design practices

## MediaType
 Resource supports json or xml format.

## Flat API
We decided to keep our API response as flat as possible. This is to limit the knowlegde needed by 
the consumer to consume the api. Since if there is structure to the resource returned that structure needs to be traversed to get the necessary data.


#Running a local workspace
The vehicle-service is based upon Spring Boot. 
Clone this service locally and import into IDE as a maven project
Build and run
You can change server port in properties file. 
