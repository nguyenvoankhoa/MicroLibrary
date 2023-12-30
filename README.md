# Micro Library

## Project Overview
This repository contains the source code and documentation for a set of microservices designed to manage and provide access to a collection of books in a micro-library system. These microservices are developed to work together seamlessly and provide a scalable and modular architecture.

## Architecture
- Discovery Server: discover and register routes.
- Api Gateway: entry point for external clients to interact with the microservices.
- Book Sevice: manage the information related to books in the micro-library.
- Borrowing Service: manage the borrowing and returning of books.
- Employee Service: handles employee-related functionalities.
- Common Service: contains shared functionalities and utilities used across multiple microservices.
- Notification Service: sending notifications to users or employees.
- User Service: manages user-related functionalities.

## Dependencies
- SpringBoot
- Spring Cloud
- Axon Framework
- H2 Database
