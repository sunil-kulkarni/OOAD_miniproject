# OOAD Mini Project: Blog Application

A full-featured monolithic Blog Application built with Spring Boot, allowing users to register, log in, create and edit blogs, upload images, leave comments, and react to posts.

## Features

- **User Authentication:** Secure registration and login functionalities using Spring Security.
- **Blog Management:** Create, read, update, and delete (CRUD) blog posts.
- **Image Support:** Upload and display images seamlessly within blog posts.
- **Interaction:** Add comments and react to your favorite blogs.
- **Responsive UI:** Custom-built frontend using HTML, CSS, and Thymeleaf for dynamic rendering.

## Technology Stack

- **Backend:** Java 17+, Spring Boot (Spring Web, Spring Security, Spring Data JPA)
- **Frontend:** HTML5, CSS3, Thymeleaf
- **Database:** SQLite (Lightweight, local file-based database)
- **Build Tool:** Maven

## Prerequisites

Before running the application, ensure you have the following installed:
- [Java Development Kit (JDK) 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or higher
- [Git](https://git-scm.com/)

*(Note: Maven is included via the Maven Wrapper (`mvnw`), so you do not need to install it separately).*

## Getting Started

### 1. Clone the Repository

Open your terminal or command prompt and run:
```bash
git clone <repository-url>
cd OOAD_miniproject
```

### 2. Run the Application

You can run the application directly using the included Maven Wrapper. 

**On Linux / macOS:**
```bash
./mvnw spring-boot:run
```

**On Windows:**
```cmd
mvnw.cmd spring-boot:run
```

### 3. Access the Application

Once the application has started and you see the `Started BlogApplication in X seconds` message in your terminal, open your web browser and navigate to:

 **[http://localhost:8080](http://localhost:8080)**

## Database Information

This project uses an **SQLite** database for simplicity and ease of setup. 
- The database file (`blog.db`) will be created automatically in the root directory of the project the first time you run the application.
- Tables are generated and updated automatically by Hibernate.

