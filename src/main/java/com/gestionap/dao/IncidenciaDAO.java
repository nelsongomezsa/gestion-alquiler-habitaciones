package com.gestionap.dao;

import com.gestionap.database.DatabaseConnection;
import com.gestionap.model.Incidencia;
import com.gestionap.model.Incidencia.Estado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class IncidenciaDAO {

    private Connection getConexion() throws SQLException {
        return DatabaseConnection.getInstance().getConexion();
    }


    public List<Incidencia> listarTodas() throws SQLException {
        String sql = """
                SELECT inc.id_incidencia, inc.id_habitacion, inc.id_inquilino,
                       inc.descripcion, inc.estado, inc.fecha,
                       CONCAT(i.nombre, ' ', i.apellidos) AS nombre_inquilino,
                       h.numero AS numero_habitacion
                FROM Incidencias inc
                JOIN Inquilinos   i ON inc.id_inquilino  = i.id_inquilino
                JOIN Habitaciones h ON inc.id_habitacion = h.id_habitacion
                ORDER BY inc.fecha DESC
                """;
        List<Incidencia> lista = new ArrayList<>();
        try (PreparedStatement ps = getConexion().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearFila(rs));
            }
        }
        return lista;
    }


    public List<Incidencia> listarPendientes() throws SQLException {
        String sql = """
                SELECT inc.id_incidencia, inc.id_habitacion, inc.id_inquilino,
                       inc.descripcion, inc.estado, inc.fecha,
                       CONCAT(i.nombre, ' ', i.apellidos) AS nombre_inquilino,
                       h.numero AS numero_habitacion
                FROM Incidencias inc
                JOIN Inquilinos   i ON inc.id_inquilino  = i.id_inquilino
                JOIN Habitaciones h ON inc.id_habitacion = h.id_habitacion
                WHERE inc.estado = 'pendiente'
                ORDER BY inc.fecha ASC
                """;
        List<Incidencia> lista = new ArrayList<>();
        try (PreparedStatement ps = getConexion().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearFila(rs));
            }
        }
        return lista;
    }


    public Incidencia buscarPorId(int idIncidencia) throws SQLException {
        String sql = """
                SELECT inc.id_incidencia, inc.id_habitacion, inc.id_inquilino,
                       inc.descripcion, inc.estado, inc.fecha,
                       CONCAT(i.nombre, ' ', i.apellidos) AS nombre_inquilino,
                       h.numero AS numero_habitacion
                FROM Incidencias inc
                JOIN Inquilinos   i ON inc.id_inquilino  = i.id_inquilino
                JOIN Habitaciones h ON inc.id_habitacion = h.id_habitacion
                WHERE inc.id_incidencia = ?
                """;
        try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
            ps.setInt(1, idIncidencia);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearFila(rs);
            }
        }
        return null;
    }


    public int insertar(Incidencia inc) throws SQLException {
        String sql = "INSERT INTO Incidencias (id_habitacion, id_inquilino, descripcion, estado, fecha) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = getConexion().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, inc.getIdHabitacion());
            ps.setInt(2, inc.getIdInquilino());
            ps.setString(3, inc.getDescripcion());
            ps.setString(4, inc.getEstado().name());
            ps.setDate(5, Date.valueOf(inc.getFecha()));
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        throw new SQLException("No se pudo obtener el ID generado para la incidencia.");
    }

    public boolean actualizarEstado(int idIncidencia, Estado nuevoEstado) throws SQLException {
        String sql = "UPDATE Incidencias SET estado = ? WHERE id_incidencia = ?";
        try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
            ps.setString(1, nuevoEstado.name());
            ps.setInt(2, idIncidencia);
            return ps.executeUpdate() > 0;
        }
    }


    public boolean eliminar(int idIncidencia) throws SQLException {
        String sql = "DELETE FROM Incidencias WHERE id_incidencia = ?";
        try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
            ps.setInt(1, idIncidencia);
            return ps.executeUpdate() > 0;
        }
    }



    private Incidencia mapearFila(ResultSet rs) throws SQLException {
        Incidencia inc = new Incidencia();
        inc.setIdIncidencia(rs.getInt("id_incidencia"));
        inc.setIdHabitacion(rs.getInt("id_habitacion"));
        inc.setIdInquilino(rs.getInt("id_inquilino"));
        inc.setDescripcion(rs.getString("descripcion"));
        inc.setEstado(Estado.valueOf(rs.getString("estado")));
        inc.setFecha(rs.getDate("fecha").toLocalDate());
        inc.setNombreInquilino(rs.getString("nombre_inquilino"));
        inc.setNumeroHabitacion(rs.getInt("numero_habitacion"));
        return inc;
    }
}
