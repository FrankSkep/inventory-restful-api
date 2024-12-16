<h1 align="center">API RESTful Inventario</h1>

## Tabla de Contenidos

- [Descripción](#descripción)
- [Funcionalidades](#funcionalidades)
- [Arquitectura](#arquitectura)
- [Endpoints](#endpoints)
  - [Autenticación](#autenticación)
  - [Productos](#productos)
  - [Categorías](#categorías)
  - [Proveedores](#proveedores)
  - [Movimientos](#movimientos)
  - [Notificaciones](#notificaciones)
- [Permisos por Rol](#permisos-por-rol)
- [Requisitos](#requisitos)
- [Instalación](#instalación)
- [Uso](#uso)
- [Desarrollador](#desarrollador)
- [Licencia](#licencia)

## Descripción

API RESTful para la gestión de inventarios, diseñada para manejar productos, proveedores, categorías, movimientos de stock y notificaciones. Utiliza autenticación basada en JWT para gestionar permisos y roles, e integra servicios en la nube como **Cloudinary** para la gestión de imágenes.

Incluye un **manejador de excepciones global** que garantiza respuestas consistentes en errores comunes (autenticación, validación, datos no encontrados, etc.).

## Funcionalidades

### Autenticación y Autorización

- Registro e inicio de sesión de usuarios.
- Generación y validación de tokens JWT.
- Protección de endpoints por roles (`USER`, `MOD` y `ADMIN`).
- Manejo de excepciones específicas (token inválido, acceso no autorizado).

### Productos

- CRUD completo de productos con paginación y filtros.
- Gestión de imágenes mediante **Cloudinary**.
- Reportes PDF del inventario actual.
- Asociación con proveedores y categorías.

### Categorías

- CRUD de categorías.
- Relación jerárquica con productos.

### Proveedores

- CRUD de proveedores.
- Asociación con productos.

### Movimientos

- Registro de entradas y salidas de stock.
- Exportación de reportes en formato PDF.
- Alertas para stock bajo.

### Notificaciones

- Gestión centralizada de notificaciones.
- Sistema para marcar como leídas o eliminarlas.

### Excepciones Globales

- Manejo centralizado de errores en:
  - Validación de datos.
  - Autenticación y autorización.
  - Recursos no encontrados.
  - Operaciones no permitidas.

## Arquitectura

La API utiliza un diseño modular basado en controladores RESTful, servicios de negocio y repositorios.

### Principales tecnologías:
- **Spring Boot**: Framework principal para la aplicación.
- **JWT**: Autenticación y autorización.
- **Spring Security**: Gestión de roles y protección de endpoints.
- **MySQL**: Base de datos relacional.
- **Cloudinary**: Almacenamiento de imágenes en la nube.

## Endpoints

### Autenticación

| Método | Endpoint         | Descripción                         |
| ------ |------------------| ----------------------------------- |
| POST   | /api/auth/signin | Iniciar sesión y obtener token JWT. |
| POST   | /api/auth/signup | Registrar un nuevo usuario.         |

### Productos

| Método | Endpoint                | Descripción                              |
| ------ |-------------------------|------------------------------------------|
| GET    | /api/products           | Obtener productos paginados y filtrados. |
| GET    | /api/products/{id}      | Obtener detalles de un producto.         |
| POST   | /api/products           | Agregar nuevo producto al inventario.    |
| PUT    | /api/products/{id}      | Editar un producto existente.            |
| DELETE | /api/products/{id}      | Eliminar un producto.                    |
| GET    | /api/products/report    | Generar reporte de inventario.           |

### Categorías

| Método | Endpoint             | Descripción                            |
| ------ |----------------------| -------------------------------------- |
| GET    | /api/categories      | Obtener lista de todas las categorías. |
| GET    | /api/categories/{id} | Ver detalles de una categoría.         |
| POST   | /api/categories      | Registrar nueva categoría.             |
| PUT    | /api/categories/{id} | Editar datos de una categoría.         |
| DELETE | /api/categories/{id} | Eliminar una categoría.                |

### Proveedores

| Método | Endpoint            | Descripción                             |
| ------ |---------------------| --------------------------------------- |
| GET    | /api/suppliers      | Obtener lista de todos los proveedores. |
| GET    | /api/suppliers/{id} | Ver detalles de un proveedor.           |
| POST   | /api/suppliers      | Registrar nuevo proveedor.              |
| PUT    | /api/suppliers/{id} | Editar datos de un proveedor.           |
| DELETE | /api/suppliers/{id} | Eliminar un proveedor.                  |

### Movimientos

| Método | Endpoint                           | Descripción                                               |
| ------ |------------------------------------|-----------------------------------------------------------|
| GET    | /api/stock-movements               | Obtener todos los movimientos.                            |
| GET    | /api/stock-movements/entries       | Obtener las entradas de stock.                            |
| GET    | /api/stock-movements/outputs       | Obtener las salidas de stock.                             |
| DELETE | /api/stock-movements               | Eliminar todos los movimientos.                           |
| DELETE | /api/stock-movements/{id}          | Eliminar un movimiento de stock.                          |
| DELETE | /api/stock-movements/entries       | Eliminar todas las entradas de stock.                     |
| DELETE | /api/stock-movements/outputs       | Eliminar todas las salidas de stock.                      |
| POST   | /api/stock-movements               | Registrar un movimiento de stock.                         |
| GET    | /api/stock-movements/report/{type} | Generar reporte de movimiento (general, entrada, salida). |

### Notificaciones

| Método | Endpoint                     | Descripción                         |
| ------ |------------------------------| ----------------------------------- |
| GET    | /api/notifications/unread    | Obtener notificaciones no leídas.   |
| GET    | /api/notifications           | Obtener todas las notificaciones.   |
| POST   | /api/notifications/{id}/read | Marcar una notificación como leída. |
| DELETE | /api/notifications/{id}      | Eliminar una notificación.          |
| DELETE | /api/notifications           | Eliminar todas las notificaciones.  |

### Permisos por Rol

#### `USER` Usuario
- Puede consultar y registrar recursos básicos, como productos y movimientos de stock.
- No tiene permisos para modificar, eliminar recursos críticos ni gestionar roles.

#### `MOD` Moderador
- Puede consultar y registrar recursos.
- Tiene permisos adicionales para eliminar usuarios con rol `USER`.
- No puede gestionar roles ni acceder a configuraciones avanzadas.

#### `ADMIN` Administrador
- Acceso total al sistema, incluyendo:
  - Modificar y eliminar cualquier recurso.
  - Gestionar roles (asignar o revocar).
  - Eliminar usuarios con rol `USER` y `MOD`.

## Requisitos

- **Java 17+**
- **Spring Boot**
- **MySQL**
- **Maven**

## Instalación

1. Clona el repositorio:

   ```sh
   git clone https://github.com/FrankSkep/inventory-rest-api
   ```

2. Configura el archivo `.env`:
   ```properties
   CLOUDINARY_CLOUD_NAME=tu_cloud_name
   CLOUDINARY_API_KEY=tu_api_key
   CLOUDINARY_API_SECRET=tu_api_secret
   DB_URL=jdbc:mysql://localhost:3306/tu_base_de_datos
   DB_USERNAME=tu_usuario_mysql
   DB_PASSWORD=tu_contraseña_mysql
   JWT_SECRET_KEY=tu_jwt_secret_key
   ```

3. Construye y ejecuta el proyecto:
   ```sh
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

## Uso

Para interactuar con la API, usa herramientas como Postman o cURL. Los endpoints se encuentran documentados en la sección correspondiente.

## Desarrollador

[FrankSkep](https://github.com/FrankSkep)

## Licencia

**[GNU Affero General Public License v3.0](https://www.gnu.org/licenses/agpl-3.0.html)**.
© 2024 FrankSkep. Consultar el archivo [LICENSE](LICENSE) para más información.