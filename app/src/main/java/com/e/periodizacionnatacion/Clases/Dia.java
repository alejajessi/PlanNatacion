package com.e.periodizacionnatacion.Clases;

import java.io.Serializable;

public class Dia implements Serializable {

    private String fecha;
    private String volHabilidad1;
    private String volHabilidad2;
    private String volHabilidad3;
    private String volHabilidad4;
    private String volHabilidad5;

    /**
     * Constructor vacio
     */
    public Dia() {
        fecha = "";
        volHabilidad1 = "";
        volHabilidad2 = "";
        volHabilidad3 = "";
        volHabilidad4 = "";
        volHabilidad5 = "";
    }

    /**
     * Constructor Basico
     * @param fecha
     */
    public Dia(String fecha) {
        this.fecha = fecha;
        volHabilidad1 = "";
        volHabilidad2 = "";
        volHabilidad3 = "";
        volHabilidad4 = "";
        volHabilidad5 = "";
    }

    /**
     * Constructor Completo
     * @param fecha
     * @param volHabilidad1
     * @param volHabilidad2
     * @param volHabilidad3
     * @param volHabilidad4
     * @param volHabilidad5
     */
    public Dia(String fecha, String volHabilidad1, String volHabilidad2, String volHabilidad3, String volHabilidad4, String volHabilidad5) {
        this.fecha = fecha;
        this.volHabilidad1 = volHabilidad1;
        this.volHabilidad2 = volHabilidad2;
        this.volHabilidad3 = volHabilidad3;
        this.volHabilidad4 = volHabilidad4;
        this.volHabilidad5 = volHabilidad5;
    }

    public String getFecha() {
        return fecha;
    }

    public String getVolHabilidad1() {
        return volHabilidad1;
    }

    public String getVolHabilidad2() {
        return volHabilidad2;
    }

    public String getVolHabilidad3() {
        return volHabilidad3;
    }

    public String getVolHabilidad4() {
        return volHabilidad4;
    }

    public String getVolHabilidad5() {
        return volHabilidad5;
    }

    public float getVolumen() {
        float vol = 0;
        if (!volHabilidad1.isEmpty()){
            vol = vol+(Float.parseFloat(volHabilidad1));
        }
        if (!volHabilidad2.isEmpty()){
            vol = vol+(Float.parseFloat(volHabilidad2));
        }
        if (!volHabilidad3.isEmpty()){
            vol = vol+(Float.parseFloat(volHabilidad3));
        }
        if (!volHabilidad4.isEmpty()){
            vol = vol+(Float.parseFloat(volHabilidad4));
        }
        if (!volHabilidad5.isEmpty()){
            vol = vol+(Float.parseFloat(volHabilidad5));
        }
        return vol;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setVolHabilidad1(String volHabilidad1) {
        this.volHabilidad1 = volHabilidad1;
    }

    public void setVolHabilidad2(String volHabilidad2) {
        this.volHabilidad2 = volHabilidad2;
    }

    public void setVolHabilidad3(String volHabilidad3) {
        this.volHabilidad3 = volHabilidad3;
    }

    public void setVolHabilidad4(String volHabilidad4) {
        this.volHabilidad4 = volHabilidad4;
    }

    public void setVolHabilidad5(String volHabilidad5) {
        this.volHabilidad5 = volHabilidad5;
    }
}
