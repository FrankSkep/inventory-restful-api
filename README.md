# Gestion de Inventario - APIREST

## Descripción

API RESTful para la gestión de inventarios. Permite controlar y administrar productos, proveedores y stock con funcionalidades para agregar, editar y eliminar productos, gestionar entradas y salidas de stock, agregar, editar y eliminar proveedores, y recibir alertas por correo electrónico. Incluye autenticación y autorización, validaciones de entrada y manejo de imágenes de productos en Cloudinary.

## Funcionalidades

- **Productos**:
  - Crear, leer, actualizar y eliminar productos.
  - Gestionar imágenes de productos.
  - Buscar y filtrar productos.
  - Ver detalles del producto (datos, entradas/salidas de stock y proveedor).
  - Conteo del total de productos
  - Exportar reporte PDF del inventario actual.

- **Stock**:
  - Registrar entradas y salidas de stock.
  - Visualizar el historial general de movimientos de stock.
  - Alertas por correo electrónico para stock bajo.
  - Exportar reporte PDF de movimientos de stock.

- **Proveedores**:
  - Crear, leer, actualizar y eliminar proveedores.
  - Asociar productos con proveedores.
  - Ver detalles del proveedor (datos y productos que provee).

- **Autenticación y Autorización**:
  - Gestión de roles y permisos.

## Requisitos

- Java 17+
- Spring Boot
- MySQL
