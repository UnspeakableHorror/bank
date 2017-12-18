# Bank
## Installation
 On the commandline:
* Clone with `git clone https://github.com/UnspeakableHorror/bank.git`
* After it completes `cd bank`
* Run `./gradlew build`
* You can find the resulting jar inside build/libs/bank-1.0-SNAPSHOT.jar
* Execute `cd ..` and `cd build/libs/`
* Start the application with ` JAVA_OPTS="-Dspring.profiles.active=local" ./bank-1.0-SNAPSHOT.jar`

## Endpoints
### GET http://localhost:8080/api/account/{accountId}
Returns account information. 

There are 5 accounts available, from 1 to 5.
* 1, Santander Río, Argentina, funds: $10000.0
* 2, Santander Río, Argentina, funds: $1.0
* 3, Deutsche Bank, GERMANY, funds: $0.0
* 4, Lehman Brothers, USA, funds: $1000000.0
* 5, Galicia, Argentina, funds: $100.0

### POST http://localhost:8080/api/transaction
#### Body example
```
{
      "originAccount": 1,
      "destinationAccount":2,
      "amount": 100.0
}
```

### GET http://localhost:8080/api/start
Generates random transactions for testing with RestTemplate, starting 4 threads with Runner objects.

WARNING: it runs as fast as possible generating a lot of transactions.

Uses get to make it easier to start and stop.

### GET http://localhost:8080/api/stop
Stops all the runners.

