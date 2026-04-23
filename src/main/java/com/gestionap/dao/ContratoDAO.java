package com.gestionap.dao;

import com.gestionap.database.DatabaseConnection;
import com.gestionap.model.Contrato;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Acceso a datos para la tabla Contratos.
 * Realiza operaciones CRUD usando JDBC puro.
 */
public class ContratoDAO {

    private Connection getConexion() throws SQLException {
        return DatabaseConnection.getInstance().getConexion();
    }

    // Fragmento SQL reutilizable con JOINs para traer datos relacionados
    private static final String SELECT_BASE = """
            SELECT c.id_contrato, c.id_habitacion, c.id_inquilino,
                   c.fecha_inicio, c.fecha_fin, c.precio_mensual,
                   i.nombre || ' ' || i.apellidos AS nombre_inquilino,
                   i.dni                          AS dni_inquilino,
                   h.numero                       AS numero_habitacion,
                   p.direccion                    AS direccion_piso
            FROM Contratos c
            JOIN Inquilinos  i ON c.id_inquilino  = i.id_inquilino
            JOIN Habitaciones h ON c.id_habitacion = h.id_habitacion
            JOIN Pisos        p ON h.id_piso        = p.id_piso
            """;

    // -------------------------------------------------------------------------
    // Consultas de lectura
    // -------------------------------------------------------------------------

    /** Devuelve todos los contratos. */
    public List<Contrato> listarTodos() throws SQLException {
        // MySQL usa CONCAT en vez de ||
        String sql = """
                SELECT c.id_contrato, c.id_habitacion, c.id_inquilino,
                       c.fecha_inicio, c.fecha_fin, c.precio_mensual,
                       CONCAT(i.nombre, ' ', i.apellidos) AS nombre_inquilino,
                       i.dni                              AS dni_inquilino,
                       h.numero                          AS numero_habitacion,
                       p.direccion                       AS direccion_piso
                FROM Contratos c
                JOIN Inquilinos   i ON c.id_inquilino  = i.id_inquilino
                JOIN Habitaciones h ON c.id_habitacion = h.id_habitacion
                JOIN Pisos        p ON h.id_piso        = p.id_piso
                ORDER BY c.fecha_inicio DESC
                """;
        List<Contrato> lista = new ArrayList<>();
        try (PreparedStatement ps = getConexion().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearFila(rs));
            }
        }
        return lista;
    }

    /**
     * Devuelve solo los contratos cuya vigencia cubre la fecha actual.
     */
    public List<Contrato> listarActivos() throws SQLException {
        String sql = """
                SELECT c.id_contrato, c.id_habitacion, c.id_inquilino,
                       c.fecha_inicio, c.fecha_fin, c.precio_mensual,
                       CONCAT(i.nombre, ' ', i.apellidos) AS nombre_inquilino,
                       i.dni                              AS dni_inquilino,
                       h.numero                          AS numero_habitacion,
                       p.direccion                       AS direccion_piso
                FROM Contratos c
                JOIN Inquilinos   i ON c.id_inquilino  = i.id_inquilino
                JOIN Habitaciones h ON c.id_habitacion = h.id_habitacion
                JOIN Pisos        p ON h.id_piso        = p.id_piso
                WHERE c.fecha_inicio <= CURDATE() AND c.fecha_fin >= CURDATE()
                ORDER BY c.fecha_fin ASC
                """;
        List<Contrato> lista = new ArrayList<>();
        try (PreparedStatement ps = getConexion().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearFila(rs));
            }
        }
        return lista;
    }

    /**
     * Busca un contrato por ID.
     * Devuelve null si no existe.
     */
    public Contrato buscarPorId(int idContrato) throws SQLException {
        String sql = """
                SELECT c.id_contrato, c.id_habitacion, c.id_inquilino,
                       c.fecha_inicio, c.fecha_fin, c.precio_mensual,
                       CONCAT(i.nombre, ' ', i.apellidos) AS nombre_inquilino,
                       i.dni                              AS dni_inquilino,
                       h.numero                          AS numero_habitacion,
                       p.direccion                       AS direccion_piso
                FROM Contratos c
                JOIN Inquilinos   i ON c.id_inquilino  = i.id_inquilino
                JOIN Habitaciones h ON c.id_habitacion = h.id_habitacion
                JOIN Pisos        p ON h.id_piso        = p.id_piso
                WHERE c.id_contrato = ?
                """;
        try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
            ps.setInt(1, idContrato);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearFila(rs);
            }
        }
        return null;
    }

    // -------------------------------------------------------------------------
    // Operaciones de escritura
    // -------------------------------------------------------------------------

    /**
     * Inserta un nuevo contrato y devuelve el ID generado.
     */
    public int insertar(Contrato c) throws SQLException {
        String sql = "INSERT INTO Contratos (id_habitacion, id_inquilino, fecha_inicio, fecha_fin, precio_mensual) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = getConexion().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, c.getIdHabitacion());
            ps.setInt(2, c.getIdInquilino());
            ps.setDate(3, Date.valueOf(c.getFechaInicio()));
            ps.setDate(4, Date.valueOf(c.getFechaFin()));
            ps.setBigDecimal(5, c.getPrecioMensual());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        throw new SQLException("No se pudo obtener el ID generado para el contrato.");
    }

    /**
     * Elimina un contrato por su ID.
     */
    public boolean eliminar(int idContrato) throws SQLException {
        String sql = "DELETE FROM Contratos WHERE id_contrato = ?";
        try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
            ps.setInt(1, idContrato);
            return ps.executeUpdate() > 0;
        }
    }

    // -------------------------------------------------------------------------
    // Metodo auxiliar de mapeo
    // -------------------------------------------------------------------------

    private Contrato mapearFila(ResultSet rs) throws SQLException {
        Contrato c = new Contrato();
        c.setIdContrato(rs.getInt("id_contrato"));
        c.setIdHabitacion(rs.getInt("id_habitacion"));
        c.setIdInquilino(rs.getInt("id_inquilino"));
        c.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
        c.setFechaFin(rs.getDate("fecha_fin").toLocalDate());
        c.setPrecioMensual(rs.getBigDecimal("precio_mensual"));
        c.setNombreInquilino(rs.getString("nombre_inquilino"));
        c.setDniInquilino(rs.getString("dni_inquilino"));
        c.setNumeroHabitacion(rs.getInt("numero_habitacion"));
        c.setDireccionPiso(rs.getString("direccion_piso"));
        return c;
    }
}
