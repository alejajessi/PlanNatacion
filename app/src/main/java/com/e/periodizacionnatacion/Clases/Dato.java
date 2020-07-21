package com.e.periodizacionnatacion.Clases;

import android.util.Log;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

public class Dato implements Serializable {

    private String inicio;
    private String fin;
    private String porcentaje;
    private String Volumen;
    private boolean Modificado;
    private ArrayList<Dato> fecha;
    private ArrayList<Dia> dias;

    /**
     * Constructor vacio
     */
    public Dato() {
        this.inicio = "";
        this.porcentaje = "";
        Volumen = "";
        Modificado = false;
        this.fecha = null;
        this.fin = "";
        this.dias = null;
    }

    /**
     * Constructor Mes y Semanas
     * @param inicio
     * @param porcentaje
     * @param volumen
     * @param modificado
     * @param fecha
     * @param fin
     */
    public Dato(String inicio, String fin, String porcentaje, String volumen, boolean modificado, ArrayList<Dato> fecha) {
        this.inicio = inicio;
        this.porcentaje = porcentaje;
        Volumen = volumen;
        Modificado = modificado;
        this.fecha = fecha;
        this.fin = fin;
        this.dias = null;
    }

    public String getInicio() {
        return inicio;
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

    public String getFin() {
        return fin;
    }

    public ArrayList<Dia> getDias() {
        return dias;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
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

    public void setFin(String fin) {
        this.fin = fin;
    }

    public void setDias(ArrayList<Dia> dias) {
        this.dias = dias;
    }

    public void agregarVolumenFecha(){
        if (fecha != null){
            //Pregunto el tamaño del arreglo y calculo el porcentaje
            int numFecha = fecha.size();
            int porcentaje = 100/numFecha;

            //Se calculan los porcentajes y volumenes de cada mes dependiendo de la cantidad y
            //se hace la misma operacion con los array de cada fecha
            int cantidad = 0;
            int medio = (int)numFecha/2;
            int porcentajeFecha = 0;
            int ultimoPorcentaje = 100;
            float volumenFecha = 0;
            float volumen = Float.parseFloat(Volumen);
            for (int i=0; i<numFecha-1;i++){

                cantidad = calcularCantidad(numFecha);
                if (i<medio || i>medio){
                    porcentajeFecha = porcentaje-cantidad;
                }else if (i == medio){
                    porcentajeFecha = porcentaje+cantidad;
                }

                //Agrego el porcentaje del mes
                ultimoPorcentaje = ultimoPorcentaje-porcentajeFecha;
                fecha.get(i).setPorcentaje(porcentajeFecha+"");

                //Calculo el volumen del mes y lo asigno
                volumenFecha = volumen*((float)(porcentajeFecha/100.00));
                fecha.get(i).setVolumen(volumenFecha+"");
                fecha.get(i).agregarVolumenFecha();
            }

            //Agrego el porcentaje del ultimo mes
            fecha.get(numFecha-1).setPorcentaje(ultimoPorcentaje+"");

            //Calculo el volumen del ultimo mes y lo asigno
            volumenFecha = volumen*((float)(ultimoPorcentaje/100.00));
            fecha.get(numFecha-1).setVolumen(volumenFecha+"");
            fecha.get(numFecha-1).agregarVolumenFecha();
        }

    }

    public int calcularCantidad(int tamaño){
        //defino la cantidad que se va a aumentar o disminuir del procentaje
        //dependiendo el tamaño del arreglo
        int cantidad = 0;
        if (tamaño<4 && tamaño>1){
            cantidad = (int)(Math.random() * 11);
        }else if (tamaño<7 && tamaño>3){
            cantidad = (int)(Math.random()*6);
        }else if (tamaño<13 && tamaño>6){
            cantidad = (int)(Math.random()*4);
        }else{
            cantidad = (int)(Math.random()*2);
        }

        return cantidad;
    }

    @Override
    public String toString() {
        return "Dato{" +
                "inicio='" + inicio + '\'' +
                ", porcentaje='" + porcentaje + '\'' +
                ", Volumen='" + Volumen + '\'' +
                ", Modificado=" + Modificado +
                ", fecha=" + fecha +
                ", fin=" + fin +
                '}';
    }
}