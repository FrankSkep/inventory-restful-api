<h1 align="center">Inventory RESTful API</h1>

## Table of Contents

- [Description](#description)
- [Features](#features)
- [Architecture](#architecture)
- [Endpoints](#endpoints)
  - [Authentication](#authentication)
  - [Products](#products)
  - [Categories](#categories)
  - [Suppliers](#suppliers)
  - [Movements](#movements)
  - [Notifications](#notifications)
- [Role Permissions](#role-permissions)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Developer](#developer)
- [License](#license)

## Description

RESTful API for inventory management, designed to handle products, suppliers, categories, stock movements, and notifications. It uses JWT-based authentication to manage permissions and roles and integrates cloud services like **Cloudinary** for image management.

Includes a **global exception handler** that ensures consistent responses for common errors (authentication, validation, data not found, etc.).

## Features

### Authentication and Authorization

- User registration and login.
- JWT token generation and validation.
- Endpoint protection by roles (`USER`, `MOD`, and `ADMIN`).
- Specific exception handling (invalid token, unauthorized access).

### Products

- Full CRUD for products with pagination and filters.
- Image management via **Cloudinary**.
- PDF reports of the current inventory.
- Association with suppliers and categories.

### Categories

- Full CRUD for categories.
- Hierarchical relationship with products.

### Suppliers

- Full CRUD for suppliers.
- Association with products.

### Movements

- Registration of stock entries and exits.
- Export reports in PDF format.
- Alerts for low stock.

### Notifications

- Centralized notification management.
- System to mark as read or delete them.

### Global Exceptions

- Centralized error handling in:
  - Data validation.
  - Authentication and authorization.
  - Resources not found.
  - Forbidden operations.

## Architecture

The API uses a modular design based on RESTful controllers, business services, and repositories.

### Main Technologies:
- **Spring Boot**: Main framework for the application.
- **JWT**: Authentication and authorization.
- **Spring Security**: Role management and endpoint protection.
- **MySQL**: Relational database.
- **Cloudinary**: Cloud image storage.

## Endpoints

### Authentication

| Method | Endpoint         | Description                         |
| ------ |------------------| ----------------------------------- |
| POST   | /api/auth/signin | Log in and obtain JWT token.        |
| POST   | /api/auth/signup | Register a new user.                |

### Products

| Method | Endpoint                | Description                              |
| ------ |-------------------------|------------------------------------------|
| GET    | /api/products           | Get paginated and filtered products.     |
| GET    | /api/products/{id}      | Get product details.                     |
| POST   | /api/products           | Add a new product to the inventory.      |
| PUT    | /api/products/{id}      | Edit an existing product.                |
| DELETE | /api/products/{id}      | Delete a product.                        |
| GET    | /api/products/report    | Generate inventory report.               |

### Categories

| Method | Endpoint             | Description                            |
| ------ |----------------------| -------------------------------------- |
| GET    | /api/categories      | Get a list of all categories.          |
| GET    | /api/categories/{id} | View category details.                 |
| POST   | /api/categories      | Register a new category.               |
| PUT    | /api/categories/{id} | Edit category details.                 |
| DELETE | /api/categories/{id} | Delete a category.                     |

### Suppliers

| Method | Endpoint            | Description                             |
| ------ |---------------------| --------------------------------------- |
| GET    | /api/suppliers      | Get a list of all suppliers.            |
| GET    | /api/suppliers/{id} | View supplier details.                  |
| POST   | /api/suppliers      | Register a new supplier.                |
| PUT    | /api/suppliers/{id} | Edit supplier details.                  |
| DELETE | /api/suppliers/{id} | Delete a supplier.                      |

### Movements

| Method | Endpoint                           | Description                                               |
| ------ |------------------------------------|-----------------------------------------------------------|
| GET    | /api/stock-movements               | Get all movements.                                        |
| GET    | /api/stock-movements/entries       | Get stock entries.                                        |
| GET    | /api/stock-movements/outputs       | Get stock outputs.                                        |
| DELETE | /api/stock-movements               | Delete all movements.                                     |
| DELETE | /api/stock-movements/{id}          | Delete a stock movement.                                  |
| DELETE | /api/stock-movements/entries       | Delete all stock entries.                                 |
| DELETE | /api/stock-movements/outputs       | Delete all stock outputs.                                 |
| POST   | /api/stock-movements               | Register a stock movement.                                |
| GET    | /api/stock-movements/report/{type} | Generate movement report (general, entry, exit).          |

### Notifications

| Method | Endpoint                     | Description                         |
| ------ |------------------------------| ----------------------------------- |
| GET    | /api/notifications/unread    | Get unread notifications.           |
| GET    | /api/notifications           | Get all notifications.              |
| POST   | /api/notifications/{id}/read | Mark a notification as read.        |
| DELETE | /api/notifications/{id}      | Delete a notification.              |
| DELETE | /api/notifications           | Delete all notifications.           |

### Role Permissions

#### `USER` User
- Can view and register basic resources, such as products and stock movements.
- Cannot modify, delete critical resources, or manage roles.

#### `MOD` Moderator
- Can view and register resources.
- Has additional permissions to delete users with the `USER` role.
- Cannot manage roles or access advanced settings.

#### `ADMIN` Administrator
- Full access to the system, including:
  - Modify and delete any resource.
  - Manage roles (assign or revoke).
  - Delete users with the `USER` and `MOD` roles.

## Requirements

- **Java 17+**
- **Spring Boot**
- **MySQL**
- **Maven**

## Installation

1. Clone the repository:

   ```sh
   git clone https://github.com/FrankSkep/inventory-rest-api
   ```

2. Configure the `.env` file:
   ```properties
   CLOUDINARY_CLOUD_NAME=your_cloud_name
   CLOUDINARY_API_KEY=your_api_key
   CLOUDINARY_API_SECRET=your_api_secret
   DB_URL=jdbc:mysql://localhost:3306/your_database
   DB_USERNAME=your_mysql_user
   DB_PASSWORD=your_mysql_password
   JWT_SECRET_KEY=your_jwt_secret_key
   ```

3. Build and run the project:
   ```sh
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

## Usage

To interact with the API, use tools like Postman or cURL. The endpoints are documented in the corresponding section.

## Developer

[FrankSkep](https://github.com/FrankSkep)

## License

**[GNU Affero General Public License v3.0](https://www.gnu.org/licenses/agpl-3.0.html)**.
© 2024 FrankSkep. See the LICENSE file for more information.