# Análisis de Datos

## Descripción del sistema
Aplicación para gestionar el alquiler de habitaciones en diferentes pisos. El sistema permitirá controlar inquilinos, habitaciones, contratos, pagos, incidencias y gastos asociados a cada piso.

## Objetivo
Facilitar la gestión de alquileres permitiendo registrar, consultar y administrar toda la información relacionada con los inquilinos y las propiedades.

## Entidades identificadas
- Piso
- Habitacion
- Inquilino
- Contrato
- Pago
- Incidencia
- Gasto

## Descripción de entidades

### Piso
Representa una vivienda que contiene varias habitaciones en alquiler.

### Habitacion
Espacio dentro de un piso que puede ser alquilado por uno o varios inquilinos.

### Inquilino
Persona que alquila una habitación.

### Contrato
Relación entre una habitación y uno o varios inquilinos durante un periodo de tiempo.

### Pago
Registro de los pagos realizados por un contrato.

### Incidencia
Problemas o averías asociados a una habitación.

### Gasto
Costes asociados a un piso (luz, agua, reparaciones, etc.).

## Relaciones
- Un piso tiene muchas habitaciones (1:N)
- Una habitación pertenece a un solo piso (N:1)
- Una habitación puede tener varios contratos (1:N)
- Un contrato puede incluir varios inquilinos (N:M)
- Un inquilino puede tener varios contratos (N:M)
- Un contrato puede tener varios pagos (1:N)
- Una habitación puede tener varias incidencias (1:N)
- Un piso puede tener varios gastos (1:N)

## Observaciones
Se ha considerado la posibilidad de piso compartido, por lo que un contrato puede estar asociado a varios inquilinos. Esto implica la necesidad de una relación N:M que será resuelta mediante una tabla intermedia en el modelo relacional.