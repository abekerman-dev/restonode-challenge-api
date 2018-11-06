# True North Sr Fullstack Challenge - Restonode 

## REST API component

This repo contains the RESTful API of the *Restonode* restaurants management system which is made up of two other components/repos:

1. [Order messaging service](https://github.com/abekerman-dev/truenorth-restonode-challenge-order-messaging-service) 
2. [Front end](https://github.com/abekerman-dev/truenorth-restonode-challenge-frontend)
    
The former handles incoming RabbitMQ messages sent through this API upon creation of restaurant delivery orders, whereas the latter is a website where customers can make their orders and restaurant owners can manage their businesses by adding meals to be delivered, all of these interactions being handled by this API.

## Running the whole Restonode system

As the Restonode restaurants management is divided up into three components/repos, in order for the whole system to run there are two alternatives:

1. In isolation
2. With docker
    
Let's explore these possibilities further:

### Running *in isolation*

You'll find instructions on how to get the other two components up and running in their respective repos *README* file, so here we'll explain how to get this API up and running

This can be achieved simply by cloning/downloading this repo and executing `mvn spring-boot:run` but prior to this you'll have to make sure there's a RabbitMQ server instance running.

In order to achieve this, there's a `docker-compose-rabbitMQ-only.yml` file which you can start with `docker-compose up` and it will launch a RabbitMQ instance in your `localhost`.

### Running *with docker*

## Wishlist, a.k.a what's been left outside (lack of time is to blame!)

## Author

* **Andrés Bekerman** - [GitHub](https://github.com/abekerman-dev)
