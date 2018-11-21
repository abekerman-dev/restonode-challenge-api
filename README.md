# True North Sr Fullstack Challenge - Restonode 

## REST API component

This repo contains the RESTful API of the *Restonode* restaurants management system which is made up of two other components/repos:

1. [Order messaging service](https://github.com/abekerman-dev/restonode-challenge-order-messaging-service) 
2. [Front end](https://github.com/abekerman-dev/restonode-challenge-frontend)
    
The former handles incoming RabbitMQ messages sent through this API upon creation of restaurant delivery orders, whereas the latter is a website where customers can make their orders and restaurant owners can manage their businesses by adding meals to be delivered, all of these interactions being handled by this API.

## Running the whole *Restonode* system

As the *Restonode* restaurants management is divided up into three components/repos, in order for the system to run as a whole each component must be launched individually, explained right below.

> It's important to note that, although this API can be executed in isolation and accessed through an HTTP client such as [Postman](https://www.getpostman.com/), this usage doesn't make much sense from a functionality perspective - only as an API testing mechanism. If this is the intended use, [here is a Postman collection with the API endpoints and sample requests](https://www.getpostman.com/collections/480b4b5d3508b1d8a243).

So in order to run the whole *Restonode* system there are two alternatives:

1. "Manual" execution
2. With docker
    
> Of course, you'll first have to clone/download this repo and then follow either of the execution alternatives below.

> In either case, you'll have to set up [Google Maps Distance Matrix](https://developers.google.com/maps/documentation/distance-matrix/start) API key so that the application can work properly. See below for how to tackle it in both cases.

So let's explore these alternatives further:

### Running manually

First, clone or download this repo.

The first step is to export the Google Maps Distance Matrix API key as an environment variable so the application can look it up and use it.

> For security reasons the API key is not (and should not) be hardcoded as it would become part of a public repo, so the API key must be obtained somehow (e.g by email).

In order to export it, you can use the `.env.sample` file in the root of this repo like this: copy it to a new .env file (`cp .env_sample .env`) and replace the right side of the equal sign with the actual API key. Then, run `source .env`. That's it.

Next, we have to launch a RabbitMQ server instance. In order to achieve this, run `docker-compose -f docker-compose-rabbitMQ-only.yml up` and it will launch a RabbitMQ instance in your `localhost` which this API can send messages to.

Lastly, let's run the application by executing `mvn spring-boot:run`. Of course, [maven](https://maven.apache.org/) must be already installed.

In order to get the other two components up and running as well, please refer to each repo's *README* file where you'll find instructions on how to do that just like here.

### Running *with docker*

The three repos comprising *Restonode* are *docker-ready*, so it's highly advised getting the whole system to run through this method. How? Simple!

As with the manual execution, here we also need to set up the API key in the environment variable, except that we do it differently with docker: find the `DISTANCE_MATRIX_API_KEY` entry under `environment` in the `docker-compose.yml` file in the root of this repo and replace the right side of the equal sign with the actual API key.

Finally, run `docker-compose up` and it'll trigger the build of a maven image with this API code as a SpringBoot application to run on top of it, alongside a RabbitMQ image (later a service) which will be shared between this API and the order messaging service.

> Note: The first time it gets executed it'll take quite some time to download both RabbitMQ and maven/jdk images as well as the `pom.xml` dependencies, so be patient while it's doing its job!

#### The API is now up and running and can start serving the frontend and sending messages to the order messaging service.

## High-level architecture

### Code

This API is just a [Spring Boot](http://spring.io/projects/spring-boot) application set up using [Spring Initializr](https://start.spring.io/), hence most of the application's design/architecture was already decided (and simplified!) for us.

The API consists of the following core components:

1. A single `RestonodeController` class handling all the REST requests and responses (tied to an `ExceptionHandlerAdvice` class to help handle exceptions within the controller)

2. A single `RestonodeService` implementation handling all the API's business logic

3. Three repositories, each dealing with their respective model/POJO: `DeliveryOrderRepository`, `RestaurantRepository` and `MealRepository`

4. A `DistanceMatrixClient` interface implementation to query *Google Maps Distance Matrix API* about the ETA between two given points represented by their lat/lng float values. There is also a mock implementation for local/integration tests whose idea is to avoid making requests to the actual Google API.

5. A set of classes dealing with producing RabbitMQ messages which will be consumed by the order messaging service.

6. Finally, the model/POJO classes which make up the data model of the application.

### Database

The database is a plain H2 in-memory database which comes "out of the box" with Spring Initializr. No big changes should be made in order to have a more robust, production-ready DB engine in place of it (please see [here](#wishlist-db) for more details).

> The application creates initial sample data to test the API against, but this procedure should be modified for production-like environments: it can be either skipped (this can be easily achieved by commenting out - or deleting - the `StartupRunner` class) or further enhanced to set up real production data.

## Wishlist, a.k.a what's been left outside and I wish could sometime get in there (lack of time is to blame!)

There are a number of things that had to be left outside this application just because of lack of time, not desire:

1. When customers create a delivery order, it would be nice if they had the ability to indicate the number of meals they want for each meal they choose (not just one like it works now).

2. As a developer, I would've liked to use Spring Data JPA more sophisticated features than plain `@Query` annotations such as *specifications* or *QBE (Query By Example)*. That would've helped me gain a bit more knowledge about it for future use.

3. Although exceptions are somehow taken care of, I think a better job can be done especially facing the client side of the API when showing error messages. These could be wrapped in a JSON object with more detailed information than the current plain-text version.

4. Googling up it turns out that, according to some, *constructor injection* has some advantages with respect to the "mainstream" `@Autowired` field injection - it's only I found out too late down the road to be able to try it :( so next time I set out to write a Spring application, I'll do it right off the bat!

5. Gain deeper understanding on testing Spring applications (both unit & component testing) and using [Mockito](https://site.mockito.org/) - which, in my opinion, is a whole world in its own right!

6. <a name="wishlist-db"></a>Switch the DB engine to something more robust like MySQL or PostgreSQL. I admit I tried plugging in Spring Boot PostgreSQL dependency and it didn't work out upfront - I had authentication issues. I googled up a bit but the solution to it seemed non-trivial so I decided to move forward with the rest of the application trusting I could go back to it eventually - which unfortunately didn't happen :(

7. Have the API deployed somewhere in the cloud with e.g. AWS, CloudFoundry, etc.

## Author

* **Andrés Bekerman** - [GitHub](https://github.com/abekerman-dev)
