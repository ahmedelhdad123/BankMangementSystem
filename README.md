# Bank Management System

Welcome to the Bank Management System repository, a robust foundation for managing banking operations built using Spring Boot! This project streamlines various banking operations through a modern web-based application.

## Introduction

The Bank Management System is a web-based application developed using Spring Boot. It aims to simplify banking operations by providing features to manage customer accounts and perform transactions securely and efficiently.

## Features

- **Authentication and Authorization**: Prioritize security with robust user authentication and authorization mechanisms, ensuring safe and controlled access to the system.
- **Multi-Account Support**: Users can create up to three separate accounts within the system, catering to various financial needs and goals.
- **Comprehensive Account Details**: Access a wealth of information about your accounts, including balance summaries, card numbers, CVV numbers, and more, empowering you with a clear overview of your financial status.
- **Efficient Transaction Handling**: Utilize well-defined API endpoints for seamless fund transfers between accounts, ensuring efficient and accurate transaction processing.

## Installation

To run the Bank Management System locally, ensure you have the following prerequisites:
- Java 11 or higher
- Maven
- MySQL

## Usage

### Customer Actions

- **Account Creation**: Create multiple accounts tailored to your financial needs.
- **View Account Details**: Access comprehensive details about your accounts, empowering you with insights into your balances and transactions.

### Transaction Operations

- **Deposit Funds**: Use the `/api/transactions/deposit` endpoint to securely deposit funds into specified accounts.
- **Withdraw Funds**: Utilize the `api/transactions/withdraw` endpoint for withdrawing funds from accounts, ensuring seamless and accurate transactions.

## Technologies Used
- Java
- Spring Boot
- Spring Security
- json web token (JWT)
- Spring Data JPA
- Lombok
- MySQL(Workbench)
- Maven 
- Docker
## BankApi
 - [![Run in Postman](https://run.pstmn.io/button.svg)](https://documenter.getpostman.com/view/20573769/2sA3rzKsPu)

## Database Schema

```mermaid
erDiagram
    User {
        SERIAL id PK
        VARCHAR name
        VARCHAR password
        VARCHAR phone "UNIQUE"
        VARCHAR email "UNIQUE"
        ENUM role
    }
    Account {
        SERIAL id PK
        VARCHAR cardNumber "UNIQUE"
        VARCHAR cvv
        DECIMAL balance
        INTEGER user_id FK
    }
    Transaction {
        SERIAL id PK
        ENUM type
        DECIMAL amount
        VARCHAR notes
        TIMESTAMP timestamp
        INTEGER account_id FK
    }

    User ||--o{ Account : "has"
    Account ||--o{ Transaction : "contains"
