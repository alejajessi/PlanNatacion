package com.e.periodizacionnatacion.Clases;

import java.io.Serializable;
import java.util.ArrayList;

public class Usuario implements Serializable {

    private String nombre;
    private String correo;
    private String foto;
    private String id;
    private ArrayList<DatoBasico> MacroCiclos;

    /**
     * Constructor vacio
     */
    public Usuario() {
        nombre = "";
        correo = "";
        foto = "";
        this.id = "";
        MacroCiclos = new ArrayList<DatoBasico>();
    }

    /**
     * Constructor basico
     * @param nombre
     * @param correo
     * @param ID
     */
    public Usuario(String correo, String foto, String ID, String nombre) {
        this.nombre = nombre;
        this.correo = correo;
        this.foto = foto;
        this.id = ID;
        MacroCiclos = new ArrayList<DatoBasico>();
    }

    /**
     * Constructor completo
     * @param nombre
     * @param correo
     * @param ID
     * @param macroCiclos
     */
    public Usuario(String correo, String foto, String ID, String nombre, ArrayList<DatoBasico> macroCiclos) {
        this.nombre = nombre;
        this.correo = correo;
        this.foto = foto;
        this.id = ID;
        MacroCiclos = macroCiclos;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getFoto() {
        return foto;
    }

    public String getID() {
        return id;
    }

    public ArrayList<DatoBasico> getMacroCiclos() {
        return MacroCiclos;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setID(String ID) {
        this.id = ID;
    }

    public void setMacroCiclos(ArrayList<DatoBasico> macroCiclos) {
        MacroCiclos = macroCiclos;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "Nombre='" + nombre + '\'' +
                ", Correo='" + correo + '\'' +
                ", ID='" + id + '\'' +
                ", MacroCiclos=" + MacroCiclos +
                '}';
    }
}
