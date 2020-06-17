package com.e.periodizacionnatacion.Clases;

import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MacroCiclo implements Serializable {

    private String Nombre;
    private String ID;
    private String Inicio;
    private String Fin;
    private Trabajo DiasAgua;
    private Trabajo DiasTierra;
    private ArrayList<DatoBasico> Integrantes;

    /**
     * Constructor vacio
     */
    public MacroCiclo() {
        Nombre = "";
        this.ID = "";
        Inicio = "";
        Fin = "";
        DiasAgua = new Trabajo();
        DiasTierra = new Trabajo();
        Integrantes = new ArrayList<DatoBasico>();
    }

    /**
     * Constructor basico
     * @param nombre
     * @param ID
     * @param inicio
     * @param fin
     */
    public MacroCiclo(String nombre, String ID, String inicio, String fin) {
        Nombre = nombre;
        this.ID = ID;
        Inicio = inicio;
        Fin = fin;
        DiasAgua = new Trabajo();
        DiasTierra = new Trabajo();
        Integrantes = new ArrayList<DatoBasico>();
    }

    /**
     * Constructor Completo
     * @param nombre
     * @param ID
     * @param inicio
     * @param fin
     * @param diasAgua
     * @param diasTierra
     * @param integrantes
     */
    public MacroCiclo(String nombre, String ID, String inicio, String fin, Trabajo diasAgua, Trabajo diasTierra, ArrayList<DatoBasico> integrantes) {
        Nombre = nombre;
        this.ID = ID;
        Inicio = inicio;
        Fin = fin;
        DiasAgua = diasAgua;
        DiasTierra = diasTierra;
        Integrantes = integrantes;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getID() {
        return ID;
    }

    public String getInicio() {
        return Inicio;
    }

    public String getFin() {
        return Fin;
    }

    public Trabajo getDiasAgua() {
        return DiasAgua;
    }

    public Trabajo getDiasTierra() {
        return DiasTierra;
    }

    public ArrayList<DatoBasico> getIntegrantes() {
        return Integrantes;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setInicio(String inicio) {
        Inicio = inicio;
    }

    public void setFin(String fin) {
        Fin = fin;
    }

    public void setDiasAgua(Trabajo diasAgua) {
        DiasAgua = diasAgua;
    }

    public void setDiasTierra(Trabajo diasTierra) {
        DiasTierra = diasTierra;
    }

    public void setIntegrantes(ArrayList<DatoBasico> integrantes) {
        Integrantes = integrantes;
    }

    public ArrayList<Cronograma> GenerarCronogramasDeEntrenamiento(){

        ArrayList<Cronograma> cronogramas = new ArrayList<Cronograma>();

        //Se separa la fecha en dia-mes-año
        String[] inicio = Inicio.split("-");
        String[] fin = Fin.split("-");

        //Se configura la variable con la fecha de inicio
        Calendar fecha1 = Calendar.getInstance();
        fecha1.set(Integer.parseInt(inicio[2]),Integer.parseInt(inicio[1])-1,Integer.parseInt(inicio[0]));

        //Se configura la variable con la fecha de fin
        Calendar fecha2 = Calendar.getInstance();
        fecha2.set(Integer.parseInt(fin[2]),Integer.parseInt(fin[1])-1,Integer.parseInt(fin[0]));

        //Fecha para incrementar
        Calendar fechaIncremento = Calendar.getInstance();
        fechaIncremento.set(Integer.parseInt(inicio[2]),Integer.parseInt(inicio[1])-1,Integer.parseInt(inicio[0]));

        //Se calcula cuantos meses hay entre las fechas
        int meses = calcularMes(fecha1, fecha2);

        Dato periodo1 = new Dato();
        Dato periodo2 = new Dato();
        Dato periodo3 = new Dato();

        //Calcular el piso de meses/3
        double duracion = Math.floor(meses/3);

        //Duración de cada periodo
        int duracion1 = (int)duracion;
        int duracion2 = (int)duracion;
        int duracion3 = (int)duracion;

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        boolean dura3meses = false;
        int [] fecha = null;
        String inicioPeriodo ="";
        String finPeriodo = "";

        if (meses<12){
            if (meses==3){

                //Periodo 1 dura 1 mes y 1 semana
                inicioPeriodo = df.format(fechaIncremento.getTime());
                fecha = FechaIncrementoMesSemana(1,1,fechaIncremento);
                fechaIncremento.set(fecha[2],fecha[1],fecha[0]);
                finPeriodo = df.format(fechaIncremento.getTime());
                //Agrego las fechas al periodo
                periodo1.setInicio(inicioPeriodo);
                periodo1.setFin(finPeriodo);

                //Aumento 1 dia
                fecha = FechaIncrementoDia(1,fechaIncremento);
                fechaIncremento.set(fecha[2],fecha[1],fecha[0]);

                //Periodo 2 dura 1 mes y 1 semana
                inicioPeriodo = df.format(fechaIncremento.getTime());
                fecha = FechaIncrementoMesSemana(1,1,fechaIncremento);
                fechaIncremento.set(fecha[2],fecha[1],fecha[0]);
                finPeriodo = df.format(fechaIncremento.getTime());
                //Agrego las fechas al periodo
                periodo2.setInicio(inicioPeriodo);
                periodo2.setFin(finPeriodo);

                //Aumento 1 dia
                fecha = FechaIncrementoDia(1,fechaIncremento);
                fechaIncremento.set(fecha[2],fecha[1],fecha[0]);

                //Periodo 3 dura 2 semanas
                inicioPeriodo = df.format(fechaIncremento.getTime());
                fecha = FechaIncrementoMesSemana(0,2,fechaIncremento);
                fechaIncremento.set(fecha[2],fecha[1],fecha[0]);
                finPeriodo = df.format(fechaIncremento.getTime());
                //Agrego las fechas al periodo
                periodo3.setInicio(inicioPeriodo);
                periodo3.setFin(finPeriodo);

                //Aviso que el macrociclo dura 3 meses
                dura3meses = true;

            }else if ((meses%3)==0){

                double calculo = meses*0.3;
                //duracion3 se resta 1 hasta ser menor a calculo
                while (!(duracion3<calculo)){
                    duracion3--;
                }
            }else if (meses==7 || meses==10){
                duracion3--;
            }
        }else{

            double calculo = meses*0.2;
            //duracion3 se resta 1 hasta ser menor a calculo
            while (!(duracion3<calculo)){
                duracion3--;
            }
        }

        //Pregunto si el macrociclo dura 3 meses, en caso de sel falso incrementa la duracion del periodo 1 y 2
        if (!dura3meses){
            //Define si se incrementa duracion1(false) o duracion2(true)
            boolean acual = true;
            double suma = duracion1+duracion2+duracion3;
            //sumar 1 a duracion2 y despues a duracion1 hasta que suma sea igual a meses
            while (!(suma == meses)){
                if (acual){
                    duracion2++;
                    suma = duracion1+duracion2+duracion3;
                    acual=false;
                }else{
                    duracion1++;
                    suma = duracion1+duracion2+duracion3;
                    acual=true;
                }
            }

            //Agregar fechas de inicio y fin a los periodos

            //Periodo 1 dura duracion1
            inicioPeriodo = df.format(fechaIncremento.getTime());
            fecha = FechaIncrementoMesSemana(duracion1,0,fechaIncremento);
            fechaIncremento.set(fecha[2],fecha[1],fecha[0]);
            finPeriodo = df.format(fechaIncremento.getTime());

            //Agrego las fechas al periodo1
            periodo1.setInicio(inicioPeriodo);
            periodo1.setFin(finPeriodo);

            //Aumento 1 dia
            fecha = FechaIncrementoDia(1,fechaIncremento);
            fechaIncremento.set(fecha[2],fecha[1],fecha[0]);

            //Periodo 2 dura duracion2
            inicioPeriodo = df.format(fechaIncremento.getTime());
            fecha = FechaIncrementoMesSemana(duracion2,0,fechaIncremento);
            fechaIncremento.set(fecha[2],fecha[1],fecha[0]);
            finPeriodo = df.format(fechaIncremento.getTime());

            //Agrego las fechas al periodo2
            periodo2.setInicio(inicioPeriodo);
            periodo2.setFin(finPeriodo);

            //Aumento 1 dia
            fecha = FechaIncrementoDia(1,fechaIncremento);
            fechaIncremento.set(fecha[2],fecha[1],fecha[0]);

            //Periodo 3 dura duracion3
            inicioPeriodo = df.format(fechaIncremento.getTime());
            fecha = FechaIncrementoMesSemana(0,2,fechaIncremento);
            fechaIncremento.set(fecha[2],fecha[1],fecha[0]);
            finPeriodo = df.format(fechaIncremento.getTime());

            //Agrego las fechas al periodo3
            periodo3.setInicio(inicioPeriodo);
            periodo3.setFin(finPeriodo);
        }

        //Calcular las fechas cuando no son meses exactos
        fecha = FechaIncrementoMesSemana(meses,0,fecha1);
        fecha1.set(fecha[2],fecha[1],fecha[0]);
        int diasFecha1 = fecha1.get(Calendar.DAY_OF_YEAR);
        int diasFecha2 = fecha2.get(Calendar.DAY_OF_YEAR);
        int diferenciaDias = diasFecha2-diasFecha1;

        if (diferenciaDias>0){

            //Modificar fin de periodo 1
            fin = periodo1.getFin().split("-");
            fechaIncremento.set(Integer.parseInt(fin[2]),Integer.parseInt(fin[1])-1,Integer.parseInt(fin[0]));

            //Aumento diferenciaDias en dia
            fecha = FechaIncrementoDia(diferenciaDias,fechaIncremento);
            fechaIncremento.set(fecha[2],fecha[1],fecha[0]);
            finPeriodo = df.format(fechaIncremento.getTime());
            periodo1.setFin(finPeriodo);

            //Modificar inicio de periodo 2
            fecha = FechaIncrementoDia(1,fechaIncremento);
            fechaIncremento.set(fecha[2],fecha[1],fecha[0]);
            finPeriodo = df.format(fechaIncremento.getTime());
            periodo2.setInicio(finPeriodo);

            //Modificar fin de periodo 2
            fin = periodo2.getFin().split("-");
            fechaIncremento.set(Integer.parseInt(fin[2]),Integer.parseInt(fin[1])-1,Integer.parseInt(fin[0]));

            //Aumento dias en la cantidad de diferenciaDias
            fecha = FechaIncrementoDia(diferenciaDias,fechaIncremento);
            fechaIncremento.set(fecha[2],fecha[1],fecha[0]);
            finPeriodo = df.format(fechaIncremento.getTime());
            periodo2.setFin(finPeriodo);

            //Modificar inicio de periodo 3
            fecha = FechaIncrementoDia(1,fechaIncremento);
            fechaIncremento.set(fecha[2],fecha[1],fecha[0]);
            finPeriodo = df.format(fechaIncremento.getTime());
            periodo3.setInicio(finPeriodo);

            //Modificar fin de periodo 3
            fin = periodo3.getFin().split("-");
            fechaIncremento.set(Integer.parseInt(fin[2]),Integer.parseInt(fin[1])-1,Integer.parseInt(fin[0]));

            //Aumento dias en la cantidad de diferenciaDias
            fecha = FechaIncrementoDia(diferenciaDias,fechaIncremento);
            fechaIncremento.set(fecha[2],fecha[1],fecha[0]);
            finPeriodo = df.format(fechaIncremento.getTime());
            periodo3.setFin(finPeriodo);
        }

        cronogramas.add(DiasAgua.generarCronograma(periodo1,periodo2,periodo3));
        cronogramas.add(DiasTierra.generarCronograma(periodo1,periodo2,periodo3));
        return cronogramas;
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
        return "MacroCiclo{" +
                "Nombre='" + Nombre + '\'' +
                ", ID='" + ID + '\'' +
                ", Inicio='" + Inicio + '\'' +
                ", Fin='" + Fin + '\'' +
                ", DiasAgua=" + DiasAgua +
                ", DiasTierra=" + DiasTierra +
                ", Integrantes=" + Integrantes +
                '}';
    }
}
