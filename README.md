<h1 align="center">API RESTful Inventario</h1>

## Tabla de Contenidos

- [Descripción](#descripción)
- [Funcionalidades](#funcionalidades)
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

## Descripción

Esta API RESTful de Inventario permite gestionar productos, proveedores, entradas/salidas de stock y notificaciones. Proporciona funcionalidades para crear, leer, actualizar y eliminar registros, generacion de reportes y gestion de imagenes de productos en la nube con Cloudinary. La API incluye autenticación basada en JWT para la gestión de roles y permisos.

## Funcionalidades

### Autenticación y Autorización

- Registro de nuevos usuarios.
- Inicio de sesión con credenciales de usuario.
- Generación y validación de tokens JWT para autenticación.
- Protección de endpoints mediante roles (`USER` y `ADMIN`).
- Manejo de excepciones de autenticación y autorización.

### Productos

- Crear, leer, actualizar y eliminar productos.
- Gestionar imágenes de productos.
- Buscar y filtrar productos.
- Ver detalles del producto (datos, movimientos de stock y proveedor).
- Conteo del total de productos.
- Exportar reporte PDF del inventario actual.
- Paginación de productos.

### Categorias

- Crear, leer, actualizar y eliminar categorias de productos.

### Proveedores

- Crear, leer, actualizar y eliminar proveedores.
- Asociar productos con proveedores.
- Ver detalles del proveedor (datos y productos).

### Movimientos

- Registrar entradas y salidas de stock.
- Visualizar el historial general de movimientos de stock.
- Notificación para stock bajo.
- Exportar reporte PDF de movimientos de stock (Entrada / Salida / General).

### Notificaciones

- Almacenamiento de notificaciones en la base de datos.
- Marcar notificaciones como leídas.
- Eliminar notificaciones.

## Endpoints

### Autenticación

| Método | Endpoint             | Descripción                         |
| ------ | -------------------- | ----------------------------------- |
| POST   | `/api/auth/login`    | Iniciar sesión y obtener token JWT. |
| POST   | `/api/auth/register` | Registrar un nuevo usuario.         |

### Productos

| Método | Endpoint                 | Descripción                              |
| ------ |--------------------------| ---------------------------------------- |
| GET    | `/api/productos`         | Obtener productos paginados y filtrados. |
| GET    | `/api/productos/{id}`    | Obtener detalles de un producto.         |
| POST   | `/api/productos`         | Agregar nuevo producto al inventario.    |
| PUT    | `/api/productos/{id}`    | Editar un producto existente.            |
| DELETE | `/api/productos/{id}`    | Eliminar un producto.                    |
| GET    | `/api/productos/reporte` | Generar reporte de inventario.           |

### Categorías

| Método | Endpoint               | Descripción                            |
| ------ | ---------------------- | -------------------------------------- |
| GET    | `/api/categorias`      | Obtener lista de todas las categorías. |
| GET    | `/api/categorias/{id}` | Ver detalles de una categoría.         |
| POST   | `/api/categorias`      | Registrar nueva categoría.             |
| PUT    | `/api/categorias/{id}` | Editar datos de una categoría.         |
| DELETE | `/api/categorias/{id}` | Eliminar una categoría.                |

### Proveedores

| Método | Endpoint                | Descripción                             |
| ------ | ----------------------- | --------------------------------------- |
| GET    | `/api/proveedores`      | Obtener lista de todos los proveedores. |
| GET    | `/api/proveedores/{id}` | Ver detalles de un proveedor.           |
| POST   | `/api/proveedores`      | Registrar nuevo proveedor.              |
| PUT    | `/api/proveedores/{id}` | Editar datos de un proveedor.           |
| DELETE | `/api/proveedores/{id}` | Eliminar un proveedor.                  |

### Movimientos

| Método | Endpoint                          | Descripción                                                |
| ------ | --------------------------------- | ---------------------------------------------------------- |
| GET    | `/api/movimientos`                | Obtener todos los movimientos.                             |
| GET    | `/api/movimientos/entradas`       | Obtener las entradas de stock.                             |
| GET    | `/api/movimientos/salidas`        | Obtener las salidas de stock.                              |
| DELETE | `/api/movimientos`                | Eliminar todos los movimientos.                            |
| DELETE | `/api/movimientos/{id}`           | Eliminar un movimiento de stock.                           |
| DELETE | `/api/movimientos/entradas`       | Eliminar todas las entradas de stock.                      |
| DELETE | `/api/movimientos/salidas`        | Eliminar todas las salidas de stock.                       |
| POST   | `/api/movimientos`                | Registrar un movimiento de stock.                          |
| GET    | `/api/movimientos/reporte/{tipo}` | Generar reporte de movimientos (general, entrada, salida). |

### Notificaciones

| Método | Endpoint                                | Descripción                         |
| ------ | --------------------------------------- | ----------------------------------- |
| GET    | `/api/notificaciones/no-leidas`         | Obtener notificaciones no leídas.   |
| GET    | `/api/notificaciones`                   | Obtener todas las notificaciones.   |
| POST   | `/api/notificaciones/{id}/marcar-leida` | Marcar una notificación como leída. |
| DELETE | `/api/notificaciones/{id}`              | Eliminar una notificación.          |
| DELETE | `/api/notificaciones`                   | Eliminar todas las notificaciones.  |

## Permisos por Rol

### `USER`

El rol `USER` tiene permisos para consultar y realizar operaciones de bajo impacto en el sistema, especialmente aquellas relacionadas con el acceso a datos, la consulta de información y la creación de registros de stock que puedan ser revisados o auditados posteriormente por un `ADMIN`.

### `ADMIN`

El rol `ADMIN` tiene acceso total, incluyendo permisos para modificar, crear y eliminar recursos críticos, como productos y proveedores. También debe gestionar el inventario y los movimientos de stock de manera completa.

### Resumen de Permisos por Rol

| Endpoint                                | Método | `USER` | `ADMIN` |
| --------------------------------------- | ------ | ------ | ------- |
| `/productos`                            | GET    | ✔️     | ✔️      |
| `/productos/{id}`                       | GET    | ✔️     | ✔️      |
| `/productos`                            | POST   |        | ✔️      |
| `/productos/{id}`                       | PUT    |        | ✔️      |
| `/productos/{id}`                       | DELETE |        | ✔️      |
| `/productos/reporte`                    | GET    | ✔️     | ✔️      |
| `/api/categorias`                       | GET    | ✔️     | ✔️      |
| `/api/categorias/{id}`                  | GET    | ✔️     | ✔️      |
| `/api/categorias`                       | POST   |        | ✔️      |
| `/api/categorias/{id}`                  | PUT    |        | ✔️      |
| `/api/categorias/{id}`                  | DELETE |        | ✔️      |
| `/api/proveedores`                      | GET    | ✔️     | ✔️      |
| `/api/proveedores/{id}`                 | GET    | ✔️     | ✔️      |
| `/api/proveedores`                      | POST   |        | ✔️      |
| `/api/proveedores/{id}`                 | PUT    |        | ✔️      |
| `/api/proveedores/{id}`                 | DELETE |        | ✔️      |
| `/api/notificaciones/no-leidas`         | GET    | ✔️     | ✔️      |
| `/api/notificaciones`                   | GET    | ✔️     | ✔️      |
| `/api/notificaciones/{id}/marcar-leida` | POST   | ✔️     | ✔️      |
| `/api/notificaciones/{id}`              | DELETE |        | ✔️      |
| `/api/notificaciones`                   | DELETE |        | ✔️      |
| `/api/movimientos`                      | GET    | ✔️     | ✔️      |
| `/api/movimientos/entradas`             | GET    | ✔️     | ✔️      |
| `/api/movimientos/salidas`              | GET    | ✔️     | ✔️      |
| `/api/movimientos`                      | POST   | ✔️     | ✔️      |
| `/api/movimientos/{id}`                 | DELETE |        | ✔️      |
| `/api/movimientos/entradas`             | DELETE |        | ✔️      |
| `/api/movimientos/salidas`              | DELETE |        | ✔️      |
| `/api/movimientos/reporte/{tipo}`       | GET    | ✔️     | ✔️      |

## Requisitos

- Java 17+
- Spring Boot
- MySQL

## Instalación

1. Clona el repositorio:

   ```sh
   git clone https://github.com/FrankSkep/inventory-rest-api
   ```

2. Navega al directorio del proyecto:

   ```sh
   cd inventory-rest-api
   ```

3. Configura el archivo `.env.template` con tus credenciales y renómbralo a `.env`:

   ```sh
   mv .env.template .env
   ```

   Abre el archivo `.env` y completa las siguientes variables de entorno con tus credenciales:

   ```properties
   CLOUDINARY_CLOUD_NAME=tu_cloud_name
   CLOUDINARY_API_KEY=tu_api_key
   CLOUDINARY_API_SECRET=tu_api_secret
   DB_URL=jdbc:mysql://localhost:3306/tu_base_de_datos
   DB_USERNAME=tu_usuario_mysql
   DB_PASSWORD=tu_contraseña_mysql
   JWT_SECRET_KEY=tu_jwt_secret_key
   ```

4. Construye el proyecto utilizando Maven:

   ```sh
   ./mvnw clean install
   ```

5. Ejecuta el proyecto:

   ```sh
   ./mvnw spring-boot:run
   ```

6. Accede a la API en `http://localhost:8080`.

## Uso

Para interactuar con la API, puedes usar herramientas como [Postman](https://www.postman.com/) o [cURL](https://curl.se/).

## Desarrollador

[FrankSkep](https://github.com/FrankSkep)
