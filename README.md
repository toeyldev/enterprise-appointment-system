# Enterprise Appointment Scheduling System

## Problem Statement
Many small fitness studios still rely on manual scheduling methods, spreadsheets, or messaging applications to manage class reservations. These approaches can lead to double bookings, inconsistent reservation records, limited visibility into class availability, and inefficient communication between customers, instructors, and administrators.

This project solves these issues by providing an enterprise-style appointment scheduling system for a Pilates studio using Java, Spring Boot, JDBC, and MySQL. The system supports real-time class booking, waitlist management, credit purchases, instructor scheduling, and concurrency control to prevent double booking. The application also demonstrates enterprise backend concepts such as layered architecture, transaction management, logging, health monitoring, and external service integration.

---

## Features
**Customers**
- Browse available class schedules
- View real-time class status (Open or Waitlisted)
- Reserve classes
- Join waitlists
- Cancel reservations
- View reservation history
- Purchase class credits
- View credit purchase history
- View upcoming class statistics

**Instructors**
- View assigned classes
- View enrolled customers for each class

**Admins**
- Add new classes
- Cancel existing classes
- Update instructor assignments
- Manage credit packages
- Add, edit, and remove credit packages

---

## Tech Stack
**Frontend**
- HTML
- CSS
- JavaScript

**Backend**
- Java
- Spring Boot
- JDBC
- Maven

**Database**
- MySQL
- MySQL Workbench

**Additional Concepts**
- REST APIs
- Layered Enterprise Architecture
- Transaction Management
- Concurrency Control
- Logging & Monitoring

---

## Architecture

The system follows a layered enterprise architecture:

Controller → Service → Repository → Database

- Controllers handle HTTP requests
- Services contain business logic
- Repositories handle JDBC database operations
- MySQL stores persistent application data

---

## Setup

**1. Clone the repository:**
```bash
git clone https://github.com/toeyldev/enterprise-appointment-system.git
cd enterprise-appointment-system
```

**2. Setup the Database**
- Open MySQL Workbench and run the SQL script located at:
  ```bash
  src/main/resources/enterprise-system-DB.sql
  ```

3. Run the Application
- From the terminal:
  ```bash
  ./mvnw spring-boot:run
  ```
- The application will start at: `http://localhost:8080/`

---

## Project Structure
```bash
src/
 ├── main/
 │    ├── java/
 │    │     └── edu/sjsu/cmpe172/starterdemo/
 │    │            ├── controller/
 │    │            ├── dto/
 │    │            ├── model/
 │    │            ├── repository/
 │    │            └── service/
 │    └── resources/
 │          ├── static/
 │          ├── templates/
 │          ├── application.properties
 │          └── enterprise-system-DB.sql
```
