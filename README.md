# How to Run:
* Install Java 21
  * sudo apt-get install default-jdk
* Install maven
  * sudo apt-get install maven
* Install Docker
  * docs.docker.com/engine/install
* Install Docker Compose v2
  * docs.docker.com/compose/install/linux/#install-using-the-repository
  * sudo apt-get install docker-compose-v2
* Run `docker compose up -d` to start containers in the background
* Run `mvn clean test` to build and run tests
  * docker compose must be running to run the tests because the end to end tests will store information in Redis
* The application is running on port 8080
* Make a GET request to `localhost:8080/api/v1/<githubUsername>`

# Trade-offs
To keep the project simple, I made the following choices that I wouldn't typically make:
* I chose not to create a custom exception handler and custom exceptions to manage the response status codes that are returned to the user
* I didn't do any input validation
  * Since this was a GET request with a url parameter, input validation seemed unnecessary in this case

# Architecture
* Redis for caching
  * This will help prevent rate limiting errors from the github apis
* Standard Controller/Service/Repository structure for a spring boot app
* Docker-compose to run the application and dependent service
  * This allows for a quick and easy start up with everything that's required