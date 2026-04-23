package com.gestionap.service;

import com.gestionap.dao.HabitacionDAO;
import com.gestionap.model.Habitacion;
import com.gestionap.model.Habitacion.Estado;

import java.sql.SQLException;
import java.util.List;

/**
 * Logica de negocio para la gestion de habitaciones.
 * Coordina validaciones antes de delegar en HabitacionDAO.
 */
public class HabitacionService {

    private final HabitacionDAO habitacionDAO = new HabitacionDAO();

    /**
     * Devuelve todas las habitaciones con informacion de piso y ciudad.
     */
    public List<Habitacion> listarTodas() throws SQLException {
        return habitacionDAO.listarTodas();
    }

    /**
     * Devuelve solo las habitaciones disponibles para alquilar.
     */
    public List<Habitacion> listarDisponibles() throws SQLException {
        return habitacionDAO.listarDisponibles();
    }

    /**
     * Busca una habitacion por ID.
     * Lanza IllegalArgumentException si no existe.
     */
    public Habitacion buscarPorId(int idHabitacion) throws SQLException {
        Habitacion h = habitacionDAO.buscarPorId(idHabitacion);
        if (h == null) {
            throw new IllegalArgumentException("No existe una habitacion con ID " + idHabitacion);
        }
        return h;
    }

    /**
     * Cambia el estado de una habitacion.
     * Valida que la transicion sea coherente:
     * - Solo se puede alquilar una habitacion disponible.
     * - Solo se puede marcar como disponible una habitacion en mantenimiento.
     */
    public void cambiarEstado(int idHabitacion, Estado nuevoEstado) throws SQLException {
        Habitacion h = buscarPorId(idHabitacion);
        Estado actual = h.getEstado();

        if (actual == nuevoEstado) {
            throw new IllegalStateException(
                "La habitacion ya tiene el estado '" + nuevoEstado + "'.");
        }
        if (nuevoEstado == Estado.alquilada && actual != Estado.disponible) {
            throw new IllegalStateException(
                "Solo se puede alquilar una habitacion que este disponible. Estado actual: " + actual);
        }

        boolean actualizado = habitacionDAO.actualizarEstado(idHabitacion, nuevoEstado);
        if (!actualizado) {
            throw new SQLException("No se pudo actualizar el estado de la habitacion ID " + idHabitacion);
        }
    }

    /**
     * Marca una habitacion como disponible (usada al finalizar un contrato).
     */
    public void liberarHabitacion(int idHabitacion) throws SQLException {
        habitacionDAO.actualizarEstado(idHabitacion, Estado.disponible);
    }
}
