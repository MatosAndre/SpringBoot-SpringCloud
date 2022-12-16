# Spring Microservices using Spring Cloud

[André Matos](https://www.linkedin.com/in/andre-matos98/)

- [Tools](#tools)
- [Currency Exchange Service](#ces)
- [Currency Conversion Service](#ccs)
- [Eureka Naming Server](#nms)
- [API Gateway](#api)
- [URLs](#url)

## Tools <a name="tools"></a>
* Java 11
* Spring Boot 2.7.6 & Spring Cloud 2021.0.5
  * Service Registry using Eureka Naming Server
  * Load Balancing with Spring Cloud LoadBalancer
  * API Gateway with Spring Cloud Gateway
  * Circuit Breaker with Resilience4j
  * Distributed Tracing with Zipkin

## Currency Exchange Service <a name="ces"></a>
* Gives information about the exchange rate from one currency to another
* Uses H2 memory to store several combinations of currency exchange
* Service Name: currency-exchange
* Implemented a simple Circuit Breaker Controller to explore:
  * @Retry - Retries x number of times to call an unresponsive API
  * @CircuitBreaker - Keeps the client safe and functional when the target server is failing / unresponsive
  * @RateLimiter - Protects the server from over loading by controlling throughput 
* All the requests are tracked via Zipkin 

## Currency Conversion Service <a name="ccs"></a>
* Responsible to convert a certain amount of currency A to currency B 
* Service Name: currency-conversion
* Implemented two separate methods for the same purpose:
  * calculateCurrencyConversion: uses ResponseEntity and RestTemplate to retrieve information from Currency Exchange Service
  * calculateCurrencyConversionFeign: uses Feign REST Client for Service Invocation
* All the requests are tracked via Zipkin 

## Eureka Naming Server <a name="nms"></a>
* Registers service name to avoid hardcoded IPs, ports of the different microservices
* Allows to dinamically distribute load between several instances of the same microservice

## API Gateway <a name="api"></a>
* Simple and effective way to route to APIs
* Provide cross cutting concerns:
  * Security
  * Monitoring/metrics
* Explored features
  * Match routes on any request atribute
  * Define Predicates and Filters
  * Integrate with Sptring Cloud Discovery Client
  * Path Rewriting   

## URLs <a name="url"></a>
#### Currency Exchange Service
- http://localhost:8000/currency-exchange/from/USD/to/INR

#### Currency Conversion Service
- http://localhost:8100/currency-conversion/from/USD/to/INR/quantity/10
- http://localhost:8100/currency-conversion-feign/from/USD/to/INR/quantity/10

#### Eureka
- http://localhost:8761/

#### Zipkin
- http://localhost:9411/

#### API GATEWAY
- http://localhost:8765/currency-exchange/from/USD/to/INR
- http://localhost:8765/currency-conversion/from/USD/to/INR/quantity/10
- http://localhost:8765/currency-conversion-feign/from/USD/to/INR/quantity/10
- http://localhost:8765/currency-conversion-new/from/USD/to/INR/quantity/10

#### Commands
```
docker run -p 9411:9411 openzipkin/zipkin:2.23
```
