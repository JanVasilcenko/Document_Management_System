# Document Management System
This is a small project in Spring Boot that leverages REST api and H2 in memory database to create, store and edit documents.

## How to start
1. Navigate to the root of the project.
2. Open cmd
3. Run this command: mvnw spring-boot:run

If there are problems with locating the JDK you can download it on this link: https://adoptium.net/temurin/releases/?version=21

## Content of the project
This project features REST api that the user can interact with and manage documents and protocols.

Document is a simple model that holds a document type like JPG or PDF.
It also stores information when it was created, along with its name and who it was created by.

Protocol is a model that holds multiple or one document, it cannot exist alone. It holds its status like NEW, PREPARE_FOR_SHIPMENT or CANCELLED. It has also information about when it was created and by whom.

Note: Each document can belong to only ONE protocol.

### Rest Endpoints
Document Controller

| Method Call  | Request Path | Response | 
| ------------- | ------------- | ------------- |
| GET  | BASEPATH/api/v1/documents/{documentId} | 200 OK, 404 NOT FOUND  |
| POST  | BASEPATH/api/v1/documents  | 201 CREATED |
| PATCH  | BASEPATH/api/v1/documents/{documentId}  | 200 OK, 404 NOT FOUND |
| DELETE  | BASEPATH/api/v1/documents/{documentId}  | 204 NO CONTENT, 404 NOT FOUND |

Protocol Controller
| Method Call  | Request Path | Response | 
| ------------- | ------------- | ------------- |
| GET  | BASEPATH/api/v1/protocols/{protocolId} | 200 OK, 404 NOT FOUND  |
| POST  | BASEPATH/api/v1/protocols  | 201 CREATED, 404 NOT FOUND, 409 CONFLICT  |
| PUT  | BASEPATH/api/v1/protocols/{protocolId}  | 200 OK, 404 NOT FOUND, 409 CONFLICT |
| PATCH  | BASEPATH/api/v1/protocols/{protocolId}  | 200 OK, 404 NOT FOUND |

### Database
There is a simple in-memory H2 database in place that deletes its content once the application closes. The tables are generated using Hibernate by using specified entities.

### Authentication
All the endpoints are secured using in-memory user. Username: user, Password: user. Disallowing unauthenticated interaction with the API.

The only endpoints that are unsecured are the SWAGGER documentation:<br>
BASEPATH/swagger-ui/index.html<br>
BASEPATH/v3/api-docs

### Unit tests
Services are tested for all the situations using Mockito and JUnit.
