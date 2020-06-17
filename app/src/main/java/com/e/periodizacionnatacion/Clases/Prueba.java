package com.e.periodizacionnatacion.Clases;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Esta clase es utilizada unicamente por la clase integrantes para guardar los diferentes
 * resultados de una prueba, la cual se puede realizar muchas veces.
 */
public class Prueba implements Serializable {

    private String nombre;
    private ArrayList<DatoBasico> resultadosPruebas;

    /**
     * Constructor vacio
     */
    public Prueba() {
        nombre = "";
        resultadosPruebas = new ArrayList<DatoBasico>();
    }

    /**
     * Constructor Completo
     * @param nombre
     * @param resultadosPruebas
     */
    public Prueba(String nombre, ArrayList<DatoBasico> resultadosPruebas) {
        this.nombre = nombre;
        this.resultadosPruebas = resultadosPruebas;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<DatoBasico> getResultadosPruebas() {
        return resultadosPruebas;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setResultadosPruebas(ArrayList<DatoBasico> resultadosPruebas) {
        this.resultadosPruebas = resultadosPruebas;
    }

    @Override
    public String toString() {
        return "Prueba{" +
                "nombre='" + nombre + '\'' +
                ", resultadosPruebas=" + resultadosPruebas +
                '}';
    }
}
