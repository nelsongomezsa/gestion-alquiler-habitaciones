package com.gestionap.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Gestiona la conexion a MySQL usando el patron Singleton.
 * Lee las credenciales de database.properties en el classpath.
 *
 * Para configurar: copia database.properties.example como database.properties
 * en src/main/resources/ y rellena tus credenciales.
 */
public class DatabaseConnection {

    private static DatabaseConnection instancia;
    private Connection conexion;

    private static final String PROPERTIES_FILE = "database.properties";

    private DatabaseConnection() throws SQLException {
        Properties props = cargarPropiedades();
        String url      = props.getProperty("db.url");
        String usuario  = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        try {
            Class.forName(props.getProperty("db.driver", "com.mysql.cj.jdbc.Driver"));
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL no encontrado: " + e.getMessage(), e);
        }

        this.conexion = DriverManager.getConnection(url, usuario, password);
        this.conexion.setAutoCommit(true);
    }

    /**
     * Devuelve la unica instancia de la conexion, creandola si no existe o esta cerrada.
     */
    public static synchronized DatabaseConnection getInstance() throws SQLException {
        if (instancia == null || instancia.getConexion().isClosed()) {
            instancia = new DatabaseConnection();
        }
        return instancia;
    }

    /** Devuelve el objeto Connection listo para usar en los DAOs. */
    public Connection getConexion() {
        return conexion;
    }

    /** Cierra la conexion de forma controlada. Llama a esto al salir de la app. */
    public void cerrarConexion() {
        if (conexion != null) {
            try {
                if (!conexion.isClosed()) {
                    conexion.close();
                    System.out.println("Conexion a la base de datos cerrada correctamente.");
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexion: " + e.getMessage());
            }
        }
    }

    private Properties cargarPropiedades() throws SQLException {
        Properties props = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (is == null) {
                throw new SQLException(
                    "No se encontro '" + PROPERTIES_FILE + "' en el classpath.\n" +
                    "Copia 'database.properties.example' como 'database.properties' " +
                    "en src/main/resources/ y rellena tus credenciales."
                );
            }
            props.load(is);
        } catch (IOException e) {
            throw new SQLException("Error leyendo " + PROPERTIES_FILE + ": " + e.getMessage(), e);
        }
        return props;
    }
}
