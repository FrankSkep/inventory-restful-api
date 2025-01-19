![Status](https://img.shields.io/badge/status-in%20development-yellow)

# Inventory RESTful API

A RESTful API for inventory management, handling products, suppliers, categories, stock movements, and notifications. It features JWT-based authentication for role-based access control and integrates Cloudinary for image management.

## Table of Contents

- [Features](#features)
- [Architecture](#architecture)
- [Endpoints](#endpoints)
- [Role Permissions](#role-permissions)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Sample Valid JSON Request Bodies](#sample-valid-json-request-bodies)
- [License](#license)

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

- **Spring Boot**: Core framework.
- **Spring Security & JWT**: Authentication and authorization.
- **Spring Data JPA & MySQL**: Database management.
- **Cloudinary**: Image storage.

## Endpoints

### Authentication

| Method | Endpoint         | Description                  | Sample Valid Request Body |
| ------ | ---------------- | ---------------------------- | ------------------------- |
| POST   | /api/auth/signin | Log in and obtain JWT token. | [JSON](#signin)           |
| POST   | /api/auth/signup | Register a new user.         | [JSON](#signup)           |

### Users

| Method | Endpoint            | Description                               | Sample Valid Request Body |
| ------ | ------------------- | ----------------------------------------- | ------------------------- |
| PUT    | /api/users          | Update the authenticated user's details.  | [JSON](#userupdate)       |
| PATCH  | /api/users/{id}     | Update the role of a user (Admin only).   | [JSON](#roleupdate)       |
| DELETE | /api/users          | Delete the authenticated user's account.  |                           |
| DELETE | /api/users/{id}     | Delete a user by ID (Admin only).         |                           |
| PATCH  | /api/users/password | Update the authenticated user's password. | [JSON](#passwordupdate)   |

### Products

| Method | Endpoint             | Description                          | Sample Valid Request Body |
| ------ | -------------------- | ------------------------------------ | ------------------------- |
| GET    | /api/products        | Get paginated and filtered products. |                           |
| GET    | /api/products/{id}   | Get product details.                 |                           |
| POST   | /api/products        | Add a new product to the inventory.  | [JSON](#productcreate)    |
| PUT    | /api/products/{id}   | Edit an existing product.            | [JSON](#productupdate)    |
| DELETE | /api/products/{id}   | Delete a product.                    |                           |
| GET    | /api/products/report | Generate inventory report.           |                           |

### Categories

| Method | Endpoint             | Description                   | Sample Valid Request Body |
| ------ | -------------------- | ----------------------------- | ------------------------- |
| GET    | /api/categories      | Get a list of all categories. |                           |
| GET    | /api/categories/{id} | View category details.        |                           |
| POST   | /api/categories      | Register a new category.      | [JSON](#categorycreate)   |
| PUT    | /api/categories/{id} | Edit category details.        | [JSON](#categoryupdate)   |
| DELETE | /api/categories/{id} | Delete a category.            |                           |

### Suppliers

| Method | Endpoint            | Description                  | Sample Valid Request Body |
| ------ | ------------------- | ---------------------------- | ------------------------- |
| GET    | /api/suppliers      | Get a list of all suppliers. |                           |
| GET    | /api/suppliers/{id} | View supplier details.       |                           |
| POST   | /api/suppliers      | Register a new supplier.     | [JSON](#suppliercreate)   |
| PUT    | /api/suppliers/{id} | Edit supplier details.       | [JSON](#supplierupdate)   |
| DELETE | /api/suppliers/{id} | Delete a supplier.           |                           |

### Movements

| Method | Endpoint                           | Description                                      | Sample Valid Request Body |
| ------ | ---------------------------------- | ------------------------------------------------ | ------------------------- |
| GET    | /api/stock-movements               | Get all movements.                               |                           |
| GET    | /api/stock-movements/entries       | Get stock entries.                               |                           |
| GET    | /api/stock-movements/outputs       | Get stock outputs.                               |                           |
| DELETE | /api/stock-movements               | Delete all movements.                            |                           |
| DELETE | /api/stock-movements/{id}          | Delete a stock movement.                         |                           |
| DELETE | /api/stock-movements/entries       | Delete all stock entries.                        |                           |
| DELETE | /api/stock-movements/outputs       | Delete all stock outputs.                        |                           |
| POST   | /api/stock-movements               | Register a stock movement.                       | [JSON](#movementcreate)   |
| GET    | /api/stock-movements/report/{type} | Generate movement report (general, entry, exit). |                           |

### Notifications

| Method | Endpoint                     | Description                  |
| ------ | ---------------------------- | ---------------------------- |
| GET    | /api/notifications/unread    | Get unread notifications.    |
| GET    | /api/notifications           | Get all notifications.       |
| POST   | /api/notifications/{id}/read | Mark a notification as read. |
| DELETE | /api/notifications/{id}      | Delete a notification.       |
| DELETE | /api/notifications           | Delete all notifications.    |

## Role Permissions

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
- **MySQL**
- **Maven**

## Installation

1. Clone the repository:

   ```sh
   git clone https://github.com/FrankSkep/inventory-rest-api
   ```

2. Create MySQL database:

   ```bash
   create database database_name
   ```

3. Configure the `.env` file:

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

   ```sh
   mvn spring-boot:run
   ```

The app will start running at <http://localhost:8080>

## Usage

To interact with the API, use tools like Postman or cURL. The endpoints are documented in the corresponding section.

## Sample Valid JSON Request Bodies

##### <a id="signup">Sign Up -> /api/auth/signup</a>

```json
{
  "username": "john_doe",
  "password": "Password123",
  "firstname": "John",
  "lastname": "Doe",
  "country": "USA"
}
```

##### <a id="signin">Log In -> /api/auth/signin</a>

```json
{
  "username": "john_doe",
  "password": "Password123"
}
```

##### <a id="userupdate">Update User -> /api/users</a>

```json
{
  "username": "john_doe_updated",
  "firstname": "John",
  "lastname": "Doe",
  "country": "USA"
}
```

##### <a id="roleupdate">Update Role -> /api/users/{id}</a>

```json
"ROLE_NAME"
```

##### <a id="passwordupdate">Update Password -> /api/users/password</a>

```json
{
  "oldPassword": "OldPassword123",
  "newPassword": "NewPassword123"
}
```

##### <a id="productcreate">Create Product -> /api/products</a>

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

##### <a id="productupdate">Update Product -> /api/products/{id}</a>

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

##### <a id="categorycreate">Create Category -> /api/categories</a>

```json
{
  "name": "Category Name"
}
```

##### <a id="categoryupdate">Update Category -> /api/categories/{id}</a>

```json
{
  "name": "Updated Category Name"
}
```

##### <a id="suppliercreate">Create Supplier -> /api/suppliers</a>

```json
{
  "name": "Supplier Name",
  "address": "Supplier Address",
  "email": "supplier@example.com",
  "phone": "+1234567890",
  "taxIdentification": "123456789"
}
```

##### <a id="supplierupdate">Update Supplier -> /api/suppliers/{id}</a>

```json
{
  "name": "Updated Supplier Name",
  "address": "Updated Supplier Address",
  "email": "updated_supplier@example.com",
  "phone": "+0987654321",
  "taxIdentification": "987654321"
}
```

##### <a id="movementcreate">Create Movement -> /api/stock-movements</a>

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

## License

**[GNU Affero General Public License v3.0](https://www.gnu.org/licenses/agpl-3.0.html)**.
© 2024 FrankSkep. See the LICENSE file for more information.
