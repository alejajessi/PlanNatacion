package com.e.periodizacionnatacion.Clases;

import java.util.ArrayList;

public class Integrante {

    private String Nombre;
    private String ID;
    private String Prueba;
    private ArrayList<String> FechaTiempo;

    /**
     * Constructor vacio
     */
    public Integrante() {
        Nombre = "";
        this.ID = "";
        Prueba = "";
        FechaTiempo = new ArrayList<String>();
    }

    /**
     * Constructor basico
     * @param nombre
     * @param prueba
     */
    public Integrante(String nombre, String ID, String prueba) {
        Nombre = nombre;
        this.ID = ID;
        Prueba = prueba;
        FechaTiempo = new ArrayList<String>();
    }

    /**
     * Constructor Completo
     * @param nombre
     * @param prueba
     * @param fechaTiempo
     */
    public Integrante(String nombre, String ID, String prueba, ArrayList<String> fechaTiempo) {
        Nombre = nombre;
        this.ID = ID;
        Prueba = prueba;
        FechaTiempo = fechaTiempo;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getID() {
        return ID;
    }

    public String getPrueba() {
        return Prueba;
    }

    public ArrayList<String> getFechaTiempo() {
        return FechaTiempo;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setPrueba(String prueba) {
        Prueba = prueba;
    }

    public void setFechaTiempo(ArrayList<String> fechaTiempo) {
        FechaTiempo = fechaTiempo;
    }

    @Override
    public String toString() {
        return "Integrante{" +
                "Nombre='" + Nombre + '\'' +
                ", Prueba='" + Prueba + '\'' +
                ", FechaTiempo=" + FechaTiempo +
                '}';
    }
}
