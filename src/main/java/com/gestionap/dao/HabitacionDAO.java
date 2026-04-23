package com.gestionap.dao;

import com.gestionap.database.DatabaseConnection;
import com.gestionap.model.Habitacion;
import com.gestionap.model.Habitacion.Estado;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class HabitacionDAO {

    private Connection getConexion() throws SQLException {
        return DatabaseConnection.getInstance().getConexion();
    }


    public List<Habitacion> listarTodas() throws SQLException {
        String sql = """
                SELECT h.id_habitacion, h.numero, h.precio, h.estado, h.id_piso,
                       p.direccion AS direccion_piso, c.nombre AS nombre_ciudad
                FROM Habitaciones h
                JOIN Pisos p ON h.id_piso = p.id_piso
                JOIN Ciudades c ON p.id_ciudad = c.id_ciudad
                ORDER BY c.nombre, p.direccion, h.numero
                """;
        List<Habitacion> lista = new ArrayList<>();
        try (PreparedStatement ps = getConexion().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearFila(rs));
            }
        }
        return lista;
    }


    public List<Habitacion> listarDisponibles() throws SQLException {
        String sql = """
                SELECT h.id_habitacion, h.numero, h.precio, h.estado, h.id_piso,
                       p.direccion AS direccion_piso, c.nombre AS nombre_ciudad
                FROM Habitaciones h
                JOIN Pisos p ON h.id_piso = p.id_piso
                JOIN Ciudades c ON p.id_ciudad = c.id_ciudad
                WHERE h.estado = 'disponible'
                ORDER BY c.nombre, p.direccion, h.numero
                """;
        List<Habitacion> lista = new ArrayList<>();
        try (PreparedStatement ps = getConexion().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearFila(rs));
            }
        }
        return lista;
    }

    public Habitacion buscarPorId(int idHabitacion) throws SQLException {
        String sql = """
                SELECT h.id_habitacion, h.numero, h.precio, h.estado, h.id_piso,
                       p.direccion AS direccion_piso, c.nombre AS nombre_ciudad
                FROM Habitaciones h
                JOIN Pisos p ON h.id_piso = p.id_piso
                JOIN Ciudades c ON p.id_ciudad = c.id_ciudad
                WHERE h.id_habitacion = ?
                """;
        try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
            ps.setInt(1, idHabitacion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearFila(rs);
            }
        }
        return null;
    }


    public int insertar(Habitacion h) throws SQLException {
        String sql = "INSERT INTO Habitaciones (numero, precio, estado, id_piso) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = getConexion().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, h.getNumero());
            ps.setBigDecimal(2, h.getPrecio());
            ps.setString(3, h.getEstado().name());
            ps.setInt(4, h.getIdPiso());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        throw new SQLException("No se pudo obtener el ID generado para la habitacion.");
    }

    public boolean actualizarEstado(int idHabitacion, Estado nuevoEstado) throws SQLException {
        String sql = "UPDATE Habitaciones SET estado = ? WHERE id_habitacion = ?";
        try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
            ps.setString(1, nuevoEstado.name());
            ps.setInt(2, idHabitacion);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int idHabitacion) throws SQLException {
        String sql = "DELETE FROM Habitaciones WHERE id_habitacion = ?";
        try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
            ps.setInt(1, idHabitacion);
            return ps.executeUpdate() > 0;
        }
    }


    private Habitacion mapearFila(ResultSet rs) throws SQLException {
        Habitacion h = new Habitacion();
        h.setIdHabitacion(rs.getInt("id_habitacion"));
        h.setNumero(rs.getInt("numero"));
        h.setPrecio(rs.getBigDecimal("precio"));
        h.setEstado(Estado.valueOf(rs.getString("estado")));
        h.setIdPiso(rs.getInt("id_piso"));
        h.setDireccionPiso(rs.getString("direccion_piso"));
        h.setNombreCiudad(rs.getString("nombre_ciudad"));
        return h;
    }
}
