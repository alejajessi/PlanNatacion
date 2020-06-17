package com.e.periodizacionnatacion.Clases;

import android.util.Log;

import java.io.Serializable;
import java.util.Calendar;

public class Trabajo implements Serializable {

    private String Volumen;
    private DatoBasico Trabajo1;
    private DatoBasico Trabajo2;
    private DatoBasico Trabajo3;
    private String Cronograma;

    /**
     * Constructor vacio
     */
    public Trabajo() {
        Volumen = "";
        Trabajo1 = new DatoBasico();
        Trabajo2 = new DatoBasico();
        Trabajo3 = new DatoBasico();
        this.Cronograma = "";
    }

    /**
     * Constructor basico
     * @param volumen
     * @param trabajo1
     * @param trabajo2
     * @param trabajo3
     */
    public Trabajo(String volumen, DatoBasico trabajo1, DatoBasico trabajo2, DatoBasico trabajo3) {
        Volumen = volumen;
        Trabajo1 = trabajo1;
        Trabajo2 = trabajo2;
        Trabajo3 = trabajo3;
        this.Cronograma = "";
    }

    /**
     * Constructor completo
     * @param volumen
     * @param trabajo1
     * @param trabajo2
     * @param trabajo3
     * @param cronograma
     */
    public Trabajo(String volumen, DatoBasico trabajo1, DatoBasico trabajo2, DatoBasico trabajo3, String cronograma) {
        Volumen = volumen;
        Trabajo1 = trabajo1;
        Trabajo2 = trabajo2;
        Trabajo3 = trabajo3;
        this.Cronograma = cronograma;
    }

    public String getVolumen() {
        return Volumen;
    }

    public DatoBasico getTrabajo1() {
        return Trabajo1;
    }

    public DatoBasico getTrabajo2() {
        return Trabajo2;
    }

    public DatoBasico getTrabajo3() {
        return Trabajo3;
    }

    public String getCronograma() {
        return this.Cronograma;
    }

    public void setVolumen(String volumen) {
        Volumen = volumen;
    }

    public void setTrabajo1(DatoBasico trabajo1) {
        Trabajo1 = trabajo1;
    }

    public void setTrabajo2(DatoBasico trabajo2) {
        Trabajo2 = trabajo2;
    }

    public void setTrabajo3(DatoBasico trabajo3) {
        Trabajo3 = trabajo3;
    }

    public void setCronograma(String cronograma) {
        this.Cronograma = cronograma;
    }

    public Cronograma generarCronograma(Dato periodo1, Dato periodo2, Dato periodo3){
        Cronograma cronogramaDias = new Cronograma();

        cronogramaDias.getPeriodo1().setInicio(periodo1.getInicio());
        cronogramaDias.getPeriodo1().setFin(periodo1.getFin());

        cronogramaDias.getPeriodo2().setInicio(periodo2.getInicio());
        cronogramaDias.getPeriodo2().setFin(periodo2.getFin());

        cronogramaDias.getPeriodo3().setInicio(periodo3.getInicio());
        cronogramaDias.getPeriodo3().setFin(periodo3.getFin());

        //generar los porcentajes de cada periodo
        int porcentaje1 = (int) (Math.random() * 11) + 30;
        int porcentaje2 = (int) (Math.random() * 11) + 35;
        int porcentaje3 = 100 - porcentaje1 - porcentaje2;

        //Agregar los porcentajes de cada periodo
        cronogramaDias.getPeriodo1().setPorcentaje(porcentaje1+"");
        cronogramaDias.getPeriodo2().setPorcentaje(porcentaje2+"");
        cronogramaDias.getPeriodo3().setPorcentaje(porcentaje3+"");

        //Calcular los volumenes de cada periodo
        float volumen = Float.parseFloat(Volumen);
        float volumen1 = volumen*((float)(porcentaje1/100.00));
        float volumen2 = volumen*((float)(porcentaje2/100.00));
        float volumen3 = volumen*((float)(porcentaje3/100.00));

        //Agregar los volumenes de cada periodo
        cronogramaDias.getPeriodo1().setVolumen(volumen1+"");
        cronogramaDias.getPeriodo2().setVolumen(volumen2+"");
        cronogramaDias.getPeriodo3().setVolumen(volumen3+"");

        //Generar cronograma
        cronogramaDias.generarCronogramaPeriodo();

        return cronogramaDias;
    }

    @Override
    public String toString() {
        return "Trabajo{" +
                "Volumen='" + Volumen + '\'' +
                ", Habilidad1='" + Trabajo1 + '\'' +
                ", Habilidad2='" + Trabajo2 + '\'' +
                ", Habilidad3='" + Trabajo3 + '\'' +
                ", Cronograma=" + this.Cronograma +
                '}';
    }
}
