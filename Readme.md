# Foosball demo application

Demo REST web app for maintaining foosball scores. Requires JDK 11.

## Quick start

### 1. Create database
The app requires PostgreSQL database. Default database URL is `jdbc:postgresql://localhost:5432/foosball`
with user `postgres` and password `postgres`. These can be changed by setting environment variables
`DATASOURCE_URL`, `DATASOURCE_USERNAME` and `DATASOURCE_PASSWORD`. The app uses automatic migrations, so the DB schema will be created automatically.

### 2. Run the app

Run the app by running
```
./gradlew runBoot
```
(Or just `gradlew runBoot` on Windows.)

By default, the app runs on the local port 8080. This can be changed by setting the `server.port` environment
variable. 

## API

API description in the API Blueprint format (in Czech) can be found in the `apiary.apib` file or on 
http://foosball4.docs.apiary.io/ 

## Authentication

For simplicity, HTTP Basic authentication scheme is used. Actuator endpoints (`/actuator/health` etc.)
are accessible without authentication. For other resources, authorization HTTP header must be added
to the request. For simplicity, the user credentials are not stored in the database but hardcoded (username `user`,
password `test`). HTTP header can be generated on sites like
https://www.blitter.se/utils/basic-authentication-header-generator/, for the hardcoded user it's
`Authorization: Basic dXNlcjp0ZXN0`.

## Usage examples

These are some example requests using `curl` command:

### Create a player
```curl -v -H "Authorization: Basic dXNlcjp0ZXN0" -H "Content-Type: application/json" -X PUT -d "{\"nickname\": \"rick\"}" http://localhost:8080/players```

### List existing players with their wins and losses:
```curl -v -H "Authorization: Basic dXNlcjp0ZXN0" -H "Content-Type: application/json" http://localhost:8080/players```

### Get a particular player:
```curl -v -H "Authorization: Basic dXNlcjp0ZXN0" -H "Content-Type: application/json" http://localhost:8080/players/morty```

### Create a game:
```curl -v -H "Authorization: Basic dXNlcjp0ZXN0" -H "Content-Type: application/json" -X PUT -d "{\"teamA\":[\"john\", \"james\"], \"teamB\": [\"rick\", \"morty\"], \"winningTeam\": \"A\"}" http://localhost:8080/games```
All players must be created beforehand. The URL of the created game is returned as a value of the `Location` header.

### List all games:
```curl -v -H "Authorization: Basic dXNlcjp0ZXN0" -H "Content-Type: application/json" http://localhost:8080/games```

### Get a particular game:
```curl -v -H "Authorization: Basic dXNlcjp0ZXN0" -H "Content-Type: application/json" http://localhost:8080/games/13```
(Where **13** is the ID of the game.)

---

## Building the application

Self-hosting JAR file can be built by
```
./gradlew bootJar
```

This builds a JAR file in `build/libs/` which can later be run by
```
java -jar build/libs/foosball-0.0.1-SNAPSHOT.jar
```

Automatic tests and linting can be run by
```
./gradlew check
```

### WAR file
Alternatively, a WAR file which can be deployed to external application server (such as Tomcat) can be built by
```
./gradlew bootWar 
```

The war file will then be available in `build/libs/foosball-0.0.1-SNAPSHOT.jar` 

---

[![CircleCI](https://circleci.com/gh/vkolencik/foosball/tree/master.svg?style=svg&circle-token=38baa4e48eedb12fbb333f5e5ce4cb6ccf07965f)](https://circleci.com/gh/vkolencik/foosball/tree/master)
