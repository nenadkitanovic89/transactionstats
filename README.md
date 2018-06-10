# TransactionStats

Requirements: JDK 8, Gradle 4.7

Build project with (in project root folder where the build.gradle is located):
```
./gradlew clean build
```
 
Start the application:
```
./gradlew bootRun
``` 
Sample requests:

  -- New transaction:
  ```
    curl -X POST http://localhost:8080/transactions -H 'Content-Type: application/json' -d '{"amount": 11.0,"timestamp": 1528650612289}'
```


  --Get stats:
  
  ```
  curl -X GET http://localhost:8080/statistics
  ```
