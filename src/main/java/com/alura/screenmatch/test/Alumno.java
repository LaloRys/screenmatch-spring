package com.alura.screenmatch.test;

import java.time.LocalDate;
import java.time.Period;

public class Alumno {
    private String nombre;
    private LocalDate fechaNacimiento;

    public Alumno(String nombre, String fechaNacimiento) {
        this.nombre = nombre;
        this.fechaNacimiento = LocalDate.parse(fechaNacimiento);
    }


    public int getEdad() {
        Period periodo = Period.between(fechaNacimiento, LocalDate.now());

        return periodo.getYears();
    }

    @Override
    public String toString() {
        Period periodo = Period.between(fechaNacimiento, LocalDate.now());

        return "Alumno{" +
                "nombre='" + nombre + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", Perido =" + periodo.getChronology() +
                ", edad=" + getEdad() +
                '}';
    }
    // getters, setters y toString omitidos
}