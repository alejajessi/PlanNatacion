package com.e.periodizacionnatacion.Clases;

public class Trabajo {

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
