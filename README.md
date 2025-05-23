# HomeStock

Smart Home Inventory Tracking Web Application

HomeStock is a web application built with Spring Boot (Java) and React (JavaScript) that helps you manage your household inventory (groceries & household items). Features include:

* Smart expiry alerts
* Recipe suggestions based on soon-to-expire items
* Insights on frequently used and wasted items
* Google OAuth2 authentication

## Tech Stack

* **Backend**: Spring Boot, Spring Data JPA, Spring Security (OAuth2), MySQL
* **Frontend**: React, React Router, Axios, Bootstrap
* **Database**: MySQL
* **Authentication**: Google OAuth2
* **Styling**: Bootstrap
* **Code Quality**: SonarQube (optional)

## Prerequisites

* Java 17 SDK
* Node.js and npm (>=14)
* MySQL Server
* Docker (optional, for SonarQube)

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/your-username/HomeStock.git
cd HomeStock
```

### 2. Configure the Backend

1. Create a MySQL database:

```sql
CREATE DATABASE homestock;
```

2. Copy `backend/src/main/resources/application.properties.example` to `backend/src/main/resources/application.properties` and update the following properties:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/homestock?useSSL=false&serverTimezone=UTC
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET
```

### 3. Run the Backend

```bash
cd backend
./mvnw clean package
./mvnw spring-boot:run
```

The backend runs at [http://localhost:8080](http://localhost:8080).

### 4. Run the Frontend

```bash
cd frontend
npm install
npm start
```

The frontend opens at [http://localhost:3000](http://localhost:3000).

## Features

* **Login**: Click *Log in with Google* to authenticate.
* **Inventory**: Add, edit, delete items (name, quantity, unit, expiry date, notes).
* **Expiry Alerts**: Scheduled task logs items expiring soon.
* **Recipe Suggestions**: Stub endpoint returns recipe suggestions.
* **Analytics**: Endpoints for usage and waste metrics (planned).

## (Optional) SonarQube Setup

To analyze code quality:

```bash
docker run -d --name sonarqube -p 9000:9000 sonarqube:community
```

* Open [http://localhost:9000](http://localhost:9000), log in with admin/admin, change password.
* Create project key HomeStock, generate a token.
* Add `sonar-project.properties` at the project root with your token.
* Run scanner:

```bash
docker run --rm -e SONAR_HOST_URL=http://localhost:9000 -e SONAR_LOGIN=YOUR_SONAR_TOKEN -v $(pwd):/usr/src sonarsource/sonar-scanner-cli.
```

## License

This project is licensed under the MIT License.
