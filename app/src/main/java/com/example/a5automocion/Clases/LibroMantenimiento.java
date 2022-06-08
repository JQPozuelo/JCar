package com.example.a5automocion.Clases;

import java.io.Serializable;
import java.util.Objects;

public class LibroMantenimiento implements Serializable {
    private String Referencia;
    private String Contenido;

    // Constructor
    public LibroMantenimiento(String referencia, String contenido) {
        Referencia = referencia;
        Contenido = contenido;
    }
    public LibroMantenimiento() {
    }
    // Getters y setters

    public String getReferencia() {
        return Referencia;
    }

    public void setReferencia(String referencia) {
        Referencia = referencia;
    }

    public String getContenido() {
        return Contenido;
    }

    public void setContenido(String contenido) {
        Contenido = contenido;
    }
    // hashcode y equals

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LibroMantenimiento that = (LibroMantenimiento) o;
        return Objects.equals(Referencia, that.Referencia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Referencia);
    }
    // to string

    @Override
    public String toString() {
        return "LibroMantenimiento{" +
                "Referencia='" + Referencia + '\'' +
                ", Contenido='" + Contenido + '\'' +
                '}';
    }
}
