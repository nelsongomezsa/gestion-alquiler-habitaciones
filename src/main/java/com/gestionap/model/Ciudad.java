package com.gestionap.model;

/**
 * Representa una ciudad donde se ubican los pisos.
 * Mapea la tabla Ciudades de la base de datos.
 */
public class Ciudad {

    private int idCiudad;
    private String nombre;

    public Ciudad() {}

    public Ciudad(int idCiudad, String nombre) {
        this.idCiudad = idCiudad;
        this.nombre   = nombre;
    }

    public int getIdCiudad() { return idCiudad; }
    public void setIdCiudad(int idCiudad) { this.idCiudad = idCiudad; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @Override
    public String toString() {
        return "Ciudad{id=" + idCiudad + ", nombre='" + nombre + "'}";
    }
}
