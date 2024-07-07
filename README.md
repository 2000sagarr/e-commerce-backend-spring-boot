# E-commerce Backend with Spring Boot

## Overview
This repository contains the backend code for an e-commerce application built using Spring Boot. The application is designed to handle various functionalities essential for an online store, including user authentication, product management, order processing, and more.

## Features
- **User Management**: Registration, login, and profile management.
- **Product Management**: CRUD operations for products.
- **Order Management**: Placing, viewing, and managing orders.
- **Category Management**: Organize products into categories.
- **Security**: JWT-based authentication and authorization.
- **Database Integration**: MySQL database for storing application data.

## Prerequisites
- Java 11 or higher
- Maven 3.6.0 or higher
- MySQL database

## Installation

1. **Clone the repository**
    ```sh
    git clone https://github.com/2000sagarr/e-commerce-backend-spring-boot.git
    cd e-commerce-backend-spring-boot
    ```

2. **Configure the database**
    - Create a MySQL database named `ecommerce`.
    - Update the `application.properties` file in the `src/main/resources` directory with your database credentials.
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
    ```

3. **Build the project**
    ```sh
    mvn clean install
    ```

4. **Run the application**
    ```sh
    mvn spring-boot:run
    ```

## API Endpoints

### User Endpoints
- **Register**: `POST /api/users/register`
- **Login**: `POST /api/users/login`
- **Get Profile**: `GET /api/users/profile`

### Product Endpoints
- **Get All Products**: `GET /api/products`
- **Get Product by ID**: `GET /api/products/{id}`
- **Create Product**: `POST /api/products`
- **Update Product**: `PUT /api/products/{id}`
- **Delete Product**: `DELETE /api/products/{id}`

### Order Endpoints
- **Place Order**: `POST /api/orders`
- **Get User Orders**: `GET /api/orders/user/{userId}`
- **Get Order by ID**: `GET /api/orders/{id}`

### Category Endpoints
- **Get All Categories**: `GET /api/categories`
- **Get Category by ID**: `GET /api/categories/{id}`
- **Create Category**: `POST /api/categories`
- **Update Category**: `PUT /api/categories/{id}`
- **Delete Category**: `DELETE /api/categories/{id}`

## Technologies Used
- **Spring Boot**: Framework for building Java-based applications.
- **Spring Security**: Provides authentication and authorization.
- **MySQL**: Database for storing application data.
- **Hibernate**: ORM framework for database operations.
- **JWT**: JSON Web Tokens for secure user authentication.
