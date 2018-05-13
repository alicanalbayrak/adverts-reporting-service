# Reporting Service

Reporting service is a RESTful web service that provides reporting advertising data, 
part of which is extracted from a csv file and the rest being calculated as additional metrics.

* [Maven] - Dependency and software project management tool
* [Spring Boot] - Bootstrapping and development of a Spring application
* [H2 Database] - Relational database
* [Swagger] - Visualizing project API's resources


## Building from source

#### Build Requirements

Reporting service requires only [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) and [Maven 3.2](https://maven.apache.org/download.cgi).

+ _H2 Database provided in a fat jar._

##### Pre-build

Extract project and enter folder.
```sh
$ tar -xvzf adverts-reporting-service-alicanalbayrak.tar.gz
$ cd adverts-reporting-service/
```

##### Build

Compile the source code in the ```adverts-reporting-service``` directorty.

```sh
$ mvn clean install
```

### Running the project

In the ```adverts-reporting-service``` directorty.

```sh
$ mvn spring-boot:run
```

#### Usage

This project automatically reads, parses and persists the any CSV files that are located in ```adverts-reporting-service/src/main/resources/csv/```. 

**Check H2-console**

H2 web console is enabled by default. Persisted data can be seen from following location:

```sh
http://localhost:8080/h2-console/
```

with following configuration:

| Key | Value |
| ------ | ------ |
| Saved Settings: | Generic H2 (Server) |
| Driver Class: | org.h2.Driver |
| JDBC URL: | jdbc:h2:mem:report_db |
| User Name: | alicana |
| Password: | pa$$word |

**Swagger Api Documentation**

```sh
http://localhost:8080/swagger-ui.html
```


### Examples

#### General API Information

##### Parameter Definitions

**Month:**
* numeric (ranging from 1-12) that map to the corresponding months (`1` for `January`, `2` for `February` etc)
* first 3 letters of the month (`Jan` for `January`, `Feb` for `February` etc)
* full name of the month (case insensitive)

**Site:**
* desktop_web
* mobile_web
* android
* iOS

##### Passing arguments as path variable 

Both `month` and  `site` variables can be passed in the form `/reports/{month}/{site}`.

Example: Retrieve advertisement data records (incl. metrics) of customer's desktop web app on January.

Request:
```sh
GET /reports/jan/desktop_web HTTP/1.1
Host: localhost:8080
Cache-Control: no-cache
```

Response Code: 200 OK

Response Body:
```json
{
  "month": "January",
  "site": "desktop_web",
  "requests": 12483775,
  "impressions": 11866157,
  "clicks": 30965,
  "conversions": 7608,
  "revenue": 23555.46,
  "CTR": 0.26,
  "CR": 0.06,
  "fill_rate": 95.05,
  "eCPM": 1.99
}
```

Response Headers:
```sh
 content-type: application/json;charset=UTF-8 
 date: Sun, 13 May 2018 19:30:25 GMT 
 transfer-encoding: chunked 
```

##### Mixed query string and path variable 

`month` and `site` variables can be passed in the form `/reports/{month}?site=android` 

Request:
```sh
GET /reports/february?site=android HTTP/1.1
Host: localhost:8080
Cache-Control: no-cache
```
Response: [__200 OK__]
```json
{
    "month": "February",
    "site": "android",
    "requests": 8921215,
    "impressions": 8342439,
    "clicks": 22934,
    "conversions": 5347,
    "revenue": 17210.12,
    "CTR": 0.27,
    "CR": 0.06,
    "fill_rate": 93.51,
    "eCPM": 2.06
}
```

**If site information not provided, the endpoint retrieves all site information and returns aggregated result**

Request: (notice absence of "site" attribute)
```sh
GET /reports/feb HTTP/1.1
Host: localhost:8080
Cache-Control: no-cache
```

Response: [__200 OK__] (notice site field not included in the resulting object)

```json
{
    "month": "February",
    "requests": 33969832,
    "impressions": 31322712,
    "clicks": 97742,
    "conversions": 18071,
    "revenue": 62940.15,
    "CTR": 0.31,
    "CR": 0.06,
    "fill_rate": 92.21,
    "eCPM": 2.01
}
```

##### Passing both arguments as query parameter

Reporting service also provides passing parameters in the following form `/reports?month=jan&site=iOS` 

* If `month` attribute is absent returns aggregated result in according to `site` attribute
* If `site` attribute is absent returns aggregated result in according to `month` attribute

Request:
```sh
GET /reports?month=1&site=desktop_web HTTP/1.1
Host: localhost:8080
Cache-Control: no-cache
```

Response: [__200 OK__]
```json
{
  "month": "January",
  "site": "desktop_web",
  "requests": 12483775,
  "impressions": 11866157,
  "clicks": 30965,
  "conversions": 7608,
  "revenue": 23555.46,
  "CTR": 0.26,
  "CR": 0.06,
  "fill_rate": 95.05,
  "eCPM": 1.99
}
```

Example: Aggregated __all months__ 

Request:
```sh
GET /reports?site=desktop_web HTTP/1.1
Host: localhost:8080
Cache-Control: no-cache
```

Response: [__200 OK__]
```json
{
    "site": "desktop_web",
    "requests": 23727650,
    "impressions": 22232512,
    "clicks": 71421,
    "conversions": 9064,
    "revenue": 39300.78,
    "CTR": 0.32,
    "CR": 0.04,
    "fill_rate": 93.7,
    "eCPM": 1.77
}
```

#### Error handling

* Wrong month numeric

Request:
```sh 
GET /reports/177?site=desktop_web HTTP/1.1
Host: localhost:8080
Cache-Control: no-cache
```

Response: [__400 BAD REQUEST__]
```json
{
    "timestamp": 1526243449336,
    "status": 400,
    "error": "Bad Request",
    "message": "Month [177] is not valid!"
}
```

* Wrong month string

Request:
```sh 
GET /reports/xyZ?site=desktop_web HTTP/1.1
Host: localhost:8080
Cache-Control: no-cache
```

Response: [__400 BAD REQUEST__]
```json
{
    "timestamp": 1526243593057,
    "status": 400,
    "error": "Bad Request",
    "message": "Month [xyZ] is not valid!"
}
```

* Wrong site attribute

Request:
```sh 
GET /reports/feb?site=iot HTTP/1.1
Host: localhost:8080
Cache-Control: no-cache
```

Response:
```json
{
    "timestamp": 1526243643638,
    "status": 400,
    "error": "Bad Request",
    "message": "Unkown site type received:[iot] ! Accepted types: [desktop_web, mobile_web, android, iOS]"
} 
```