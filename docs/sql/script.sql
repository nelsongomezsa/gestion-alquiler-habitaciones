CREATE DATABASE gestion_alquiler;
USE gestion_alquiler;

TABLA CIUDADES

CREATE TABLE ciudades (
  id_ciudad INT AUTO_INCREMENT PRIMARY KEY,
  nombre_ciudad VARCHAR(100)
);


TABLA PISOS
CREATE TABLE pisos (
  id_piso INT AUTO_INCREMENT PRIMARY KEY,
  direccion VARCHAR(100),
  numero_habitaciones INT,
  id_ciudad INT,
  FOREIGN KEY (id_ciudad) REFERENCES ciudades(id_ciudad)
);
 TABLA HABITACIONES

CREATE TABLE habitaciones (
  id_habitacion INT AUTO_INCREMENT PRIMARY KEY,
  id_piso INT,
  estado VARCHAR(50),
  FOREIGN KEY (id_piso) REFERENCES pisos(id_piso)
);

TABLA INQUILINOS

CREATE TABLE inquilinos (
  id_inquilino INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100),
  apellido VARCHAR(100),
  dni VARCHAR(20)
);


TABLA CONTRATOS

CREATE TABLE contratos (
  id_contrato INT AUTO_INCREMENT PRIMARY KEY,
  fecha_inicio DATE,
  fecha_fin DATE
);


TABLA INTERMEDIA (N:M)

CREATE TABLE inquilino_contrato (
  id_inquilino INT,
  id_contrato INT,
  PRIMARY KEY (id_inquilino, id_contrato),
  FOREIGN KEY (id_inquilino) REFERENCES inquilinos(id_inquilino),
  FOREIGN KEY (id_contrato) REFERENCES contratos(id_contrato)
);


TABLA PAGOS

CREATE TABLE pagos (
  id_pago INT AUTO_INCREMENT PRIMARY KEY,
  cantidad DECIMAL(10,2),
  fecha_pago DATE,
  metodo_pago VARCHAR(50),
  id_contrato INT,
  FOREIGN KEY (id_contrato) REFERENCES contratos(id_contrato)
);

TABLA INCIDENCIAS

CREATE TABLE incidencias (
  id_incidencia INT AUTO_INCREMENT PRIMARY KEY,
  descripcion TEXT,
  estado VARCHAR(50),
  id_habitacion INT,
  FOREIGN KEY (id_habitacion) REFERENCES habitaciones(id_habitacion)
);