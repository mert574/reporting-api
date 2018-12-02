# Reporting-API

Built with Maven, Spring Boot, HSQLDB.

## Usage

This app uses an embedded DB. Installing or setting up a DB server is not required. 

Just ```spring-boot:run``` and you're ready.

## Demo
[Heroku App](https://reporting-dashboard-mert.herokuapp.com)


### Notes

- To change default port (8080) or default API URL (sandbox), you can use ```/src/main/resources/application.yml```
- Current logging state is not default. You might want to change it with ```application.yml```
- For frontend source and notes, check out [Here](https://github.com/mert574/report-api-frontend).