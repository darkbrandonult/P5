# SafetyNet Alerts - Technical Specifications

## Project Overview

SafetyNet Alerts is a Spring Boot-based REST API application designed for emergency management systems. It provides endpoints to retrieve critical safety information for emergency responders.

## System Requirements

### Runtime Environment
- **Java Version**: 21 (LTS)
- **Operating System**: Windows, macOS, Linux
- **Memory**: Minimum 512MB RAM
- **Storage**: 100MB disk space

### Development Environment
- **Java SDK**: OpenJDK 21 or Oracle JDK 21
- **Build Tool**: Apache Maven 3.6+
- **IDE**: Any Java IDE (IntelliJ IDEA, Eclipse, VS Code)

## Technical Architecture

### Framework & Dependencies
- **Spring Boot**: 2.7.18
- **Spring Web**: REST API framework
- **Jackson**: JSON processing
- **SLF4J + Logback**: Logging framework
- **JUnit 5**: Unit testing framework
- **Mockito**: Mocking framework for tests
- **JaCoCo**: Code coverage analysis

### Application Architecture
```
┌─────────────────┐
│   Controllers   │ ← REST API Layer
├─────────────────┤
│    Services     │ ← Business Logic Layer
├─────────────────┤
│     Models      │ ← Data Model Layer
├─────────────────┤
│   Data Source   │ ← JSON File Storage
└─────────────────┘
```

### Package Structure
```
com.safetynet.alerts/
├── controller/
│   ├── AlertController.java
│   ├── PersonController.java
│   ├── PersonManagementController.java
│   ├── FirestationController.java
│   ├── MedicalRecordController.java
│   └── MedicalRecordManagementController.java
├── service/
│   ├── PersonService.java
│   ├── FirestationService.java
│   └── MedicalRecordService.java
├── model/
│   ├── Person.java
│   ├── Firestation.java
│   └── MedicalRecord.java
├── dto/
│   ├── FirestationCoverageDTO.java
│   ├── ChildAlertDTO.java
│   ├── PhoneAlertDTO.java
│   ├── FireDTO.java
│   ├── FloodDTO.java
│   └── PersonInfoDTO.java
└── SafetyNetAlertsApplication.java
```

## API Specifications

### Content Type
- **Request**: `application/json`
- **Response**: `application/json`

### HTTP Status Codes
- `200 OK`: Successful operation
- `400 Bad Request`: Invalid request parameters
- `404 Not Found`: Resource not found
- `500 Internal Server Error`: Server error

### Safety Alert Endpoints

#### 1. Fire Station Coverage
```
GET /firestation?stationNumber={number}
Response: FirestationCoverageDTO
{
  "persons": [PersonDTO],
  "adultCount": integer,
  "childCount": integer
}
```

#### 2. Child Alert
```
GET /childAlert?address={address}
Response: ChildAlertDTO
{
  "children": [PersonDTO],
  "adults": [PersonDTO]
}
```

#### 3. Phone Alert
```
GET /phoneAlert?firestation={number}
Response: [PhoneAlertDTO]
[{
  "phone": "string"
}]
```

#### 4. Fire Information
```
GET /fire?address={address}
Response: FireDTO
{
  "persons": [PersonWithMedicalDTO],
  "stationNumber": integer
}
```

#### 5. Flood Information
```
GET /flood/stations?stations={numbers}
Response: [FloodDTO]
[{
  "address": "string",
  "persons": [PersonWithMedicalDTO]
}]
```

#### 6. Person Information
```
GET /personInfo?lastName={lastName}
Response: [PersonInfoDTO]
[{
  "firstName": "string",
  "lastName": "string",
  "address": "string",
  "age": integer,
  "email": "string",
  "medications": ["string"],
  "allergies": ["string"]
}]
```

#### 7. Community Email
```
GET /communityEmail?city={city}
Response: ["string"]
```

## Data Models

### Person
```java
{
  "firstName": "string",
  "lastName": "string",
  "address": "string",
  "city": "string",
  "zip": "string",
  "phone": "string",
  "email": "string"
}
```

### Firestation
```java
{
  "address": "string",
  "station": "integer"
}
```

### Medical Record
```java
{
  "firstName": "string",
  "lastName": "string",
  "birthdate": "MM/dd/yyyy",
  "medications": ["string"],
  "allergies": ["string"]
}
```

## Data Storage

### File Format
- **Format**: JSON
- **File**: `data.json`
- **Location**: Application root directory
- **Encoding**: UTF-8

### Data Structure
```json
{
  "persons": [Person],
  "firestations": [Firestation],
  "medicalrecords": [MedicalRecord]
}
```

## Testing Strategy

### Test Coverage Requirements
- **Minimum Coverage**: 80%
- **Unit Tests**: All service classes
- **Integration Tests**: All controllers
- **DTO Tests**: All data transfer objects

### Test Categories
1. **Unit Tests**: Service layer business logic
2. **Integration Tests**: Full HTTP request/response cycle
3. **Controller Tests**: REST endpoint functionality
4. **DTO Tests**: Data transfer object validation

### Test Tools
- **JUnit 5**: Test framework
- **Mockito**: Mocking framework
- **Spring Boot Test**: Integration testing
- **JaCoCo**: Coverage reporting

## Performance Specifications

### Response Time Targets
- **Simple Queries**: < 100ms
- **Complex Queries**: < 500ms
- **Data Modifications**: < 200ms

### Scalability
- **Concurrent Users**: 50+
- **Data Volume**: 1000+ persons, 50+ fire stations
- **Memory Usage**: < 256MB under normal load

## Security Considerations

### Data Protection
- No sensitive personal data encryption (educational project)
- File-based storage with local access only
- No authentication/authorization (as per requirements)

### Input Validation
- Parameter validation for all endpoints
- JSON schema validation for POST/PUT requests
- SQL injection prevention (though not applicable)

## Deployment Instructions

### Build Process
```bash
mvn clean compile
mvn test
mvn package
```

### Deployment
```bash
java -jar target/safetynetalerts-1.0.0.jar
```

### Configuration
- **Port**: 8080 (default)
- **Context Path**: / (root)
- **Log Level**: INFO (configurable)

## Monitoring & Logging

### Log Levels
- **ERROR**: System errors, exceptions
- **WARN**: Business logic warnings
- **INFO**: Request/response logging
- **DEBUG**: Detailed execution flow

### Log Output
- **Console**: Development environment
- **File**: Production environment (configurable)

## Maintenance

### Backup Strategy
- **Data File**: Regular backup of `data.json`
- **Application**: Version control with Git

### Updates
- **Dependencies**: Regular security updates
- **Java Version**: Follow LTS release cycle
- **Framework**: Spring Boot minor version updates

This technical specification provides comprehensive details for development, deployment, and maintenance of the SafetyNet Alerts application.
