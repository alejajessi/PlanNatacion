package com.e.periodizacionnatacion.Clases;

import java.io.Serializable;

public class Notificacion implements Serializable {

    private String nombre;
    private String fecha;
    private String macrociclo;
    private boolean posponer;

    public Notificacion() {
        nombre = "";
        fecha = "";
        macrociclo = "";
        posponer = false;
    }

    public Notificacion(String nombre, String fecha, String macrociclo, boolean posponer) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.macrociclo = macrociclo;
        this.posponer = posponer;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public String getMacrociclo() {
        return macrociclo;
    }

    public boolean isPosponer() {
        return posponer;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setMacrociclo(String macrociclo) {
        this.macrociclo = macrociclo;
    }

    public void setPosponer(boolean posponer) {
        this.posponer = posponer;
    }
}
