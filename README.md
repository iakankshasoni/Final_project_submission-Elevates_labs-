# Final_project_submission-Elevates_labs-
# Online Quiz System (Java Swing + MySQL)

## Overview
This is a simple timed quiz application built with Java (Swing) and MySQL (JDBC).  
Features:
- User registration & login
- Quiz selection
- Randomized questions per user
- Quiz timer and scoring
- Results stored in database
- Results display with correct answers

## Requirements
- Java 8 or newer
- MySQL server
- MySQL Connector/J (JDBC driver) on classpath (e.g., `mysql-connector-java-8.0.xx.jar`)

## Setup
1. Create the database and tables:
   - Open the file `db/schema.sql` and run it in your MySQL server (change DB name if needed).
   - This will create a sample quiz and questions.

2. Update DB connection settings:
   - Open `src/db/DBConnection.java` and set `DB_URL`, `DB_USER`, `DB_PASSWORD` to your MySQL credentials.

3. Compile and run:
   - From the project root:
     ```
     javac -d out -cp "path/to/mysql-connector-java.jar" src/**/*.java
     java -cp "out:path/to/mysql-connector-java.jar" app.Main
     ```
   - On Windows adjust classpath separator `;` instead of `:`.

## Notes
- The UI is intentionally minimal and educational.
- You can extend: add password hashing, better UI styling (JavaFX), pagination, admin CRUD for quizzes.

