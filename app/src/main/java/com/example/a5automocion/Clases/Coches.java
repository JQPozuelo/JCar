package com.example.a5automocion.Clases;

import java.io.Serializable;
import java.util.Objects;

public class Coches implements Serializable {
    private String Matricula;
    private String Marca;
    private String Modelo;
    private String Motor;
    // Constructores
    public Coches(String matricula, String marca, String modelo, String motor) {
        Matricula = matricula;
        Marca = marca;
        Modelo = modelo;
        Motor = motor;
    }
    //Constructor vacio
    public Coches() {

    }
    // Getters y setters
    public String getMatricula() {
        return Matricula;
    }

    public void setMatricula(String matricula) {
        Matricula = matricula;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String modelo) {
        Modelo = modelo;
    }

    public String getMotor() {
        return Motor;
    }

    public void setMotor(String motor) {
        Motor = motor;
    }
    // Hashcode y equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coches coches = (Coches) o;
        return Matricula.equals(coches.Matricula);
    }
    @Override
    public int hashCode() {
        return Objects.hash(Matricula);
    }
    // To string
    @Override
    public String toString() {
        return "Coches{" +
                "Matricula='" + Matricula + '\'' +
                ", Marca='" + Marca + '\'' +
                ", Modelo='" + Modelo + '\'' +
                ", Motor='" + Motor + '\'' +
                '}';
    }
}
