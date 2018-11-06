# True North Sr Fullstack Challenge - Restonode 

## REST API component

This repo contains the RESTful API of the *Restonode* restaurants management system which is made up of two other components/repos:

1. [Order messaging service](https://github.com/abekerman-dev/truenorth-restonode-challenge-order-messaging-service) 
2. [Front end](https://github.com/abekerman-dev/truenorth-restonode-challenge-frontend)
    
The former handles incoming RabbitMQ messages sent through this API upon creation of restaurant delivery orders, whereas the latter is a website where customers can make their orders and restaurant owners can manage their businesses by adding meals to be delivered, all of these interactions being handled by this API.

## Running the whole *Restonode* system

As the *Restonode* restaurants management is divided up into three components/repos, in order for the system to run as a whole each component must be launched individually, explained right below.

> It's important to note that, although this API can be executed in isolation and accessed through an HTTP client such as Postman, this usage doesn't make much sense from a functionality perspective - only as an API test mechanism.

So in order to run the whole *Restonode* system there are two alternatives:

1. "Manual" execution
2. With docker
    
> Of course, you'll first have to clone/download this repo and then follow either of the execution alternatives below.

So let's explore these alternatives further:

### Running manually

This can be achieved simply by cloning/downloading this repo and executing `mvn spring-boot:run` ([maven](https://maven.apache.org/) must be already installed) but prior to this you'll have to make sure there's a RabbitMQ server instance running.

In order to achieve this, run `docker-compose -f docker-compose-rabbitMQ-only.yml up` and it will launch a RabbitMQ instance in your `localhost` which this API can send messages to.

In order to get the other two components up and running as well, please refer to each repo's *README* file where you'll find instructions on how to do that just like here.

### Running *with docker*

The three repos comprising *Restonode* are *docker-ready*, so it's highly advised getting the whole system to run through this method. How? Simple!

Just run `docker-compose up` and it'll trigger the build of a maven image with this API code as a SpringBoot application to run on top of it, alongside a RabbitMQ image (once built it'll become a service) which will be shared between this API and the order messaging service.

> Note: The first time it gets executed it'll take quite some time to download both RabbitMQ and maven/jdk images as well as the `pom.xml` dependencies, so be patient while it's doing its job!

#### The API is now up and running and can start serving the frontend and sending messages to the order messaging service.

## High-level architecture

### Code

This API is just a [Spring Boot](http://spring.io/projects/spring-boot) application set up using [Spring Initializr](https://start.spring.io/), hence most of the application's design/architecture was already decided (and simplified!) for us.

The API consists of the following core components:

1. A single `RestonodeController` class handling all the REST requests and responses (tied to an `ExceptionHandlerAdvice` class to help handle exceptions within the controller)

2. A single `RestonodeService` implementation handling all the API's business logic

3. Three repositories, each dealing with their respective model/POJO: `DeliveryOrderRepository`, `RestaurantRepository` and `MealRepository`

4. A `DistanceMatrixClient` interface implementation to query [Google Maps Distance Matrix API](https://developers.google.com/maps/documentation/distance-matrix/start) about the ETA between two given points represented by their lat/lng float values. There is also a mock implementation for local/integration tests whose idea is to avoid making requests to the actual Google API.

5. A set of classes dealing with producing RabbitMQ messages which will be consumed by the order messaging service.

6. Finally, the model/POJO classes which make up the data model of the application.

### Database

The database is a plain H2 in-memory database which comes "out of the box" with Spring Initializr. No big changes should be made in order to have a more robust, production-ready DB engine in place of it (please see [here](#wishlist-db) for more details)

## Wishlist, a.k.a what's been left outside (lack of time is to blame!)

### <a name="wishlist-db"></a>Database

## Author

* **Andrés Bekerman** - [GitHub](https://github.com/abekerman-dev)
