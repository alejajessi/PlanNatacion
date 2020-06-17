package com.e.periodizacionnatacion.Clases;

import java.io.Serializable;
import java.util.ArrayList;

public class Integrante implements Serializable {

    private String Nombre;
    private String ID;
    private ArrayList<Prueba> Pruebas;

    /**
     * Constructor vacio
     */
    public Integrante() {
        Nombre = "";
        this.ID = "";
        Pruebas = new ArrayList<Prueba>();
    }

    /**
     * Constructor basico
     * @param nombre
     * @param ID
     */
    public Integrante(String nombre, String ID) {
        Nombre = nombre;
        this.ID = ID;
        Pruebas = new ArrayList<Prueba>();
    }

    /**
     * Constructor Completo
     * @param nombre
     * @param ID
     * @param prueba
     */
    public Integrante(String nombre, String ID, ArrayList<Prueba> prueba) {
        Nombre = nombre;
        this.ID = ID;
        Pruebas = prueba;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getID() {
        return ID;
    }

    public ArrayList<Prueba> getPruebas() {
        return Pruebas;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setPruebas(ArrayList<Prueba> pruebas) {
        Pruebas = pruebas;
    }

    @Override
    public String toString() {
        return "Integrante{" +
                "Nombre='" + Nombre + '\'' +
                ", ID='" + ID + '\'' +
                ", Pruebas=" + Pruebas +
                '}';
    }
}
