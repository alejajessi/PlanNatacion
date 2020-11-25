package com.e.periodizacionnatacion.Clases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Esta clase es utilizada unicamente por la clase integrantes para guardar los diferentes
 * resultados de una prueba, la cual se puede realizar muchas veces.
 */
public class Prueba implements Serializable {

    private String nombre;
    private ArrayList<DatoBasico> resultadosPruebas;

    /**
     * Constructor vacio
     */
    public Prueba() {
        nombre = "";
        resultadosPruebas = new ArrayList<DatoBasico>();
    }

    /**
     * Constructor Completo
     * @param nombre
     * @param resultadosPruebas
     */
    public Prueba(String nombre, ArrayList<DatoBasico> resultadosPruebas) {
        this.nombre = nombre;
        this.resultadosPruebas = resultadosPruebas;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<DatoBasico> getResultadosPruebas() {
        return resultadosPruebas;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setResultadosPruebas(ArrayList<DatoBasico> resultadosPruebas) {
        this.resultadosPruebas = resultadosPruebas;
    }

    public void ordenarFechas(){
        if (resultadosPruebas.size()>1){
            sort(resultadosPruebas,0,(resultadosPruebas.size()-1));
        }
    }

    public void sort(ArrayList<DatoBasico> fecha, int inicio, int size) {
        if(inicio < size){
            //Encuentra el punto medio del vector.
            int middle = (inicio + size) / 2;

            //Divide la primera y segunda mitad (llamada recursiva).
            sort(fecha, inicio, middle);
            sort(fecha, middle+1, size);

            //Une las mitades.
            merge(fecha, inicio, middle, size);
        }
    }

    public void merge(ArrayList<DatoBasico> fecha, int inicio, int middle, int size) {
        //Encuentra el tamaño de los sub-vectores para unirlos.
        int n1 = middle - inicio + 1;
        int n2 = size - middle;

        //Vectores temporales.
        ArrayList<DatoBasico> leftArray = new ArrayList<DatoBasico>();
        ArrayList<DatoBasico> rightArray = new ArrayList<DatoBasico>();

        //Copia los datos a los arrays temporales.
        for (int i=0; i < n1; i++) {
            leftArray.add(fecha.get(inicio+i));
        }
        for (int j=0; j < n2; j++) {
            rightArray.add(fecha.get(middle + j + 1));
        }
        /* Une los vectorestemporales. */

        //Índices inicial del primer y segundo sub-vector.
        int i = 0;
        int j = 0;

        //Índice inicial del sub-vector arr[].
        int k = inicio;

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        String[] fecha1 = null;
        String[] fecha2 = null;

        //Ordenamiento.
        while (i < n1 && j < n2) {

            fecha1 = leftArray.get(i).getDato2().split("-");
            fecha2 = rightArray.get(j).getDato2().split("-");

            cal1.set(Integer.parseInt(fecha1[2]),Integer.parseInt(fecha1[1])-1,Integer.parseInt(fecha1[0]));
            cal2.set(Integer.parseInt(fecha2[2]),Integer.parseInt(fecha2[1])-1,Integer.parseInt(fecha2[0]));

            if (cal1.before(cal2) || leftArray.get(i).getDato2().equals(rightArray.get(j).getDato2())) {
                fecha.set(k,leftArray.get(i));
                i++;
            } else {
                fecha.set(k,rightArray.get(j));
                j++;
            }
            k++;
        }//Fin del while.

        /* Si quedan elementos por ordenar */
        //Copiar los elementos restantes de leftArray[].
        while (i < n1) {
            fecha.set(k,leftArray.get(i));
            i++;
            k++;
        }
        //Copiar los elementos restantes de rightArray[].
        while (j < n2) {
            fecha.set(k,rightArray.get(j));
            j++;
            k++;
        }
    }

    @Override
    public String toString() {
        return "Prueba{" +
                "nombre='" + nombre + '\'' +
                ", resultadosPruebas=" + resultadosPruebas +
                '}';
    }
}
