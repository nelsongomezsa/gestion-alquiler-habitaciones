
BASE DE DATOS: GESTIÓN DE ALQUILERES


CREATE DATABASE gestion_alquileres;
USE gestion_alquileres;


TABLA: CIUDADES

CREATE TABLE Ciudades (
    id_ciudad INT PRIMARY KEY,
    nombre_ciudad VARCHAR(100)
);


TABLA: PISOS

CREATE TABLE Pisos (
    id_piso INT PRIMARY KEY,
    direccion VARCHAR(150),
    numero_habitaciones INT,
    id_ciudad INT,
    FOREIGN KEY (id_ciudad) REFERENCES Ciudades(id_ciudad)
);


TABLA: HABITACIONES

CREATE TABLE Habitaciones (
    id_habitacion INT PRIMARY KEY,
    id_piso INT,
    id_cliente INT,
    FOREIGN KEY (id_piso) REFERENCES Pisos(id_piso)
);


TABLA: INQUILINOS

CREATE TABLE Inquilinos (
    id_cliente INT PRIMARY KEY,
    nombre_cliente VARCHAR(100),
    apellido_cliente VARCHAR(100),
    DNI VARCHAR(20),
    id_habitacion INT,
    id_contrato INT
);


TABLA: CONTRATOS

CREATE TABLE Contratos (
    id_contrato INT PRIMARY KEY,
    id_cliente INT,
    fecha_inicio DATE,
    fecha_fin DATE,
    FOREIGN KEY (id_cliente) REFERENCES Inquilinos(id_cliente)
);

TABLA: PAGOS

CREATE TABLE Pagos (
    id_pago INT PRIMARY KEY,
    id_contrato INT,
    cantidad DECIMAL(10,2),
    metodo_pago VARCHAR(50),
    fecha_pago DATE,
    FOREIGN KEY (id_contrato) REFERENCES Contratos(id_contrato)
);


TABLA: INCIDENCIAS

CREATE TABLE Incidencias (
    id_incidencia INT PRIMARY KEY,
    id_habitacion INT,
    descripcion VARCHAR(255),
    estado VARCHAR(50),
    FOREIGN KEY (id_habitacion) REFERENCES Habitaciones(id_habitacion)
);


RELACIÓN N:M: SE_ALQUILAN

CREATE TABLE Se_Alquilan (
    id_habitacion INT,
    id_cliente INT,
    PRIMARY KEY (id_habitacion, id_cliente),
    FOREIGN KEY (id_habitacion) REFERENCES Habitaciones(id_habitacion),
    FOREIGN KEY (id_cliente) REFERENCES Inquilinos(id_cliente)
);


RELACIÓN N:M: REALIZAN

CREATE TABLE Realizan (
    id_cliente INT,
    id_pago INT,
    PRIMARY KEY (id_cliente, id_pago),
    FOREIGN KEY (id_cliente) REFERENCES Inquilinos(id_cliente),
    FOREIGN KEY (id_pago) REFERENCES Pagos(id_pago)
);

RELACIÓN N:M: REPORTAN

CREATE TABLE Reportan (
    id_cliente INT,
    id_incidencia INT,
    PRIMARY KEY (id_cliente, id_incidencia),
    FOREIGN KEY (id_cliente) REFERENCES Inquilinos(id_cliente),
    FOREIGN KEY (id_incidencia) REFERENCES Incidencias(id_incidencia)
);

RELACIÓN N:M: GENERAN
CREATE TABLE Generan (
    id_cliente INT,
    id_contrato INT,
    PRIMARY KEY (id_cliente, id_contrato),
    FOREIGN KEY (id_cliente) REFERENCES Inquilinos(id_cliente),
    FOREIGN KEY (id_contrato) REFERENCES Contratos(id_contrato)
);