package com.e.periodizacionnatacion.Clases;

import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Cronograma implements Serializable {

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

    public void generarCronogramaPeriodo(){

        //Periodo 1
        //Se separa la fecha en dia-mes-año
        String[] inicio = Periodo1.getInicio().split("-");
        String[] fin = Periodo1.getFin().split("-");

        //Se configura la variable con la fecha de inicio
        Calendar inicioPeriodo = Calendar.getInstance();
        inicioPeriodo.set(Integer.parseInt(inicio[2]),Integer.parseInt(inicio[1])-1,Integer.parseInt(inicio[0]));

        //Se configura la variable con la fecha de fin
        Calendar finPeriodo = Calendar.getInstance();
        finPeriodo.set(Integer.parseInt(fin[2]),Integer.parseInt(fin[1])-1,Integer.parseInt(fin[0]));

        Periodo1.setFecha(agregarSemanasAMeses(inicioPeriodo,finPeriodo));
        Periodo1.agregarVolumenFecha();

        //Periodo 2
        //Se separa la fecha en dia-mes-año
        inicio = Periodo2.getInicio().split("-");
        fin = Periodo2.getFin().split("-");

        //Se configura la variable con la fecha de inicio
        inicioPeriodo.set(Integer.parseInt(inicio[2]),Integer.parseInt(inicio[1])-1,Integer.parseInt(inicio[0]));

        //Se configura la variable con la fecha de fin
        finPeriodo.set(Integer.parseInt(fin[2]),Integer.parseInt(fin[1])-1,Integer.parseInt(fin[0]));

        Periodo2.setFecha(agregarSemanasAMeses(inicioPeriodo,finPeriodo));
        Periodo2.agregarVolumenFecha();

        //Periodo 3
        //Se separa la fecha en dia-mes-año
        inicio = Periodo3.getInicio().split("-");
        fin = Periodo3.getFin().split("-");

        //Se configura la variable con la fecha de inicio
        inicioPeriodo.set(Integer.parseInt(inicio[2]),Integer.parseInt(inicio[1])-1,Integer.parseInt(inicio[0]));

        //Se configura la variable con la fecha de fin
        finPeriodo.set(Integer.parseInt(fin[2]),Integer.parseInt(fin[1])-1,Integer.parseInt(fin[0]));

        Periodo3.setFecha(agregarSemanasAMeses(inicioPeriodo,finPeriodo));
        Periodo3.agregarVolumenFecha();
    }

    public ArrayList<Dato> agregarSemanasAMeses(Calendar inicio, Calendar fin){

        ArrayList<Dato> meses = new ArrayList<Dato>();
        ArrayList<Dato> semanas = new ArrayList<Dato>();

        int diasFecha1 = inicio.get(Calendar.DAY_OF_YEAR);
        int diasFecha2 = fin.get(Calendar.DAY_OF_YEAR);
        int diferencia = (diasFecha2-diasFecha1)/7;

        //El modulo especifica el numero de semanas que tendra un mes
        int modulo4 = diferencia%4;
        int modulo5 = diferencia%5;
        int maximoSemanas = diferencia;
        if (modulo4 == 0 || modulo4 > 2){
            maximoSemanas = 4;
        }else if (modulo5 == 0 || modulo5 > 2){
            maximoSemanas = 5;
        }

        //Fecha para incrementar
        Calendar fechaIncremento = Calendar.getInstance();
        fechaIncremento.set(inicio.get(Calendar.YEAR),inicio.get(Calendar.MONTH),inicio.get(Calendar.DATE));

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        int [] fecha = null;

        int numSemanas = 0;
        Dato mes = null;
        Dato semana = null;

        //Agrego las semanas al array de semanas, el array de semanas al meses y los meses al array de meses
        for (int i=0; i<diferencia;i++){

            if (numSemanas==0){
                //Inicializo el array de semanas
                semanas = new ArrayList<Dato>();
           }
            //Inicializo la semana
            semana = new Dato();

            //Agrego la fecha de inicio
            semana.setInicio(df.format(fechaIncremento.getTime()));

            //Aumento la fecha en 1 semana y agrego la fecha al fin de la semana
            fecha = FechaIncrementoDia(7,fechaIncremento);
            fechaIncremento.set(fecha[2],fecha[1],fecha[0]);
            semana.setFin(df.format(fechaIncremento.getTime()));

            //Agrego la semana al array de semanas
            semanas.add(semana);
            numSemanas++;

            if (numSemanas == 1){

                //Inicializo el mes y agrego la fecha de inicio
                mes = new Dato();
                mes.setInicio(semana.getInicio());

            }else if (numSemanas == maximoSemanas){

                //Agrego la fecha de fin del mes
                mes.setFin(semana.getFin());

                //Agrego el array de semanas al mes
                mes.setFecha(semanas);

                //Agrego el mes al array de meses
                meses.add(mes);
                numSemanas=0;
            }
            //Incremento 1 dia
            fecha = FechaIncrementoDia(1,fechaIncremento);
            fechaIncremento.set(fecha[2],fecha[1],fecha[0]);
        }

        //Calcular las fechas cuando no son semanas exactas
        diasFecha1 = fechaIncremento.get(Calendar.DAY_OF_YEAR);
        diferencia = diasFecha2-diasFecha1;

        if (diferencia > 0){
            if (diferencia < 4){
                //Posicion ultima semana
                int pos = mes.getFecha().size()-1;

                //Pedir la fecha de fin de la ultima semana
                String[] fechafin = semana.getFin().split("-");
                fechaIncremento.set(Integer.parseInt(fechafin[2]),Integer.parseInt(fechafin[1])-1,Integer.parseInt(fechafin[0]));

                //Incrementar la fecha de fin por en la diferencia
                fecha = FechaIncrementoDia(diferencia,fechaIncremento);
                fechaIncremento.set(fecha[2],fecha[1],fecha[0]);

                //Cambio la fecha de fin de la ultima semana
                mes.getFecha().get(pos).setFin(df.format(fechaIncremento.getTime()));

            }else{
                //Inicializo la semana
                semana = new Dato();

                //Agrego la fecha de inicio
                semana.setInicio(df.format(fechaIncremento.getTime()));

                //Incrementar la fecha de fin por en la diferencia
                fecha = FechaIncrementoDia(diferencia,fechaIncremento);
                fechaIncremento.set(fecha[2],fecha[1],fecha[0]);

                //Agrego la fecha de fin a la semana y al mes
                semana.setFin(df.format(fechaIncremento.getTime()));


                //Agrego la nueva semana al mes
                mes.getFecha().add(semana);
            }
            //Modifico la fecha de fin del ultimo mes
            mes.setFin(df.format(fechaIncremento.getTime()));

            //Modifico el ultimo mes
            int posUltimoMes = meses.size()-1;
            meses.set(posUltimoMes,mes);
        }

        return meses;
    }

    public int[] FechaIncrementoMesSemana(int meses, int semanas, Calendar inicio){
        //Arreglo con la fecha a incrementar [dia,mes,año]
        int[] fecha = new int[3];
        int day = inicio.get(Calendar.DATE);
        int month = inicio.get(Calendar.MONTH)+1;
        int year = inicio.get(Calendar.YEAR);
        fecha[0]=day+(7*semanas);
        fecha[1]=month+meses;
        fecha[2]=year;
        int diasMax = inicio.getActualMaximum(Calendar.DAY_OF_MONTH);
        //verificar si el incremento pasa al siguiente mes
        if (fecha[0]>diasMax){
            fecha[0] = fecha[0]-diasMax;
            fecha[1]=fecha[1]+1;
            meses=meses+1;
        }
        //Verificar si el incremento aumenta mas de un año
        while (meses>12){
            fecha[1]=fecha[1]-12;
            fecha[2]=fecha[2]+1;
            meses=meses-12;
        }
        //Verificar si el incremento pasa al siguiente año
        if (fecha[1]<=12){
            fecha[1]=fecha[1]-1;
            fecha[2]=fecha[2];
        }else{
            fecha[1]=(fecha[1]-12)-1;
            fecha[2]=fecha[2]+1;
        }

        return fecha;
    }

    public int[] FechaIncrementoDia(int dias, Calendar inicio){
        //Arreglo con la fecha a incrementar [dia,mes,año]
        int[] fecha = new int[3];
        int day = inicio.get(Calendar.DATE);
        int month = inicio.get(Calendar.MONTH)+1;
        int year = inicio.get(Calendar.YEAR);
        fecha[0]=day+dias;
        fecha[1] = month;

        int diasMax = inicio.getActualMaximum(Calendar.DAY_OF_MONTH);
        //verificar si el incremento pasa al siguiente mes
        if (fecha[0]>diasMax){
            fecha[0] = fecha[0]-diasMax;
            fecha[1] = fecha[1]+1;
        }

        //Verificar si el incremento pasa al siguiente año
        if (fecha[1]<=12){
            fecha[1]=fecha[1]-1;
            fecha[2]=year;
        }else{
            fecha[1]=(fecha[1]-12)-1;
            fecha[2]=year+1;
        }
        return fecha;
    }

    public int calcularMes(Calendar inicio, Calendar fin){
        int diferenciaAnios = calcularAnio(inicio,fin);
        int diferencia = (diferenciaAnios*12)+(fin.get(Calendar.MONTH)-inicio.get(Calendar.MONTH));
        if (fin.get(Calendar.DATE) < inicio.get(Calendar.DATE)){
            diferencia--;
        }
        return diferencia;
    }

    public int calcularAnio(Calendar inicio, Calendar fin){
        int diferencia = fin.get(Calendar.YEAR) - inicio.get(Calendar.YEAR);
        if (fin.get(Calendar.DAY_OF_YEAR) > inicio.get(Calendar.DAY_OF_YEAR) && diferencia != 0){
            diferencia--;
        }
        return diferencia;
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
