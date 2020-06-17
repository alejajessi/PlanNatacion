package com.e.periodizacionnatacion.Clases;

import java.io.Serializable;

/**
 * Esta clase es utilizada para asignar datos especificos en los ArrayList
 * de las clases Usuario, MacroCiclo, Trabajo y Prueba
 */
public class DatoBasico implements Serializable {

    /**
     * Esta variable se Utiliza para asignar un nombre en las clases Usuario y MacroCiclo
     * En la clase Prueba se utiliza para asignar un tiempo
     * En la clase Trabajo se utiliza para asignar los dias de trabajo
     */
    private String dato1;

    /**
     * Esta variable se Utiliza para asignar un ID en las clases Usuario y MacroCiclo
     * En la clase Prueba se utiliza para asignar una fecha
     * En la clase Trabajo se utiliza para asignar los porcentajes de trabajo
     */
    private String dato2;

    /**
     * Constructor vacio
     */
    public DatoBasico() {
        dato1 = "";
        dato2 = "";
    }

    /**
     * Constructor Completo
     * @param dato1 Nombre o Tiempo según la clase
     * @param dato2 ID o Fecha según la clase
     */
    public DatoBasico(String dato1, String dato2) {
        this.dato1 = dato1;
        this.dato2 = dato2;
    }

    public String getDato1() {
        return dato1;
    }

    public String getDato2() {
        return dato2;
    }

    public void setDato1(String dato1) {
        this.dato1 = dato1;
    }

    public void setDato2(String dato2) {
        this.dato2 = dato2;
    }

    @Override
    public String toString() {
        return "DatoBasico{" +
                "dato1='" + dato1 + '\'' +
                ", dato2='" + dato2 + '\'' +
                '}';
    }
}
