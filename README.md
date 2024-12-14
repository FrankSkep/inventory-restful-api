<h1 align="center">API RESTful Inventario</h1>

## Tabla de Contenidos

- [Descripción](#descripción)
- [Funcionalidades](#funcionalidades)
- [Endpoints](#endpoints)
  - [Autenticación](#autenticación)
  - [Productos](#products)
  - [Categorías](#categorías)
  - [Proveedores](#proveedores)
  - [Movimientos](#movements)
  - [Notificaciones](#notificaciones)
- [Permisos por Rol](#permisos-por-rol)
- [Requisitos](#requisitos)
- [Instalación](#instalación)

## Descripción

Esta API RESTful de Inventario permite gestionar products, proveedores, entradas/salidas de stock y notificaciones. Proporciona funcionalidades para crear, leer, actualizar y eliminar registros, generacion de reportes y gestion de imagenes de products en la nube con Cloudinary. La API incluye autenticación basada en JWT para la gestión de roles y permisos.

## Funcionalidades

### Autenticación y Autorización

- Registro de nuevos usuarios.
- Inicio de sesión con credenciales de usuario.
- Generación y validación de tokens JWT para autenticación.
- Protección de endpoints mediante roles (`USER` y `ADMIN`).
- Manejo de excepciones de autenticación y autorización.

### Productos

- Crear, leer, actualizar y eliminar products.
- Gestionar imágenes de products.
- Buscar y filtrar products.
- Ver detalles del product (datos, movements de stock y supplier).
- Conteo del total de products.
- Exportar reporte PDF del inventario actual.
- Paginación de products.

### Categorias

- Crear, leer, actualizar y eliminar categorias de products.

### Proveedores

- Crear, leer, actualizar y eliminar proveedores.
- Asociar products con proveedores.
- Ver detalles del supplier (datos y products).

### Movimientos

- Registrar entradas y salidas de stock.
- Visualizar el historial general de movements de stock.
- Notificación para stock bajo.
- Exportar reporte PDF de movements de stock (Entrada / Salida / General).

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
| GET    | `/api/products`         | Obtener products paginados y filtrados. |
| GET    | `/api/products/{id}`    | Obtener detalles de un product.         |
| POST   | `/api/products`         | Agregar nuevo product al inventario.    |
| PUT    | `/api/products/{id}`    | Editar un product existente.            |
| DELETE | `/api/products/{id}`    | Eliminar un product.                    |
| GET    | `/api/products/reporte` | Generar reporte de inventario.           |

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
| GET    | `/api/proveedores/{id}` | Ver detalles de un supplier.           |
| POST   | `/api/proveedores`      | Registrar nuevo supplier.              |
| PUT    | `/api/proveedores/{id}` | Editar datos de un supplier.           |
| DELETE | `/api/proveedores/{id}` | Eliminar un supplier.                  |

### Movimientos

| Método | Endpoint                          | Descripción                                                |
| ------ | --------------------------------- | ---------------------------------------------------------- |
| GET    | `/api/movements`                | Obtener todos los movements.                             |
| GET    | `/api/movements/entradas`       | Obtener las entradas de stock.                             |
| GET    | `/api/movements/salidas`        | Obtener las salidas de stock.                              |
| DELETE | `/api/movements`                | Eliminar todos los movements.                            |
| DELETE | `/api/movements/{id}`           | Eliminar un movement de stock.                           |
| DELETE | `/api/movements/entradas`       | Eliminar todas las entradas de stock.                      |
| DELETE | `/api/movements/salidas`        | Eliminar todas las salidas de stock.                       |
| POST   | `/api/movements`                | Registrar un movement de stock.                          |
| GET    | `/api/movements/reporte/{tipo}` | Generar reporte de movements (general, entrada, salida). |

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

El rol `ADMIN` tiene acceso total, incluyendo permisos para modificar, crear y eliminar recursos críticos, como products y proveedores. También debe gestionar el inventario y los movements de stock de manera completa.

### Resumen de Permisos por Rol

| Endpoint                                | Método | `USER` | `ADMIN` |
| --------------------------------------- | ------ | ------ | ------- |
| `/products`                            | GET    | ✔️     | ✔️      |
| `/products/{id}`                       | GET    | ✔️     | ✔️      |
| `/products`                            | POST   |        | ✔️      |
| `/products/{id}`                       | PUT    |        | ✔️      |
| `/products/{id}`                       | DELETE |        | ✔️      |
| `/products/reporte`                    | GET    | ✔️     | ✔️      |
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
| `/api/movements`                      | GET    | ✔️     | ✔️      |
| `/api/movements/entradas`             | GET    | ✔️     | ✔️      |
| `/api/movements/salidas`              | GET    | ✔️     | ✔️      |
| `/api/movements`                      | POST   | ✔️     | ✔️      |
| `/api/movements/{id}`                 | DELETE |        | ✔️      |
| `/api/movements/entradas`             | DELETE |        | ✔️      |
| `/api/movements/salidas`              | DELETE |        | ✔️      |
| `/api/movements/reporte/{tipo}`       | GET    | ✔️     | ✔️      |

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

## Licencia

**[GNU Affero General Public License v3.0](https://www.gnu.org/licenses/agpl-3.0.html)**.  

© 2024 FrankSkep. Consultar el archivo [LICENSE](LICENSE) para mas informacion.
