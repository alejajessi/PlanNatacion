package com.e.periodizacionnatacion.Clases;

public class Trabajo {

    private String Volumen;
    private String Habilidad1;
    private String Habilidad2;
    private String Habilidad3;
    private String Dias;
    private Cronograma Cronograma;

    /**
     * Constructor vacio
     */
    public Trabajo() {
        Volumen = "";
        Habilidad1 = "";
        Habilidad2 = "";
        Habilidad3 = "";
        Dias = "";
        this.Cronograma = new Cronograma();
    }

    /**
     * Constructor basico
     * @param volumen
     * @param habilidad1
     * @param habilidad2
     * @param habilidad3
     * @param dias
     */
    public Trabajo(String volumen, String habilidad1, String habilidad2, String habilidad3, String dias) {
        Volumen = volumen;
        Habilidad1 = habilidad1;
        Habilidad2 = habilidad2;
        Habilidad3 = habilidad3;
        Dias = dias;
        this.Cronograma = new Cronograma();
    }

    /**
     * Constructor completo
     * @param volumen
     * @param habilidad1
     * @param habilidad2
     * @param habilidad3
     * @param dias
     * @param cronograma
     */
    public Trabajo(String volumen, String habilidad1, String habilidad2, String habilidad3, String dias, Cronograma cronograma) {
        Volumen = volumen;
        Habilidad1 = habilidad1;
        Habilidad2 = habilidad2;
        Habilidad3 = habilidad3;
        Dias = dias;
        this.Cronograma = cronograma;
    }

    public String getVolumen() {
        return Volumen;
    }

    public String getHabilidad1() {
        return Habilidad1;
    }

    public String getHabilidad2() {
        return Habilidad2;
    }

    public String getHabilidad3() {
        return Habilidad3;
    }

    public String getDias() {
        return Dias;
    }

    public Cronograma getCronograma() {
        return this.Cronograma;
    }

    public void setVolumen(String volumen) {
        Volumen = volumen;
    }

    public void setHabilidad1(String habilidad1) {
        Habilidad1 = habilidad1;
    }

    public void setHabilidad2(String habilidad2) {
        Habilidad2 = habilidad2;
    }

    public void setHabilidad3(String habilidad3) {
        Habilidad3 = habilidad3;
    }

    public void setDias(String dias) {
        Dias = dias;
    }

    public void setCronograma(Cronograma cronograma) {
        this.Cronograma = cronograma;
    }

    @Override
    public String toString() {
        return "Trabajo{" +
                "Volumen='" + Volumen + '\'' +
                ", Habilidad1='" + Habilidad1 + '\'' +
                ", Habilidad2='" + Habilidad2 + '\'' +
                ", Habilidad3='" + Habilidad3 + '\'' +
                ", Dias='" + Dias + '\'' +
                ", Cronograma=" + this.Cronograma +
                '}';
    }
}
