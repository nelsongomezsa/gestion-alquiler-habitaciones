# GestionAp — Gestión de Alquiler de Habitaciones

[![Estado](https://img.shields.io/badge/estado-en%20desarrollo-brightgreen)](https://github.com/nelsongomezsa/gestion-alquiler-habitaciones)
[![Java](https://img.shields.io/badge/Java-17-orange?logo=java)](https://adoptium.net)
[![MySQL](https://img.shields.io/badge/MySQL-8-blue?logo=mysql)](https://www.mysql.com)
[![Maven](https://img.shields.io/badge/Maven-3.9+-red?logo=apachemaven)](https://maven.apache.org)
[![Licencia](https://img.shields.io/badge/licencia-MIT-brightgreen)](https://opensource.org/licenses/MIT)

---

## Descripción

**GestionAp** es una aplicación de escritorio desarrollada en Java para la gestión integral de pisos y habitaciones en alquiler. Permite llevar el control de inquilinos, contratos, pagos e incidencias desde una interfaz sencilla, con persistencia de datos en MySQL.

El proyecto ha sido desarrollado como **Proyecto Intermodular de 1º DAM** en Prometeo by The Power, integrando los módulos de Programación (0485), Bases de Datos (0484), Lenguajes de Marcas (0373), Sistemas Informáticos (0483), Entornos de Desarrollo (0487) y MPO.

---

## Problema que resuelve

Los propietarios de pisos compartidos gestionan habitualmente sus alquileres con hojas de cálculo o papel, lo que genera errores y pérdida de información. Esta aplicación centraliza:

- El registro de pisos y habitaciones con su estado (disponible, alquilada, en mantenimiento)
- La gestión de inquilinos y sus contratos
- El control de pagos mensuales
- El seguimiento de incidencias reportadas

---

## Tecnologías utilizadas

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 17 | Lenguaje principal |
| JavaFX | 21 | Interfaz gráfica |
| MySQL | 8 | Base de datos relacional |
| JDBC | puro (sin ORM) | Conexión Java–MySQL |
| XML + XSD | — | Exportación y validación de datos |
| Apache Maven | 3.9+ | Gestión de dependencias |
| Git + GitHub | — | Control de versiones |

---

## Estructura del repositorio

```
gestion-alquiler-habitaciones/
├── src/
│   └── main/
│       ├── java/com/gestion/
│       │   ├── Main.java               # Punto de entrada
│       │   ├── model/                  # Clases de dominio (POJOs)
│       │   ├── dao/                    # Acceso a datos con JDBC
│       │   ├── service/                # Lógica de negocio
│       │   ├── controller/             # Controladores JavaFX
│       │   └── utils/                  # Utilidades (XML, validación)
│       └── resources/
│           ├── fxml/                   # Vistas de la interfaz
│           ├── css/                    # Estilos
│           └── database.properties     # Credenciales BD (no subir)
├── sql/
│   ├── schema.sql                      # DDL: creación de tablas
│   ├── datos.sql                       # DML: datos de ejemplo
│   └── consultas.sql                   # Consultas del sistema
├── xml/
│   ├── datos.xml                       # Exportación de datos
│   └── esquema.xsd                     # Esquema de validación
├── diagrams/
│   └── er_diagram.png                  # Diagrama E/R
├── docs/
│   ├── README_bbdd.md                  # Documentación módulo 0484
│   ├── README_xml.md                   # Documentación módulo 0373
│   ├── sistemas/                       # Informe técnico módulo 0483
│   └── empleabilidad/                  # Documentación módulo 1709
├── .gitignore
├── pom.xml
└── README.md
```

---

## Requisitos previos

- **Java 17** JDK — recomendado [Temurin de Adoptium](https://adoptium.net)
- **Apache Maven 3.9+**
- **MySQL 8** o MariaDB 10.6+
- **Git**

---

## Instalación y ejecución

### 1. Clonar el repositorio

```bash
git clone https://github.com/nelsongomezsa/gestion-alquiler-habitaciones.git
cd gestion-alquiler-habitaciones
```

### 2. Crear la base de datos

Ejecuta los scripts en orden:

```bash
mysql -u root -p < sql/schema.sql
mysql -u root -p < sql/datos.sql
```

### 3. Configurar la conexión

Crea el fichero `src/main/resources/database.properties`:

```properties
db.url=jdbc:mysql://localhost:3306/gestion_alquileres
db.user=root
db.password=tu_contraseña
```

### 4. Compilar y ejecutar

```bash
mvn clean package
mvn javafx:run
```

---

## Módulos académicos integrados

| Módulo | Aportación |
|---|---|
| 0484 Bases de Datos | Modelo E/R, scripts SQL, consultas |
| 0485 Programación | Java + JDBC + CRUD completo |
| 0373 Lenguajes de Marcas | Exportación XML validada con XSD |
| 0483 Sistemas Informáticos | Informe técnico del entorno |
| 0487 Entornos de Desarrollo | Git, commits, estructura del repo |
| MPO | Arquitectura en capas, diseño POO |

---

## Autor

**Nelson Gomez Sanchez** | 1º DAM | Prometeo by The Power

---

## Licencia

Este proyecto se distribuye bajo la licencia [MIT](https://opensource.org/licenses/MIT).