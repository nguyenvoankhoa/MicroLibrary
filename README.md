# Micro Library

## Project Overview
This repository contains the source code and documentation for a set of microservices designed to manage and provide access to a collection of books in a micro-library system. These microservices are developed to work together seamlessly and provide a scalable and modular architecture.

## Architecture
This project implements the Command Query Responsibility Segregation (CQRS) and Domain-Driven Design (DDD) patterns with the assistance of the Axon Framework to handle commands and queries. Authentication and authorization are managed through JSON Web Tokens (JWT), and real-time notifications are facilitated by Kafka.

## Key Components
-**Discovery Server**: Discovers and registers routes within the microservices architecture.
-**API Gateway**: Serves as the entry point for external clients, facilitating interaction with the microservices.
-**Book Service**: Manages information related to books within the micro-library.
-**Borrowing Service**: Oversees the borrowing and returning of books.
-**Employee Service**: Handles functionalities related to employees within the system.
-**Common Service**: Contains shared functionalities and utilities utilized across multiple microservices.
-**Notification Service**: Sends notifications to users or employees.
-**User Service**: Manages user-related functionalities.

## Dependencies
- SpringBoot
- Spring Cloud
- Axon Framework
- H2 Database
- Kafka

