package com.e.periodizacionnatacion.Clases;

public class Cronograma {

    private String ID;
    private Dato Periodo1;
    private Dato Periodo2;
    private Dato Periodo3;

    /**
     * Cronstructor vacio
     */
    public Cronograma() {
        this.ID = "";
        Periodo1 = new Dato();
        Periodo2 = new Dato();
        Periodo3 = new Dato();
    }

    /**
     * Constructor basico
     * @param ID
     */
    public Cronograma(String ID) {
        this.ID = ID;
        Periodo1 = new Dato();
        Periodo2 = new Dato();
        Periodo3 = new Dato();
    }

    /**
     * Constructor completo
     * @param ID
     * @param periodo1
     * @param periodo2
     * @param periodo3
     */
    public Cronograma(String ID, Dato periodo1, Dato periodo2, Dato periodo3) {
        this.ID = ID;
        Periodo1 = periodo1;
        Periodo2 = periodo2;
        Periodo3 = periodo3;
    }

    public String getID() {
        return ID;
    }

    public Dato getPeriodo1() {
        return Periodo1;
    }

    public Dato getPeriodo2() {
        return Periodo2;
    }

    public Dato getPeriodo3() {
        return Periodo3;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setPeriodo1(Dato periodo1) {
        Periodo1 = periodo1;
    }

    public void setPeriodo2(Dato periodo2) {
        Periodo2 = periodo2;
    }

    public void setPeriodo3(Dato periodo3) {
        Periodo3 = periodo3;
    }

    @Override
    public String toString() {
        return "Cronograma{" +
                "ID='" + ID + '\'' +
                ", Periodo1=" + Periodo1 +
                ", Periodo2=" + Periodo2 +
                ", Periodo3=" + Periodo3 +
                '}';
    }
}
