package com.e.periodizacionnatacion.Clases;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Dato {

    private String valor;
    private String porcentaje;
    private String Volumen;
    private boolean Modificado;
    private ArrayList<Dato> fecha;
    private int cantidad;

    /**
     * Constructor vacio
     */
    public Dato() {
        this.valor = "";
        this.porcentaje = "";
        Volumen = "";
        Modificado = false;
        this.fecha = null;
        this.cantidad = 0;
    }

    /**
     * Constructor Mes y Semanas
     * @param valor
     * @param porcentaje
     * @param volumen
     * @param modificado
     * @param fecha
     * @param cantidad
     */
    public Dato(String valor, String porcentaje, String volumen, boolean modificado, ArrayList<Dato> fecha, int cantidad) {
        this.valor = valor;
        this.porcentaje = porcentaje;
        Volumen = volumen;
        Modificado = modificado;
        this.fecha = fecha;
        this.cantidad = cantidad;
    }

    /**
     * Constructor Dia
     * @param valor
     * @param volumen
     * @param modificado
     */
    public Dato(String valor, String volumen, boolean modificado) {
        this.valor = valor;
        this.porcentaje = "";
        Volumen = volumen;
        Modificado = modificado;
        this.fecha = null;
        this.cantidad = 0;
    }

    public String getValor() {
        return valor;
    }

    public String getPorcentaje() {
        return porcentaje;
    }

    public String getVolumen() {
        return Volumen;
    }

    public boolean isModificado() {
        return Modificado;
    }

    @Nullable
    public ArrayList<Dato> getFecha() {
        return fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setPorcentaje(String porcentaje) {
        this.porcentaje = porcentaje;
    }

    public void setVolumen(String volumen) {
        Volumen = volumen;
    }

    public void setModificado(boolean modificado) {
        Modificado = modificado;
    }

    public void setFecha(ArrayList<Dato> fecha) {
        this.fecha = fecha;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Dato{" +
                "valor='" + valor + '\'' +
                ", porcentaje='" + porcentaje + '\'' +
                ", Volumen='" + Volumen + '\'' +
                ", Modificado=" + Modificado +
                ", fecha=" + fecha +
                ", cantidad=" + cantidad +
                '}';
    }
}