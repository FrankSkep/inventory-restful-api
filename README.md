# Inventory RESTful API

A RESTful API for inventory management, handling products, suppliers, categories, stock movements, and notifications. It features JWT-based authentication with role-based access control, integrates **Cloudinary** for image management, and includes interactive API documentation via **Springdoc OpenAPI**.

## Table of Contents

* [Features](#features)
* [Architecture](#architecture)
* [API Documentation](#api-documentation)
* [Endpoints](#endpoints)
* [Role Permissions](#role-permissions)
* [Requirements](#requirements)
* [Installation](#installation)
* [Usage](#usage)
* [Sample Valid JSON Request Bodies](#sample-valid-json-request-bodies)
* [License](#license)

## Features

### Authentication & Authorization

* User registration and login.
* JWT token generation and validation.
* Role-based endpoint protection (`USER`, `MOD`, `ADMIN`).
* Specific exception handling (invalid tokens, unauthorized access).

### Products

* Full CRUD with pagination and filters.
* Image management via **Cloudinary**.
* PDF inventory reports.
* Association with suppliers and categories.

### Categories

* Full CRUD operations.
* Hierarchical relationship with products.

### Suppliers

* Full CRUD operations.
* Product association.

### Movements

* Registration of stock entries and outputs.
* Export reports as PDF.
* Low stock alerts.

### Notifications

* Centralized notification management.
* Mark as read or delete.

### Global Exception Handling

* Centralized error handling for:

    * Data validation.
    * Authentication and authorization.
    * Resource not found.
    * Forbidden operations.

## Architecture

This API uses a modular design with:

* **RESTful controllers**: handle requests and responses.
* **Business services**: encapsulate core logic.
* **Repositories**: manage persistence with Spring Data JPA.

### Main Technologies

* **Spring Boot**: core framework.
* **Spring Security & JWT**: authentication and authorization.
* **Spring Data JPA & MySQL**: database management.
* **Cloudinary**: image storage.
* **Springdoc OpenAPI**: auto-generated interactive API documentation.

## API Documentation

Interactive API documentation is available via **Springdoc OpenAPI**, accessible when running the application at:

```
http://localhost:8080/swagger-ui.html
```

This provides detailed information for each endpoint, schemas, and expected responses.

## Endpoints

Below is an overview. For detailed request/response structures, visit the [API Documentation](#api-documentation).

<details>
  <summary>View endpoints</summary>

### Authentication

| Method | Endpoint         | Description                  |
| ------ | ---------------- | ---------------------------- |
| POST   | /api/auth/signin | Log in and obtain JWT token. |
| POST   | /api/auth/signup | Register a new user.         |

### Users

| Method | Endpoint            | Description                           |
| ------ | ------------------- | ------------------------------------- |
| PUT    | /api/users          | Update authenticated user's details.  |
| PATCH  | /api/users/{id}     | Update user role (Admin only).        |
| DELETE | /api/users          | Delete authenticated user's account.  |
| DELETE | /api/users/{id}     | Delete user by ID (Admin only).       |
| PATCH  | /api/users/password | Update authenticated user's password. |

### Products

| Method | Endpoint             | Description                          |
| ------ | -------------------- | ------------------------------------ |
| GET    | /api/products        | Get paginated and filtered products. |
| GET    | /api/products/{id}   | Get product details.                 |
| POST   | /api/products        | Add a new product to inventory.      |
| PUT    | /api/products/{id}   | Edit existing product.               |
| DELETE | /api/products/{id}   | Delete product.                      |
| GET    | /api/products/report | Generate inventory report.           |

### Categories

| Method | Endpoint             | Description            |
| ------ | -------------------- | ---------------------- |
| GET    | /api/categories      | List all categories.   |
| GET    | /api/categories/{id} | View category details. |
| POST   | /api/categories      | Register new category. |
| PUT    | /api/categories/{id} | Edit category.         |
| DELETE | /api/categories/{id} | Delete category.       |

### Suppliers

| Method | Endpoint            | Description            |
| ------ | ------------------- | ---------------------- |
| GET    | /api/suppliers      | List all suppliers.    |
| GET    | /api/suppliers/{id} | View supplier details. |
| POST   | /api/suppliers      | Register new supplier. |
| PUT    | /api/suppliers/{id} | Edit supplier.         |
| DELETE | /api/suppliers/{id} | Delete supplier.       |

### Movements

| Method | Endpoint                           | Description               |
| ------ | ---------------------------------- | ------------------------- |
| GET    | /api/stock-movements               | Get all movements.        |
| GET    | /api/stock-movements/entries       | Get stock entries.        |
| GET    | /api/stock-movements/outputs       | Get stock outputs.        |
| POST   | /api/stock-movements               | Register stock movement.  |
| GET    | /api/stock-movements/report/{type} | Generate movement report. |
| DELETE | /api/stock-movements               | Delete all movements.     |
| DELETE | /api/stock-movements/{id}          | Delete movement by ID.    |
| DELETE | /api/stock-movements/entries       | Delete all stock entries. |
| DELETE | /api/stock-movements/outputs       | Delete all stock outputs. |

### Notifications

| Method | Endpoint                     | Description                |
| ------ | ---------------------------- | -------------------------- |
| GET    | /api/notifications/unread    | Get unread notifications.  |
| GET    | /api/notifications           | Get all notifications.     |
| POST   | /api/notifications/{id}/read | Mark notification as read. |
| DELETE | /api/notifications/{id}      | Delete notification.       |
| DELETE | /api/notifications           | Delete all notifications.  |

</details>

## Role Permissions

#### `USER`

* View and register basic resources (products, stock movements).
* Cannot modify or delete critical resources, nor manage roles.

#### `MOD` (Moderator)

* View and register resources.
* Can delete users with the `USER` role.
* Cannot manage roles or access advanced settings.

#### `ADMIN`

* Full system access:

    * Modify and delete any resource.
    * Manage roles.
    * Delete users with `USER` or `MOD` roles.

## Requirements

* **Java 17+**
* **MySQL**
* **Maven**

## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/FrankSkep/inventory-rest-api
   ```

2. Create the MySQL database:

   ```sql
   create database your_database_name;
   ```

3. Configure environment variables in `.env`:

   ```properties
   CLOUDINARY_CLOUD_NAME=your_cloud_name
   CLOUDINARY_API_KEY=your_api_key
   CLOUDINARY_API_SECRET=your_api_secret
   DB_URL=jdbc:mysql://localhost:3306/your_database
   DB_USERNAME=your_mysql_user
   DB_PASSWORD=your_mysql_password
   JWT_SECRET_KEY=your_jwt_secret_key
   ```

4. Build and run the project:

   ```bash
   mvn spring-boot:run
   ```

The app will start at [http://localhost:8080](http://localhost:8080).

## Usage

Use tools like Postman or cURL to interact with the API. Full endpoint details are available via [API Documentation](#api-documentation).

## Sample Valid JSON Request Bodies

<details>
  <summary>View samples</summary>

##### Sign Up -> /api/auth/signup

```json
{
  "username": "john_doe",
  "password": "Password123",
  "firstname": "John",
  "lastname": "Doe",
  "country": "USA"
}
```

##### Log In -> /api/auth/signin

```json
{
  "username": "john_doe",
  "password": "Password123"
}
```

##### Update User -> /api/users

```json
{
  "username": "john_doe_updated",
  "firstname": "John",
  "lastname": "Doe",
  "country": "USA"
}
```

##### Update Role -> /api/users/{id}

```json
"ROLE_NAME"
```

##### Update Password -> /api/users/password

```json
{
  "oldPassword": "OldPassword123",
  "newPassword": "NewPassword123"
}
```

##### Create Product -> /api/products

Content-Type: multipart/form-data

```json
{
  "name": "Product Name",
  "description": "Product Description",
  "price": 100.0,
  "stock": 10,
  "category": "Category Name",
  "supplierId": 1,
  "minStock": 5,
  "image": "(binary image file)"
}
```

##### Update Product -> /api/products/{id}

Content-Type: multipart/form-data

```json
{
  "name": "Updated Product Name",
  "description": "Updated Product Description",
  "price": 150.0,
  "stock": 15,
  "category": "Updated Category Name",
  "supplierId": 1,
  "minStock": 5,
  "image": "(binary image file)"
}
```

##### Create Category -> /api/categories

```json
{
  "name": "Category Name"
}
```

##### Update Category -> /api/categories/{id}

```json
{
  "name": "Updated Category Name"
}
```

##### Create Supplier -> /api/suppliers

```json
{
  "name": "Supplier Name",
  "address": "Supplier Address",
  "email": "supplier@example.com",
  "phone": "+1234567890",
  "taxIdentification": "123456789"
}
```

##### Update Supplier -> /api/suppliers/{id}

```json
{
  "name": "Updated Supplier Name",
  "address": "Updated Supplier Address",
  "email": "updated_supplier@example.com",
  "phone": "+0987654321",
  "taxIdentification": "987654321"
}
```

##### Create Movement -> /api/stock-movements

```json
{
  "type": "ENTRY",
  "date": "2024-10-01T12:53:21.086131",
  "reason": "Stock replenishment",
  "quantity": 100,
  "product": {
    "id": 1
  },
  "acquisitionCost": 50.0
}
```

</details>

## License

**[GNU Affero General Public License v3.0](https://www.gnu.org/licenses/agpl-3.0.html)**
© 2024 FrankSkep. See the LICENSE file for more information.