# SafetyNet Alerts - README

## Project Description
SafetyNet Alerts is a Spring Boot-based application for emergency management. It provides REST endpoints to manage and query fire stations, medical records, and residents in a community. The project is designed with clean architecture, SOLID principles, and includes comprehensive unit testing and code coverage.

## Technology Stack
- Java 21
- Spring Boot
- Maven
- JUnit 5
- Jacoco
- Surefire
- Spring Web
- Jackson
- Mockito
- Lombok

## Prerequisites
- Java 21 or higher
- Maven

## Setup Instructions
1. **Clone the Repository**
   ```sh
   git clone <repository_url>
   ```
2. **Install Dependencies**
   ```sh
   cd <project_folder>
   mvn install
   ```
3. **Run the Application**
   ```sh
   mvn spring-boot:run
   ```
   The application will be accessible at http://localhost:8080

## Endpoints
- `GET /firestation?stationNumber=<station_number>`: List of people covered by the fire station, with names, addresses, phone numbers, and counts of adults/children.
- `GET /childAlert?address=<address>`: List of children at an address, with age and household members.
- `GET /phoneAlert?firestation=<firestation_number>`: List of phone numbers for residents served by a fire station.
- `GET /fire?address=<address>`: List of people at an address, with medical history and fire station number.
- `GET /flood/stations?stations=<station_numbers>`: List of homes served by multiple fire stations, grouped by address.
- `GET /personInfo?lastName=<last_name>`: Details (name, address, age, email, medical history) for all people with the given last name.
- `GET /communityEmail?city=<city>`: Email addresses of all residents in the specified city.

## Development Guidelines
- Architecture: MVC
- SOLID Principles: Code is structured for maintainability and scalability.

## Testing and Code Coverage
- Unit tests are implemented using JUnit and Mockito.
- Jacoco is used for code coverage (80%+ required).
- Maven Surefire plugin generates detailed test reports.

## Documentation
- Jacoco: `target/site/jacoco/index.html`
- Surefire: `target/site/surefire-report.html`

## Additional Links
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Jacoco Documentation](https://www.jacoco.org/jacoco/trunk/doc/)
- [JUnit 5 Documentation](https://junit.org/junit5/)
