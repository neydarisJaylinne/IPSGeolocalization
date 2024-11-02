
# Meli Geolocalization Test

This application is a geolocation service built with Java and the Spring Framework. It allows users to obtain geolocation data based on a user-provided IP address and retrieve relevant usage statistics. A simple web page is provided to interact with the features of the service.


## Installation
To install and run the application, ensure you have Docker and Docker Compose installed on your machine. Then, run the following command:

```bash
docker-compose up -d --build
```
Once all the Docker images are up, you can access the application at: http://localhost:8080/.

Additionally, download the folder hosted in https://github.com/neydarisJaylinne/IPSGeolocalization/tree/master/src/main/resources/static and open the index.html file in your browser to use the visual interface.

If the Installation process fails Run Locally
## Run Locally

Clone the project

```bash
  git clone https://github.com/neydarisJaylinne/IPSGeolocalization.git
```
Go to intelliJ or your IDE and open the IPSGeolocalization folder

Go to src/main/java/com/example/demo/MeliApplication.java file, press rigth click and run MeliApplication.java file

![image](https://github.com/user-attachments/assets/e0954fa6-d8e0-4d10-9a52-95623dc010c1)


Go to src/main/resources/static/index.html, press rigth click and run index.html file

![image](https://github.com/user-attachments/assets/149009e2-d6d4-478c-b953-c48e6cad0df4)

## Local API Reference

#### Get ip info

```http
  GET /ips/${ip}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `ip`      | `string` | **Required**. Id of item to fetch |

#### Min distance
```http
  GET /statistics/min
```
#### Max distance
```http
  GET /statistics/max
```
#### Average distance
```http
  GET /statistics/average
```

## External API Reference

#### External APIs

- ipapi (https://ipapi.com/): Used to obtain geolocation data based on an IP address.
- fixer (https://fixer.io/): Employed to get exchange rates for a country's currency.


## Features

- Geolocation via a user-provided IP address.
- Usage statistics, including minimum, maximum, and average distance for the provided geolocation.
- Runs in Docker containers for easy deployment.
- SQL for persistent storage.



## Tech Stack

**Client:** Html, JS

**Server:** SQL, JAVA, SpringBoot, JPA, Docker, Maven

## Architecture

The application is structured into different layers to enhance separation of concerns, including Controllers, Services, Repositories, and Clients:
- Controllers: Responsible for handling user requests. They validate the request format, interact with services to obtain the required data, and return responses to users.
- Services: Act as intermediaries in the application. They contain the business logic and retrieve relevant information, formatting it appropriately for the controllers. Services can obtain data from external APIs through clients or communicate with repositories for data persistence and retrieval.
- Repositories: Tasked with persisting and retrieving data from the MySQL database.
- Clients: Handle requests to external APIs and return results in a simplified format to the services.
Each layer may contain multiple classes, depending on the application's specific concerns. For example, there may be a dedicated controller for IP requests and another for statistics.

## Additional Details
- A validation was added in the IPController to check that the IP value provided by the user has the appropriate formatting.
- The application includes an index.html file that serves as a frontend for this backend service, allowing users to utilize the IP geolocation and statistics features.
- This application is designed to be deployed with Docker. The docker-compose.yml file will set up the necessary containers (SQL and the app itself). The Dockerfile will build and deploy the application.
## Limitations
- The ipapi API does not provide all available information due to the use of a free plan, limiting access to data such as timezone and currency information.
- 	Workarounds have been implemented to obtain timezone and currency data using the country codes received from the API.
- 	The fixer API also has limitations under the free subscription, leading to the implementation of an indirect approach to obtain exchange rates.


