## Sistema de Gestión de Inventario

**Descripción:**
Este proyecto consiste en desarrollar un sistema backend para la gestión de inventarios en una empresa. El sistema permitirá administrar productos, categorías, proveedores y gestionar el flujo de inventario a través de movimientos de stock (entradas y salidas). Además, contará con funcionalidades para la autenticación y autorización de usuarios y generación de reportes.


## Requerimientos:

- **Usuarios:**
  - Registro e inicio de sesión.
  - Roles: Administrador y Usuario.
  - Gestión de usuarios con diferentes niveles de acceso.

- **Productos:**
  - CRUD de productos: Crear, leer, actualizar y eliminar productos.
  - Asociar productos a categorías y proveedores.
  - Manejar atributos de productos como nombre, descripción, precio y cantidad en stock.
  - Controlar la cantidad mínima en stock y alertar cuando un producto esté bajo en inventario.
  - Buscar y filtrar productos por diferentes criterios (nombre, categoría, precio, etc.).
  - Visualizar detalles del producto, incluyendo historial de movimientos de stock.
  - Generar reporte PDF del inventario actual
  - Generar reporte PDF del historial de movimientos de stock de cada producto.
  - Permitir cambiar o eliminar la imagen de un producto (En estado de edicion de producto).

- **Categorías:**
  - CRUD de categorias.
  - Las categorías se gestionan con una entidad `Categoria` con campos Id y nombre, y tiene una relacion @ManyToOne con `Producto`.

- **Proveedores:**
  - CRUD de proveedores.
  - Registrar información de contacto de los proveedores.
  - Asociar productos a proveedores.

- **Stock:**
  - Registrar entradas de stock (nuevas adquisiciones, devoluciones, etc.).
  - Registrar salidas de stock (ventas, pérdidas, etc.).
  - Visualizar el historial de movimientos de stock para cada producto.
  - Manejar motivos de movimientos (compra, venta, ajuste, etc.).
  - Actualizar el inventario automáticamente tras cada movimiento.
  - Generar reporte PDF y Excel del historial de movimientos de stock general.

- **Seguridad:**
  - Autenticación y autorización con Spring Security.
  - Gestión de roles y permisos para acceso a diferentes funcionalidades.

- **Persistencia:**
  - Base de datos para almacenar usuarios, productos, proveedores y movimientos de stock.
- **Reportes:**
  - Generar reportes de inventario, mostrando el estado actual de los productos.
  - Reportes de movimientos de stock, con opción de exportación a formatos como PDF o Excel.
  - Reportes de proveedores y productos más comprados/vendidos.

- **Otros:**
  - Paginación y búsqueda de productos.
  - Validación de datos de entrada.
  - Integración con sistemas de notificaciones por correo electrónico para alertas.
