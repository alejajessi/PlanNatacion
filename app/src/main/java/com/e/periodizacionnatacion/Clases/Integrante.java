package com.e.periodizacionnatacion.Clases;

import java.io.Serializable;
import java.util.ArrayList;

public class Integrante implements Serializable {

    /**
     * Atributo tipo String
     */
    private String nombre;
    private String id;
    private String descripcion;
    private ArrayList<String> tiposPruebas;
    private ArrayList<Prueba> pruebas;

    /**
     * Constructor vacio
     */
    public Integrante() {
        nombre = "";
        id = "";
        descripcion = "";
        tiposPruebas = new ArrayList<String>();
        pruebas = new ArrayList<Prueba>();
    }

    /**
     * Constructor basico
     * @param nombre
     * @param ID
     */
    public Integrante(String nombre, String id, String descripcion) {
        this.nombre = nombre;
        this.id = id;
        this.descripcion = descripcion;
        tiposPruebas = new ArrayList<String>();
        pruebas = new ArrayList<Prueba>();
    }

    /**
     * Constructor Completo
     * @param nombre
     * @param ID
     * @param prueba
     */
    public Integrante(String nombre, String id, ArrayList<Prueba> prueba) {
        this.nombre = nombre;
        this.id = id;
        pruebas = prueba;
    }

    /**
     * Método getNombre retorna la variable nombre
     * @return  nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método getID retorna la variable id
     * @return id
     */
    public String getID() {
        return id;
    }

    /**
     * Método getDescripcion retorna la variable descripcion
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Método getPruebas retorna la variable pruebas
     * @return pruebas
     */
    public ArrayList<Prueba> getPruebas() {
        return pruebas;
    }

    /**
     * Método getTiposPrueba retorna la variable pruebas
     * @return tiposPruebas
     */
    public ArrayList<String> getTiposPruebas() {
        return tiposPruebas;
    }

    /**
     * Método setNombre modifica la variable nombre
     * @param nombre variable con información modificada
     */
    public void setNombre(String nombre) { this.nombre = nombre;
    }

    /**
     * Método setDescripcion modifica la variable nombre
     * @param descripcion variable con información modificada
     */
    public void setDescripcion(String descripcion) { this.descripcion = descripcion;
    }

    /**
     * Método setID modifica la variable id
     * @param id variable con información modificada
     */
    public void setID(String id) {
        this.id = id;
    }

    /**
     * Método setPruebas modifica el ArrayList pruebas
     * @param pruebas ArrayList con información modificada
     */
    public void setPruebas(ArrayList<Prueba> pruebas) {
        this.pruebas = pruebas;
    }

    /**
     * Método setTiposPruebas modifica el ArrayList tiposPruebas
     * @param tiposPruebas ArrayList con información modificada
     */
    public void setTiposPruebas(ArrayList<String> tiposPruebas) {
        this.tiposPruebas = tiposPruebas;
    }

    /**
     * Método to String
     * @return cadena de caracteres
     */
    @Override
    public String toString() {
        return "Integrante{" +
                "Nombre='" + nombre + '\'' +
                ", ID='" + id + '\'' +
                ", Pruebas=" + pruebas +
                '}';
    }
}
