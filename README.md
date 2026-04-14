# Proyecto: Gestión de Alquileres

##  Descripción
Este proyecto consiste en el diseño e implementación de una base de datos para la gestión de alquileres de pisos y habitaciones.

Permite gestionar:
- Pisos
- Habitaciones
- Inquilinos
- Contratos
- Pagos
- Incidencias

## Modelo Entidad-Relación

El modelo E/R incluye las siguientes entidades:

- **Pisos**: viviendas ubicadas en una ciudad
- **Habitaciones**: pertenecen a un piso
- **Inquilinos**: personas que alquilan habitaciones
- **Contratos**: relación entre inquilinos y alquileres
- **Pagos**: pagos realizados por los contratos
- **Incidencias**: problemas reportados en habitaciones

### Relaciones principales

- Un piso está en una ciudad (N:1)
- Un piso tiene varias habitaciones (1:N)
- Un inquilino genera contratos
- Un contrato tiene pagos
- Un inquilino puede reportar incidencias

## Modelo Relacional

El modelo relacional se ha implementado mediante las siguientes tablas:

- Ciudades
- Pisos
- Habitaciones
- Inquilinos
- Contratos
- Pagos
- Incidencias

Además, se han creado tablas intermedias para relaciones N:M:

- Se_Alquilan
- Realizan
- Reportan
- Generan

### Estructura del proyecto

## Ejecución

1. Ejecutar `base_datos.sql`
2. Ejecutar `datos.sql`
3. Ejecutar `consultas.sql`

##  Ejemplos de consultas

- Listado de pisos con su ciudad
- Inquilinos con contratos
- Pagos realizados
- Incidencias reportadas

##  Tecnologías utilizadas

- SQL (MySQL)
- Modelado E/R

## Nelson Gomez Sanchez

