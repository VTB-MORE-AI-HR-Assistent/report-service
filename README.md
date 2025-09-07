# VTB AI HR Report Service

A Spring Boot microservice for managing ML-generated candidate reports and recommendations in the VTB AI HR system.

## Overview

This service handles the storage and retrieval of machine learning-generated candidate evaluation reports. It processes ML report data, converts it into structured entities, and provides RESTful APIs for accessing candidate reports and recommendations.

## Technology Stack

- **Language**: Kotlin 1.9.25
- **Framework**: Spring Boot 3.5.5
- **Database**: PostgreSQL with JPA/Hibernate
- **Migration**: Flyway
- **Build Tool**: Gradle with Kotlin DSL
- **Java Version**: 21

## Features

- **ML Report Processing**: Accepts and processes ML-generated candidate evaluation data
- **Data Storage**: Stores reports and recommendations in PostgreSQL database
- **RESTful APIs**: Provides endpoints for saving and retrieving candidate data
- **Clean Architecture**: Follows separation of concerns with mapper, service, and repository layers
- **Database Migrations**: Automated schema management with Flyway

## API Endpoints

### Save ML Report

```http
POST /api/reports
Content-Type: application/json

{
  "candidateId": 123,
  "jobId": 456,
  "interviewId": 789,
  "overallScore": 85,
  "overallMatchScore": 90,
  "technicalSkillsScore": 88,
  "confirmedSkills": ["Java", "Spring Boot", "Kotlin"],
  "missingSkills": ["Docker", "Kubernetes"],
  "technicalDetails": "Strong technical background...",
  "communicationScore": 82,
  "communicationDetails": "Good communication skills...",
  "experienceScore": 87,
  "relevantProjects": ["Project A", "Project B"],
  "experienceDetails": "5+ years of experience...",
  "totalQuestions": 20,
  "questionsAnswered": 18,
  "problemSolvingScore": 85,
  "teamworkScore": 90,
  "leadershipScore": 75,
  "adaptabilityScore": 88,
  "redFlags": [],
  "strengths": ["Technical skills", "Problem solving"],
  "gaps": ["Leadership experience"],
  "recommendationDecision": "HIRE",
  "recommendationConfidence": 0.85,
  "recommendationReasoning": "Strong technical fit...",
  "nextSteps": ["Technical interview", "Reference check"],
  "candidatePositivePoints": ["Good technical skills"],
  "candidateImprovementAreas": ["Leadership"]
}
```

### Get Candidate Report

```http
GET /api/reports/candidate?candidateId=123&jobId=456
```

### Get Candidate Recommendation

```http
GET /api/reports/recommendations?candidateId=123&jobId=456
```

## Project Structure

```
src/main/kotlin/com/vtb/report/
├── config/              # Configuration classes
├── controller/          # REST API controllers
├── dto/                # Data Transfer Objects
├── mapper/             # Entity mapping utilities
├── model/              # JPA entities
├── repository/         # Data access layer
├── service/            # Business logic layer
└── ReportApplication.kt # Main application class
```

## Database Schema

### Reports Table

Stores comprehensive candidate evaluation data including scores, skills, and detailed feedback.

### Recommendations Table

Stores candidate hiring recommendations with decision, confidence, and reasoning.

## Getting Started

### Prerequisites

- Java 21
- PostgreSQL 12+
- Gradle 7+

### Installation

1. **Clone the repository**

   ```bash
   git clone <repository-url>
   cd report
   ```

2. **Configure Database**

   ```yaml
   # application.yml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/aihr
       username: postgres
       password: your_password
   ```

3. **Run the application**

   ```bash
   ./gradlew bootRun
   ```

4. **Verify installation**
   ```bash
   curl http://localhost:8085/actuator/health
   ```

## Configuration

### Environment Variables

- `PG_URL`: PostgreSQL connection URL
- `PG_USERNAME`: Database username
- `PG_PASSWORD`: Database password
- `JWT_SECRET`: JWT secret key

### Application Properties

The service runs on port **8085** by default. Database migrations are automatically applied on startup.

## Development

### Building the Project

```bash
# Build
./gradlew build

# Run tests
./gradlew test

# Run application
./gradlew bootRun
```

### Database Migrations

Database schema changes should be added as Flyway migration files in `src/main/resources/db/migration/`.

## Architecture

The service follows Clean Architecture principles:

- **Controller Layer**: Handles HTTP requests and responses
- **Service Layer**: Contains business logic and orchestration
- **Repository Layer**: Manages data access
- **Mapper Layer**: Converts between DTOs and entities
- **Model Layer**: Defines domain entities

## Error Handling

The service includes comprehensive error handling:

- **404 Not Found**: When candidate report/recommendation not found
- **500 Internal Server Error**: For unexpected errors during report processing
- **Validation Errors**: For malformed request data

## Monitoring

The service includes

- **Health Check**: `/actuator/health`
- **Structured Logging**: JSON-formatted logs
- **Database Connection Monitoring**: Via Spring Boot Actuator

## Contributing

1. Follow Kotlin coding standards
2. Add tests for new features
3. Update documentation for API changes
4. Ensure all tests pass before submitting PR

## License

This project is part of the VTB AI HR system and is proprietary software.
