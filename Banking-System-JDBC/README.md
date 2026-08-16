# Banking System JDBC

A console-based banking system built with **Java, JDBC, and PostgreSQL**.

This project was built to practice connecting a Java application to a relational database and implementing real database operations through JDBC.

## Features

* Customer management

  * Add customer
  * Find customer
  * Update customer
  * Delete customer
* Account management

  * Open account
  * Find account
* Banking operations

  * Deposit
  * Withdraw
  * Transfer
* Transaction history
* Balance validation
* Insufficient-balance protection
* Database transactions with commit and rollback
* Secure SQL queries using `PreparedStatement`

## Technologies

* Java
* JDBC
* PostgreSQL
* Maven
* IntelliJ IDEA
* Git & GitHub

## Project Structure

```text
Banking-System-JDBC/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── Account.java
│   │   │   ├── AccountDAO.java
│   │   │   ├── BankService.java
│   │   │   ├── Customer.java
│   │   │   ├── CustomerDAO.java
│   │   │   ├── DatabaseConnection.java
│   │   │   ├── Main.java
│   │   │   ├── Menu.java
│   │   │   ├── Transaction.java
│   │   │   └── TransactionDAO.java
│   │   │
│   │   └── resources/
│   │       └── database.properties.example
│   │
│   └── test/
│
├── .gitignore
├── pom.xml
└── README.md
```

## Database Structure

The PostgreSQL database contains three main tables:

```text
customers
    │
    └── accounts
            │
            └── transactions
```

### Customers

Stores customer information such as first name and last name.

### Accounts

Stores account information, balance, account type, and the customer associated with the account.

### Transactions

Stores deposits, withdrawals, and transfers associated with accounts.

## Architecture

The project uses a simple DAO and service-layer structure:

```text
Menu
  ↓
BankService / DAO
  ↓
JDBC
  ↓
PostgreSQL
```

### DAO Layer

The DAO classes handle communication with PostgreSQL:

* `CustomerDAO`
* `AccountDAO`
* `TransactionDAO`

### Service Layer

`BankService` handles operations that involve multiple database changes.

For example, a transfer:

```text
Withdraw from sender
        ↓
Deposit to receiver
        ↓
Create transaction records
        ↓
COMMIT
```

If any part fails, the operation is rolled back.

## Database Transactions

Money operations use database transactions to prevent partial updates.

For example, if a transfer fails after the sender's balance has been changed, the transaction is rolled back so the database returns to its previous state.

## Setup

### 1. Create the PostgreSQL database

Create a PostgreSQL database and run the SQL schema for:

* `customers`
* `accounts`
* `transactions`

### 2. Configure database credentials

Create:

```text
src/main/resources/database.properties
```

based on:

```text
src/main/resources/database.properties.example
```

Add your own PostgreSQL connection details:

```text
db.url=jdbc:postgresql://localhost:5432/your_database
db.username=your_username
db.password=your_password
```

`database.properties` is intentionally excluded from Git using `.gitignore`.

### 3. Run the application

Run `Main.java`.

The application opens a console menu:

```text
===== Banking System =====

1. Open Account
2. Deposit
3. Withdraw
4. Transfer
5. Find Customer
6. Add Customer
7. Update Customer
8. Delete Customer
9. Find Account
10. Show Transactions
11. Exit
```

## What I Practiced

This project helped me practice:

* JDBC database connections
* SQL with PostgreSQL
* CRUD operations
* `PreparedStatement`
* `ResultSet`
* DAO architecture
* Service-layer design
* Database transactions
* Commit and rollback
* Foreign keys
* Transaction history
* `BigDecimal` for monetary values
* Maven project structure
* Git and GitHub

## Future Improvements

Possible future improvements include:

* Better input validation
* Unit and integration tests
* User authentication
* Account ownership validation
* More detailed transaction types
* REST API using Spring Boot
* Web interface
* Dockerized PostgreSQL setup
