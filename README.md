# Sistema de Gestión de Inventario API

## Descripción

API RESTful para la gestión de inventarios. Permite controlar y administrar productos, proveedores y stock con funcionalidades para agregar, actualizar y eliminar productos, gestionar entradas y salidas de stock, y recibir notificaciones por correo electrónico para alertas de bajo stock. Incluye autenticación y autorización, validaciones de entrada y manejo de imágenes de productos en la nube con firebase.

## Funcionalidades

- **Productos**:
  - Crear, leer, actualizar y eliminar productos.
  - Gestionar imágenes de productos.
  - Buscar y filtrar productos.
  - Exportar reporte PDF del inventario actual.

- **Stock**:
  - Registrar entradas y salidas de stock.
  - Visualizar el historial de movimientos de stock.
  - Notificaciones por correo electrónico para stock bajo.
  - Exportar reporte PDF de movimientos de stock.

- **Proveedores**:
  - Crear, leer, actualizar y eliminar proveedores.
  - Asociar productos con proveedores.

- **Autenticación y Autorización**:
  - Gestión de roles y permisos.

## Requisitos

- Java 17+
- Spring Boot
- Base de datos (MySQL)
