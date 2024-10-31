# Inventario API RESTful

## Descripción

Esta API RESTful de Inventario permite gestionar productos, proveedores, entradas/salidas de stock y notificaciones. Proporciona funcionalidades para crear, leer, actualizar y eliminar registros, así como para generar reportes. La API también incluye autenticación basada en JWT para la gestión de roles y permisos.

## Funcionalidades

### Productos

- Crear, leer, actualizar y eliminar productos.
- Gestionar imágenes de productos.
- Buscar y filtrar productos.
- Ver detalles del producto (datos, entradas/salidas de stock y proveedor).
- Conteo del total de productos.
- Exportar reporte PDF del inventario actual.
- Paginación y filtrado avanzado de productos.

### Stock

- Registrar entradas y salidas de stock.
- Visualizar el historial general de movimientos de stock.
- Alertas por correo electrónico para stock bajo.
- Exportar reporte PDF de movimientos de stock.

### Proveedores

- Crear, leer, actualizar y eliminar proveedores.
- Asociar productos con proveedores.
- Ver detalles del proveedor (datos y productos que provee).

### Autenticación y Autorización

- Gestión de roles y permisos.
- Autenticación basada en JWT (JSON Web Tokens).

### Notificaciones

- Almacenamiento de notificaciones en la base de datos.
- Marcar notificaciones como leídas.
- Eliminar notificaciones.

## Endpoints

### Autenticación

| Método | Endpoint         | Descripción                         |
| ------ | ---------------- | ----------------------------------- |
| POST   | `/auth/login`    | Iniciar sesión y obtener token JWT. |
| POST   | `/auth/register` | Registrar un nuevo usuario.         |

### Productos

| Método | Endpoint                    | Descripción                              |
| ------ | --------------------------- | ---------------------------------------- |
| GET    | `/inventario/`              | Obtener productos paginados y filtrados. |
| GET    | `/inventario/detalles/{id}` | Obtener detalles de un producto.         |
| POST   | `/inventario/nuevo`         | Agregar nuevo producto al inventario.    |
| PUT    | `/inventario/editar/{id}`   | Editar un producto existente.            |
| DELETE | `/inventario/eliminar/{id}` | Eliminar un producto.                    |
| GET    | `/inventario/reporte`       | Generar reporte de inventario.           |

### Categorías

| Método | Endpoint                    | Descripción                            |
| ------ | --------------------------- | -------------------------------------- |
| GET    | `/categorias/`              | Obtener lista de todas las categorías. |
| GET    | `/categorias/detalles/{id}` | Ver detalles de una categoría.         |
| POST   | `/categorias/registrar`     | Registrar nueva categoría.             |
| PUT    | `/categorias/editar/{id}`   | Editar datos de una categoría.         |
| DELETE | `/categorias/eliminar/{id}` | Eliminar una categoría.                |

### Proveedores

| Método | Endpoint                     | Descripción                             |
| ------ | ---------------------------- | --------------------------------------- |
| GET    | `/proveedores/`              | Obtener lista de todos los proveedores. |
| GET    | `/proveedores/detalles/{id}` | Ver detalles de un proveedor.           |
| POST   | `/proveedores/registrar`     | Registrar nuevo proveedor.              |
| PUT    | `/proveedores/editar/{id}`   | Editar datos de un proveedor.           |
| DELETE | `/proveedores/eliminar/{id}` | Eliminar un proveedor.                  |

### Notificaciones

| Método | Endpoint                            | Descripción                         |
| ------ | ----------------------------------- | ----------------------------------- |
| GET    | `/notificaciones/no-leidas`         | Obtener notificaciones no leídas.   |
| GET    | `/notificaciones/todas`             | Obtener todas las notificaciones.   |
| POST   | `/notificaciones/marcar-leida/{id}` | Marcar una notificación como leída. |
| DELETE | `/notificaciones/eliminar/{id}`     | Eliminar una notificación.          |
| DELETE | `/notificaciones/eliminar-todas`    | Eliminar todas las notificaciones.  |

### Movimientos

| Método | Endpoint                      | Descripción                                                |
| ------ | ----------------------------- | ---------------------------------------------------------- |
| GET    | `/movimientos/`               | Obtener todos los movimientos.                             |
| GET    | `/movimientos/entradas`       | Obtener las entradas de stock.                             |
| GET    | `/movimientos/salidas`        | Obtener las salidas de stock.                              |
| POST   | `/movimientos/registrar`      | Registrar un movimiento de stock.                          |
| GET    | `/movimientos/reporte/{tipo}` | Generar reporte de movimientos (general, entrada, salida). |

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

## Autor

[FrankSkep](https://github.com/FrankSkep)
