# Calendar Event Scheduling
This project is a Spring Boot application that provides a RESTful API for scheduling calendar events. Users can create busy slots, fetch events, find conflicts, and schedule events with other users.

## Features
- Create busy slots for individual users.
- Fetch events for a specific user or multiple users.
- Identify conflicting events for a user on a particular day.
- Find the most favorable upcoming empty slot for a set of users and a specific duration.
- Schedule events with a defined start and end time involving multiple users

## Prerequisites
- Java 11 or later
- Maven
- Docker

## Setup
1. Clone the repository
2. Navigate to project directory
3. Run the springboot application

## API Endpoints
Base url : `http://localhost:8080`
### Create Busy Slot

- **POST** `/busy`
- **Body:**
```json
{
 "userId": 1,
 "startTime": "2023-12-09T10:00:00",
 "endTime": "2023-12-09T11:00:00"
}
```

### Fetch User Events
-**GET** `/{userId}`

###  Fetch Conflict Events
-**GET** `/conflicts/{userId}?date=2023-12-01`

### Fetch Favourable Slot
-**POST** `/favaourable-slot`

-**Body:**
```json
{
  "organiserId": 1,
  "participantIds": [2, 3],
  "durationMinutes": 120
}
```
### Create Event with Users
-**POST** `/create-event`

-**Body:** 
```json
{
  "organiserId": 1,
  "participantIds": [2, 3],
  "startTime": "2023-12-10T15:00:00",
  "endTime": "2023-12-10T17:00:00"
}
```

## Testing
You can test the API endpoints using tools like Postman or cURL.
### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.0/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#web)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#using.devtools)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

