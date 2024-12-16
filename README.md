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
- **MySQL**: Base de datos relacional.
- **Cloudinary**: Almacenamiento de imágenes en la nube.
- **JWT**: Autenticación y autorización.
- **Spring Security**: Gestión de roles y protección de endpoints.

## Endpoints

### Autenticación

| Método | Endpoint         | Descripción                         |
| ------ |------------------| ----------------------------------- |
| POST   | /api/auth/signin | Iniciar sesión y obtener token JWT. |
| POST   | /api/auth/signup | Registrar un nuevo usuario.         |

### Productos

| Método | Endpoint                 | Descripción                              |
| ------ |--------------------------|------------------------------------------|
| GET    | /api/products         | Obtener productos paginados y filtrados. |
| GET    | /api/products/{id}    | Obtener detalles de un producto.         |
| POST   | /api/products         | Agregar nuevo producto al inventario.    |
| PUT    | /api/products/{id}    | Editar un producto existente.            |
| DELETE | /api/products/{id}    | Eliminar un producto.                    |
| GET    | /api/products/reporte | Generar reporte de inventario.           |

### Categorías

| Método | Endpoint               | Descripción                            |
| ------ | ---------------------- | -------------------------------------- |
| GET    | /api/categorias      | Obtener lista de todas las categorías. |
| GET    | /api/categorias/{id} | Ver detalles de una categoría.         |
| POST   | /api/categorias      | Registrar nueva categoría.             |
| PUT    | /api/categorias/{id} | Editar datos de una categoría.         |
| DELETE | /api/categorias/{id} | Eliminar una categoría.                |

### Proveedores

| Método | Endpoint                | Descripción                             |
| ------ | ----------------------- | --------------------------------------- |
| GET    | /api/proveedores      | Obtener lista de todos los proveedores. |
| GET    | /api/proveedores/{id} | Ver detalles de un supplier.           |
| POST   | /api/proveedores      | Registrar nuevo supplier.              |
| PUT    | /api/proveedores/{id} | Editar datos de un supplier.           |
| DELETE | /api/proveedores/{id} | Eliminar un supplier.                  |

### Movimientos

| Método | Endpoint                          | Descripción                                               |
| ------ | --------------------------------- |-----------------------------------------------------------|
| GET    | /api/movements                | Obtener todos los movimientos.                            |
| GET    | /api/movements/entradas       | Obtener las entradas de stock.                            |
| GET    | /api/movements/salidas        | Obtener las salidas de stock.                             |
| DELETE | /api/movements                | Eliminar todos los movimientos.                           |
| DELETE | /api/movements/{id}           | Eliminar un movimiento de stock.                          |
| DELETE | /api/movements/entradas       | Eliminar todas las entradas de stock.                     |
| DELETE | /api/movements/salidas        | Eliminar todas las salidas de stock.                      |
| POST   | /api/movements                | Registrar un movimiento de stock.                         |
| GET    | /api/movements/reporte/{tipo} | Generar reporte de movimiento (general, entrada, salida). |

### Notificaciones

| Método | Endpoint                                | Descripción                         |
| ------ | --------------------------------------- | ----------------------------------- |
| GET    | /api/notificaciones/no-leidas         | Obtener notificaciones no leídas.   |
| GET    | /api/notificaciones                   | Obtener todas las notificaciones.   |
| POST   | /api/notificaciones/{id}/marcar-leida | Marcar una notificación como leída. |
| DELETE | /api/notificaciones/{id}              | Eliminar una notificación.          |
| DELETE | /api/notificaciones                   | Eliminar todas las notificaciones.  |

### Permisos por Rol

#### `USER`
- Puede consultar y registrar recursos básicos, como productos y movimientos de stock.
- No tiene permisos para modificar, eliminar recursos críticos ni gestionar roles.

#### `MOD`
- Puede consultar y registrar recursos.
- Tiene permisos adicionales para eliminar usuarios con rol `USER`.
- No puede gestionar roles ni acceder a configuraciones avanzadas.

#### `ADMIN`
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