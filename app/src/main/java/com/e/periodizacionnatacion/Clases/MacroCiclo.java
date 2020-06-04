package com.e.periodizacionnatacion.Clases;

import java.util.ArrayList;

public class MacroCiclo {

    private String Nombre;
    private String ID;
    private String Inicio;
    private String Fin;
    private Trabajo DiasAgua;
    private Trabajo DiasTierra;
    private ArrayList<Integrante> Integrantes;

    /**
     * Constructor vacio
     */
    public MacroCiclo() {
        Nombre = "";
        this.ID = "";
        Inicio = "";
        Fin = "";
        DiasAgua = new Trabajo();
        DiasTierra = new Trabajo();
        Integrantes = new ArrayList<Integrante>();
    }

    /**
     * Constructor basico
     * @param nombre
     * @param ID
     * @param inicio
     * @param fin
     */
    public MacroCiclo(String nombre, String ID, String inicio, String fin) {
        Nombre = nombre;
        this.ID = ID;
        Inicio = inicio;
        Fin = fin;
        DiasAgua = new Trabajo();
        DiasTierra = new Trabajo();
        Integrantes = new ArrayList<Integrante>();
    }

    /**
     * Constructor Completo
     * @param nombre
     * @param ID
     * @param inicio
     * @param fin
     * @param diasAgua
     * @param diasTierra
     * @param integrantes
     */
    public MacroCiclo(String nombre, String ID, String inicio, String fin, Trabajo diasAgua, Trabajo diasTierra, ArrayList<Integrante> integrantes) {
        Nombre = nombre;
        this.ID = ID;
        Inicio = inicio;
        Fin = fin;
        DiasAgua = diasAgua;
        DiasTierra = diasTierra;
        Integrantes = integrantes;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getID() {
        return ID;
    }

    public String getInicio() {
        return Inicio;
    }

    public String getFin() {
        return Fin;
    }

    public Trabajo getDiasAgua() {
        return DiasAgua;
    }

    public Trabajo getDiasTierra() {
        return DiasTierra;
    }

    public ArrayList<Integrante> getIntegrantes() {
        return Integrantes;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setInicio(String inicio) {
        Inicio = inicio;
    }

    public void setFin(String fin) {
        Fin = fin;
    }

    public void setDiasAgua(Trabajo diasAgua) {
        DiasAgua = diasAgua;
    }

    public void setDiasTierra(Trabajo diasTierra) {
        DiasTierra = diasTierra;
    }

    public void setIntegrantes(ArrayList<Integrante> integrantes) {
        Integrantes = integrantes;
    }

    @Override
    public String toString() {
        return "MacroCiclo{" +
                "Nombre='" + Nombre + '\'' +
                ", ID='" + ID + '\'' +
                ", Inicio='" + Inicio + '\'' +
                ", Fin='" + Fin + '\'' +
                ", DiasAgua=" + DiasAgua +
                ", DiasTierra=" + DiasTierra +
                ", Integrantes=" + Integrantes +
                '}';
    }
}
