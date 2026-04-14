USE gestion_alquileres;


INSERTS

CIUDADES
INSERT INTO Ciudades VALUES (1, 'Madrid');
INSERT INTO Ciudades VALUES (2, 'Barcelona');

PISOS
INSERT INTO Pisos VALUES (1, 'Calle Mayor 10', 3, 1);
INSERT INTO Pisos VALUES (2, 'Gran Via 20', 4, 2);

HABITACIONES
INSERT INTO Habitaciones VALUES (1, 1, NULL);
INSERT INTO Habitaciones VALUES (2, 1, NULL);
INSERT INTO Habitaciones VALUES (3, 2, NULL);

INQUILINOS
INSERT INTO Inquilinos VALUES (1, 'Juan', 'Perez', '12345678A', NULL, 1);
INSERT INTO Inquilinos VALUES (2, 'Maria', 'Lopez', '87654321B', NULL, 2);

CONTRATOS
INSERT INTO Contratos VALUES (1, 1, '2024-01-01', '2024-12-31');
INSERT INTO Contratos VALUES (2, 2, '2024-02-01', '2024-11-30');

PAGOS
INSERT INTO Pagos VALUES (1, 1, 500.00, 'Transferencia', '2024-03-01');
INSERT INTO Pagos VALUES (2, 2, 600.00, 'Efectivo', '2024-03-05');

INCIDENCIAS
INSERT INTO Incidencias VALUES (1, 1, 'Fuga de agua', 'Pendiente');
INSERT INTO Incidencias VALUES (2, 2, 'Calefacción rota', 'Resuelta');

RELACIONES

INSERT INTO Se_Alquilan VALUES (1,1);
INSERT INTO Se_Alquilan VALUES (2,2);

INSERT INTO Realizan VALUES (1,1);
INSERT INTO Realizan VALUES (2,2);

INSERT INTO Reportan VALUES (1,1);
INSERT INTO Reportan VALUES (2,2);

INSERT INTO Generan VALUES (1,1);
INSERT INTO Generan VALUES (2,2);