# Basic Student Management System

A Spring Boot REST API for managing students, books, and courses. Built while learning JPA relationships, so the focus is on how the three entities connect rather than on features.

**No AI was used in this project. 0% AI, 100% written by hand.**

## Relationships

- A student can own many books. A book belongs to at most one student (one-to-many).
- A student can enroll in many courses, and a course can have many students (many-to-many).

## Tech

- Java 17
- Spring Boot 4.1.0
- Spring Data JPA
- ModelMapper
- Lombok
- H2 for development, PostgreSQL driver included

## Running it

```bash
./mvnw spring-boot:run
```

The app starts on `http://localhost:8080`.

## Endpoints

### Students

| Action | Method | Path |
| --- | --- | --- |
| Create a new student | POST | `/students` |
| Retrieve all students | GET | `/students` |
| Retrieve a student by ID | GET | `/students/{id}` |
| Update a student's details | PUT | `/students/{id}` |
| Delete a student | DELETE | `/students/{id}` |

### Books

Books are identified by their ISBN.

| Action | Method | Path |
| --- | --- | --- |
| Create a new book | POST | `/books` |
| Retrieve all books | GET | `/books` |
| Retrieve a book by ISBN | GET | `/books/{isbn}` |
| Update a book's details | PUT | `/books/{isbn}` |
| Delete a book | DELETE | `/books/{isbn}` |

### Courses

Courses are identified by their course code.

| Action | Method | Path |
| --- | --- | --- |
| Create a new course | POST | `/courses` |
| Retrieve all courses | GET | `/courses` |
| Retrieve a course by code | GET | `/courses/{code}` |
| Update a course's details | PUT | `/courses/{code}` |
| Delete a course | DELETE | `/courses/{code}` |

### Books and students

| Action | Method | Path |
| --- | --- | --- |
| Assign a book to a student | POST | `/students/{id}/books/{isbn}` |
| Retrieve the student who owns a book | GET | `/books/{isbn}/owner` |
| Retrieve all books of a student | GET | `/students/{id}/books` |
| Remove a book from a student | DELETE | `/students/{id}/books/{isbn}` |

### Courses and students

| Action | Method | Path |
| --- | --- | --- |
| Enroll a student in a course | POST | `/students/{id}/courses/{code}` |
| Retrieve all students in a course | GET | `/courses/{code}/students` |
| Retrieve all courses of a student | GET | `/students/{id}/courses` |
| Remove a student from a course | DELETE | `/students/{id}/courses/{code}` |

## Sample requests

Create a student:

```bash
curl -X POST http://localhost:8080/students \
  -H "Content-Type: application/json" \
  -d '{"name": "Juan Dela Cruz"}'
```

Create a book:

```bash
curl -X POST http://localhost:8080/books \
  -H "Content-Type: application/json" \
  -d '{"isbn": "978-0134685991", "title": "Effective Java"}'
```

Create a course:

```bash
curl -X POST http://localhost:8080/courses \
  -H "Content-Type: application/json" \
  -d '{"code": "CCS06", "title": "Software Engineering"}'
```

Assign the book and enroll the student:

```bash
curl -X POST http://localhost:8080/students/1/books/978-0134685991
curl -X POST http://localhost:8080/students/1/courses/CCS06
```

## Project structure

```
controllers/     REST endpoints
services/        business logic, with impl/ holding the implementations
repositories/    Spring Data JPA repositories
entities/        JPA entities
dto/             response objects, with summaries/ for the trimmed down versions
mapper/          entity to DTO mapping built on ModelMapper
configurations/  ModelMapper bean setup
```

Full DTOs carry the related records. Summary DTOs leave them out, which keeps the JSON from looping back on itself when a student lists its courses and a course lists its students.

## Notes

Deleting a student clears its book ownership and course enrollments first, so the related rows stay intact. Deleting a book or course only removes that record.
