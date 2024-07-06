[![Java](https://img.shields.io/badge/Java-8%2B-orange)](https://www.oracle.com/java/)
![Lines of Code](https://img.shields.io/badge/lines_of_code-276-green)
![License](https://img.shields.io/badge/license-MIT-blue)

**Scheduler for Vacancies Bot** s a component of the Vacancies Bot application built on a microservices architecture. Its purpose is to enqueue messages to RabbitMQ indicating the number of vacancy pages to import.

## Features
- Connects to RabbitMQ to publish import tasks.
- Configurable to schedule tasks at specified intervals.
- Handles RabbitMQ connection errors gracefully.

## Requirements
- Java 8 or higher 
- RabbitMQ server running locally or accessible via network 
- Maven for building the project

## Installation
1. Clone the repository:
```shell
git clone https://github.com/kyljmeeski/vacancies-bot-scheduler.git
```
2. Navigate to the project directory:
```shell
cd vacancies-bot-scheduler 
```
3. Build the project using Maven:
```shell
mvn clean install
```

## Configuration
Modify the following parameters in Main.java to match your RabbitMQ setup:
```java
factory.setHost("localhost");
factory.setPort(5672);
```
Ensure the RabbitMQ server is configured with the appropriate exchanges and queues as specified in the code.

## Usage
1. Run the application:
```shell
java -jar vacancies-bot-scheduler.jar
```

