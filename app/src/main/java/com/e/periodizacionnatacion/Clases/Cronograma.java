package com.e.periodizacionnatacion.Clases;

import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Cronograma implements Serializable {

    private String ID;
    private String tipo;
    private Dato Periodo1;
    private Dato Periodo2;
    private Dato Periodo3;

    /**
     * Cronstructor vacio
     */
    public Cronograma() {
        this.ID = "";
        tipo ="";
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
        tipo = "";
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
        this.tipo = "";
        Periodo1 = periodo1;
        Periodo2 = periodo2;
        Periodo3 = periodo3;
    }

    public String getID() {
        return ID;
    }

    public String getTipo() {
        return tipo;
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

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
        Log.w("periodo1", Periodo1.getFecha().size()+"");
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
        Log.w("periodo2", Periodo2.getFecha().size()+"");
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
        Log.w("periodo3", Periodo3.getFecha().size()+"");
        Periodo3.agregarVolumenFecha();
    }

    public ArrayList<Dato> agregarSemanasAMeses(Calendar inicio, Calendar fin){

        ArrayList<Dato> meses = new ArrayList<Dato>();
        ArrayList<Dato> semanas = new ArrayList<Dato>();

        int diasFecha1 = inicio.get(Calendar.DAY_OF_YEAR);
        int diasFecha2 = fin.get(Calendar.DAY_OF_YEAR);
        int diferencia = 0;

        //Dependiendo de si inicio y fin se encuentran en años diferentes calcula la diferencia de dias entre ambas
        int diferenciaAnios = fin.get(Calendar.YEAR)-inicio.get(Calendar.YEAR);

        if (diferenciaAnios > 0){
            //Calcula los dias si inicio y fin son de años diferentes y despues calcula el numero de semanas entre ellos
            int diasanios = diferenciaAnios*inicio.getActualMaximum(Calendar.DAY_OF_YEAR);
            diferencia=(diasFecha2+diasanios-diasFecha1)/7;
        }else{
            //Calcula las semanas si inicio y fin son de años diferentes
            diferencia = (diasFecha2-diasFecha1)/7;
        }

        //El modulo especifica el numero de semanas que tendra un mes
        int modulo4 = diferencia%4;
        int modulo5 = diferencia%5;
        int maximoSemanas = diferencia;
        if (modulo4 == 0 || modulo4 > 2){
            maximoSemanas = 4;
        }else if (modulo5 == 0 || modulo5 > 2){
            maximoSemanas = 5;
        }else if (diferencia != 2){
            maximoSemanas=4;
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
            fecha = FechaIncrementoDia(6,fechaIncremento);
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
                Log.e("Meses>>"+meses.size(),"Semanas>>"+maximoSemanas);
            }
            //Incremento 1 dia
            fecha = FechaIncrementoDia(1,fechaIncremento);
            fechaIncremento.set(fecha[2],fecha[1],fecha[0]);
        }

        //Si el for llega a ser igual a diferencia y numSemana no a llegado al maximo
        //Se agregan esas semanas al ultimo mes del array de meses
        if (numSemanas>0 && numSemanas<maximoSemanas){
            mes = meses.get(meses.size()-1);
            for (int i=0; i<semanas.size(); i++){
                mes.getFecha().add(semanas.get(i));
                mes.setFin(semanas.get(i).getFin());
            }
            meses.set(meses.size()-1,mes);
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
                fechaIncremento.set(Integer.parseInt(fechafin[2]),Integer.parseInt(fechafin[1])-1,(Integer.parseInt(fechafin[0])));


                //Incrementar la fecha de fin por en la diferencia
                //Se agrega 1 a la diferencia ya que la modificacion de la variable fechaIncremento
                // no tiene en cuenta el ultimo incremento hecho en el for
                fecha = FechaIncrementoDia((diferencia+1),fechaIncremento);
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
            Log.v("Meses>>"+meses.size(),"Semanas>>"+meses.get(meses.size()-1).getFecha().size());

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

    public int[] FechaDisminucionDia(int dias, Calendar inicio){
        //Arreglo con la fecha a incrementar [dia,mes,año]
        int[] fecha = new int[3];
        int day = inicio.get(Calendar.DATE);
        int month = inicio.get(Calendar.MONTH)+1;
        int year = inicio.get(Calendar.YEAR);
        fecha[0]=day-dias;
        fecha[1] = month;
        fecha[2]=year;

        //verificar si al disminuir pasa al anterior mes o año
        if (fecha[0]<1){
            fecha[1] = fecha[1]-1;
            if (fecha[1]<1){
                fecha[2]=fecha[2]-1;
                fecha[1]= 12-fecha[1];
            }
            inicio.set(fecha[2],(fecha[1]-1),1);
            fecha[0] = inicio.getActualMaximum(Calendar.DAY_OF_MONTH)-fecha[0];
        }
        fecha[1]  = fecha[1]-1;
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

    public int calcularDias(Calendar inicio, Calendar fin){
        int dias = 0;
        while (inicio.before(fin)||inicio.equals(fin)){
            dias++;
            inicio.add(Calendar.DATE,1);
        }
        return dias;
    }

    public void generarDias(DatoBasico Trabajo1,DatoBasico Trabajo2,DatoBasico Trabajo3,DatoBasico Trabajo4,DatoBasico Trabajo5){

        String [] trabajo1 = Trabajo1.getDato1().split("-");
        String [] trabajo2 = Trabajo2.getDato1().split("-");
        String [] trabajo3 = Trabajo3.getDato1().split("-");
        String [] trabajo4 = null;
        String [] trabajo5 = null;

        if (tipo.equals("Tierra")){
            trabajo4 = Trabajo4.getDato1().split("-");
            trabajo5 = Trabajo5.getDato1().split("-");
        }
        agregarDiasASemanas("Periodo1",Periodo1,trabajo1,trabajo2,trabajo3,trabajo4,trabajo5);
        agregarDiasASemanas("Periodo2",Periodo2,trabajo1,trabajo2,trabajo3,trabajo4,trabajo5);
        if (tipo.equals("Agua")){
            definirFinalPeriodo2DiasAgua();
        }
        agregarDiasASemanas("Periodo3",Periodo3,trabajo1,trabajo2,trabajo3,trabajo4,trabajo5);

    }

    public void agregarDiasASemanas(String actual, Dato periodo, String[] trabajo1, String[] trabajo2, String[] trabajo3,
                                    String[] trabajo4, String[] trabajo5){

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        ArrayList<Dato> meses = periodo.getFecha();
        int numMeses = meses.size();

        for (int i=0; i< numMeses; i++){

            ArrayList<Dato> semanas = meses.get(i).getFecha();
            int numSemanas = semanas.size();

            for (int j=0; j<numSemanas; j++){

                Dato semana = semanas.get(j);

                int repiteTrabajo1 = 0;
                int repiteTrabajo2 = 0;
                int repiteTrabajo3 = 0;
                int repiteTrabajo4 = 0;
                int repiteTrabajo5 = 0;

                //Se separa la fecha en dia-mes-año
                String[] inicioSemana = semana.getInicio().split("-");
                String[] finSemana = semana.getFin().split("-");

                //Se configura la variable con la fecha de inicio
                Calendar inicio = Calendar.getInstance();
                inicio.set(Integer.parseInt(inicioSemana[2]),Integer.parseInt(inicioSemana[1])-1,Integer.parseInt(inicioSemana[0]));

                //Se configura la variable con la fecha de fin
                Calendar fin = Calendar.getInstance();
                fin.set(Integer.parseInt(finSemana[2]),Integer.parseInt(finSemana[1])-1,Integer.parseInt(finSemana[0]));

                //Fecha para incrementar
                Calendar fechaIncremento = Calendar.getInstance();
                fechaIncremento.set(inicio.get(Calendar.YEAR),inicio.get(Calendar.MONTH),inicio.get(Calendar.DATE));

                ArrayList<Dia> dias = new ArrayList<Dia>();
                int valorDiaDeSemana = 0;
                String diaDeLaSemana = "";

                //VAgrego el dia de la semana y verifico si algun trabajo se realiza ese dia
                while (fechaIncremento.before(fin)||fechaIncremento.equals(fin)){

                    //Agrego el dia al array de dias
                    Dia dia = new Dia(df.format(fechaIncremento.getTime()));

                    //Consulto que dia de la semana es (Lunes,Martes,Miercoles,Jueves o Viernes)
                    valorDiaDeSemana = fechaIncremento.get(Calendar.DAY_OF_WEEK);

                    //Agrego el dia de la semana correspondiente al objeto dia
                    dia.setDiaDeSemana(definirDiaDeSemana(valorDiaDeSemana));
                    dias.add(dia);

                    diaDeLaSemana = dia.getDiaDeSemana();
                    //Sumo la cantidad devuelta a la variable respectiva
                    repiteTrabajo1= repiteTrabajo1+verificarCoincidenciaDiaDeSemana(diaDeLaSemana,trabajo1);
                    repiteTrabajo2= repiteTrabajo2+verificarCoincidenciaDiaDeSemana(diaDeLaSemana,trabajo2);
                    repiteTrabajo3= repiteTrabajo3+verificarCoincidenciaDiaDeSemana(diaDeLaSemana,trabajo3);

                    if (tipo.equals("Tierra")){
                        repiteTrabajo4= repiteTrabajo4+verificarCoincidenciaDiaDeSemana(diaDeLaSemana,trabajo4);
                        repiteTrabajo5= repiteTrabajo5+verificarCoincidenciaDiaDeSemana(diaDeLaSemana,trabajo5);
                    }

                    //Incremento un dia
                    fechaIncremento.add(Calendar.DATE,1);
                }

                float volumen = Float.parseFloat(semana.getVolumen());

                //Porcentajes de cada trabajo
                float volTrabajo1 = 0;
                float volTrabajo2 = 0;
                float volTrabajo3 = 0;
                float volTrabajo4 = 0;
                float volTrabajo5 = 0;

                if (tipo.equals("Agua")) {
                    volTrabajo1 = (volumen * ((float) (65.00 / 100.00))) / repiteTrabajo1;
                    volTrabajo2 = (volumen * ((float) (33.00 / 100.00))) / repiteTrabajo2;
                    volTrabajo3 = (volumen * ((float) (2.00 / 100.00))) / repiteTrabajo3;
                }else{
                    volTrabajo1 = (volumen * ((float) ( 50.00 / 100.00))) / repiteTrabajo1;
                    volTrabajo2 = (volumen * ((float) ( 15.00 / 100.00))) / repiteTrabajo2;
                    volTrabajo3 = (volumen * ((float) ( 15.00 / 100.00))) / repiteTrabajo3;
                    volTrabajo4 = (volumen * ((float) ( 10.00 / 100.00))) / repiteTrabajo4;
                    volTrabajo5 = (volumen * ((float) ( 10.00 / 100.00))) / repiteTrabajo5;
                }

                int numDias = dias.size();
                boolean cualResis = true; //Se utiliza en el preriodo1 para variar entre resistencias
                int incremento = 1;
                if (actual.equals("Periodo3") && j==1){
                    incremento = 8;
                }

                for (int k=0;k<numDias;k++){

                    ////Consulto que dia de la semana es (Lunes,Martes,Miercoles,Jueves o Viernes)
                    diaDeLaSemana = definirDiaDeSemana(inicio.get(Calendar.DAY_OF_WEEK));


                    //Agrego el volumen del trabajo1 si este se realiza ese dia
                    if (verificarCoincidenciaDiaDeSemana(diaDeLaSemana,trabajo1)>0){
                        if (tipo.equals("Agua")){

                            //Pregunto si estoy en las ultimas dos semanas del Periodo2
                            //Los valores de estas semanas se agregan despues
                            if (actual.equals("Periodo2") && i==(numMeses-1) && j>=(numSemanas-2)){
                                dias.get(k).setTipoHabilidad1("Pendiente");
                                dias.get(k).setVolHabilidad1(volTrabajo1+"");
                            }else{

                                //AGREGAR VOLUMEN DEPENDIENDO DE LA RESISTENCIA
                                String[] resistencia = definirResistencia(actual,numMeses,i,numSemanas,j,cualResis,incremento);
                                dias.get(k).setTipoHabilidad1(resistencia[0]);

                                float volResis = Float.parseFloat(resistencia[1]);
                                float volDiferencia = volTrabajo1-volResis;

                                if (volDiferencia>=0){
                                    dias.get(k).setVolHabilidad1(resistencia[1]);
                                }else {
                                    dias.get(k).setVolHabilidad1((volResis+volDiferencia)+"");
                                }
                            }

                        }else {
                            dias.get(k).setVolHabilidad1(volTrabajo1 + "");
                        }
                    }else if (tipo.equals("Agua")) {

                        //Pregunto si estoy en las ultimas dos semanas del Periodo2
                        //y dejo aviso que en este dia no hay trabajo
                        if (actual.equals("Periodo2") && i == (numMeses - 1) && j >= (numSemanas - 2)) {
                            dias.get(k).setTipoHabilidad1("NoTrabajo");
                            dias.get(k).setVolHabilidad1(volTrabajo1+"");
                        }
                    }

                    //Agrego el volumen del trabajo2 si este se realiza ese dia
                    if (verificarCoincidenciaDiaDeSemana(diaDeLaSemana,trabajo2)>0){
                        dias.get(k).setVolHabilidad2(volTrabajo2+"");
                    }

                    //Agrego el volumen del trabajo3 si este se realiza ese dia
                    if (verificarCoincidenciaDiaDeSemana(diaDeLaSemana,trabajo3)>0){
                        dias.get(k).setVolHabilidad3(volTrabajo3+"");
                    }

                    if (tipo.equals("Tierra")){
                        //Agrego el volumen del trabajo4 si este se realiza ese dia
                        if (verificarCoincidenciaDiaDeSemana(diaDeLaSemana,trabajo4)>0){
                            dias.get(k).setVolHabilidad4(volTrabajo4+"");
                        }

                        //Agrego el volumen del trabajo5 si este se realiza ese dia
                        if (verificarCoincidenciaDiaDeSemana(diaDeLaSemana,trabajo5)>0){
                            dias.get(k).setVolHabilidad5(volTrabajo5+"");
                        }
                    }

                    if (actual.equals("Periodo3")){
                        incremento++;
                    }

                    //Incremento 1 dia
                    inicio.add(Calendar.DATE,1);
                }

                //Agrego los dias a la semana recpectiva
                semana.setDias(dias);
            }

        }

    }

    public String definirDiaDeSemana(int diaDeSemana){
        String dia ="";
        switch (diaDeSemana) {
            case 1:
                dia = "Domingo";
                break;
            case 2:
                dia = "Lunes";
                break;
            case 3:
                dia = "Martes";
                break;
            case 4:
                dia = "Miércoles";
                break;
            case 5:
                dia = "Jueves";
                break;
            case 6:
                dia = "Viernes";
                break;
            case 7:
                dia = "Sábado";
                break;
        }
        return dia;
    }

    public int verificarCoincidenciaDiaDeSemana(String diaDeSemana, String[] trabajo){
        int resultado = 0;
        for (int i=0;i<trabajo.length;i++){
            if (trabajo[i].equals(diaDeSemana)){
                resultado++;
                break;
            }
        }
        return resultado;
    }

    public String[] definirResistencia(String periodo, int numMeses, int mes, int numSemanas, int semana, boolean cualResis, int incremento){
        String[] resistencia = new String[2]; // [0] es la resistencia y [1] es el volumen

        //Pregunto en que periodo estamos
        if (periodo.equals("Periodo1")){ //Si el periodo es 1

            //Pregunto la cantidad de meses del periodo para saber
            // en que momento asiganar una resistencia diferente
            if (numMeses>2){

                int mitad = (int) Math.floor(numMeses/2);
                //Pregunto si el mes actual es manor, mayor o igual a la mitad
                if (mes < mitad){ //Si es menor, agrego R1

                    resistencia[0]="R1";
                    resistencia[1]="5000";

                }else if (mes > mitad){ //Si es mayor, agrego R2

                    resistencia[0]="R2";
                    resistencia[1]="3000";

                }else{ //Si es igual, varia entre R1 y R2

                    if (cualResis){//Si es TRUE agrega R1

                        resistencia[0]="R1";
                        resistencia[1]="5000";

                    }else{//Si es FALSE agrega R2

                        resistencia[0]="R2";
                        resistencia[1]="3000";
                    }
                    cualResis = !cualResis;
                }
            }else if (numMeses>1){

                //Pregunto si la semana actual y el mes para definir las reistencias
                if (mes==0 && semana<(numSemanas-1)){

                    resistencia[0]="R1";
                    resistencia[1]="5000";

                }else if (mes==1 && semana>0){

                    resistencia[0]="R2";
                    resistencia[1]="3000";

                }else{ //Si es igual, varia entre R1 y R2

                    if (cualResis){//Si es TRUE agrega R1

                        resistencia[0]="R1";
                        resistencia[1]="5000";

                    }else{//Si es FALSE agrega R2

                        resistencia[0]="R2";
                        resistencia[1]="3000";
                    }
                    cualResis = !cualResis;
                }
            }else{

                int mitad = (int) Math.floor(numSemanas/2);

                //Pregunto si la semana actual es manor, mayor o igual a la mitad
                if (semana < mitad){ //Si es menor, agrego R1

                    resistencia[0]="R1";
                    resistencia[1]="5000";

                }else if (semana > mitad){ //Si es mayor, agrego R2

                    resistencia[0]="R2";
                    resistencia[1]="3000";

                }else{ //Si es igual, varia entre R1 y R2

                    if (cualResis){//Si es TRUE agrega R1

                        resistencia[0]="R1";
                        resistencia[1]="5000";

                    }else{//Si es FALSE agrega R2

                        resistencia[0]="R2";
                        resistencia[1]="3000";
                    }
                    cualResis = !cualResis;
                }

            }
        }else if (periodo.equals("Periodo2")){

            if (mes==(numMeses-1) && semana>=(numSemanas-2)){

                resistencia[0]="pendiente";
                resistencia[1]="0";

            }else{
                resistencia[0]="R2";
                resistencia[1]="3000";
            }
        }else{ //Si esta en periodo3

            //Pregunto el valor del incremento para asignar la resistencia correspondiente
            if (incremento == 1 || incremento == 4 || incremento == 6 || incremento == 9){

                resistencia[0]="R2";
                resistencia[1]="3000";

            }else if (incremento == 2 || incremento == 5){

                resistencia[0]="RL";
                resistencia[1]="1200";

            }else if (incremento == 3 || incremento == 7 || incremento == 10){

                resistencia[0]="R3";
                resistencia[1]="2000";

            }else if (incremento == 8 || incremento == 11){

                resistencia[0]="TL";
                resistencia[1]="400";

            }else{

                resistencia[0]="R1";
                resistencia[1]="5000";
            }
        }
         return resistencia;
    }

    public void definirFinalPeriodo2DiasAgua(){

        //Pido el numero de meses del periodo y el ultimo mes
        int numMeses = Periodo2.getFecha().size();
        Dato mes = Periodo2.getFecha().get(numMeses-1);

        //Pido el numero de semanas del ultimo mes
        int numSemanas = mes.getFecha().size();

        //Penultima semana del ultimo mes y pido los dias de esa semana
        Dato semana1 = mes.getFecha().get(numSemanas-2);
        ArrayList<Dia> diasSem1 = semana1.getDias();
        //Ultima semana del ultimo mes y pido los dias de esa semana
        Dato semana2 = mes.getFecha().get(numSemanas-1);
        ArrayList<Dia> diasSem2 = semana2.getDias();

        //Sumo la cantidad de dias de las dos semanas
        int dias = diasSem1.size()+diasSem2.size();

        //Verifico si la cantidad de dias es igual, menos o mayor a 14
        int decremento = 14;
        int diferencia = dias-decremento;
        if (diferencia>0){ //Si es mayor, agrego la diferencia a la variable decremento
            decremento = decremento+diferencia;
        }else if (diferencia<0){ //Si es menor, disminuto la diferencia a la variable decremento
            decremento = decremento+diferencia;
        }

        float volHabilidad1 = Float.parseFloat(diasSem1.get(0).getVolHabilidad1());
        String volumenDia = "";

        //Agrego las resistencias dependiendo del valor
        // de la variable decremento a los dias en la penultima semana
        for (int i=0; i<diasSem1.size(); i++){

            if (decremento==11 || decremento==9 || decremento==7 || decremento==5 ||
                    decremento==3 || decremento==1){ //En estos casos agrego R3 al tipo y volumen de habilidad1

                diasSem1.get(i).setTipoHabilidad1("R3");
                volumenDia = "2000";

            }else if (decremento==4){ //En este caso agrego RL al tipo y volumen de habilidad1

                diasSem1.get(i).setTipoHabilidad1("RL");
                volumenDia = "1200";

            }else{//Para el resto de casos agrego R2 al tipo y volumen de habilidad1

                diasSem1.get(i).setTipoHabilidad1("R2");
                volumenDia = "3000";
            }

            //Verificar si todavia hay volumen disponible
            float volResis = Float.parseFloat(volumenDia);
            float volDiferencia = volHabilidad1-volResis;
            //Verifica si trabaja ese dia
            if (!diasSem1.get(i).getTipoHabilidad1().equals("NoTrabajo")){
                if (volDiferencia>=0){
                    diasSem1.get(i).setVolHabilidad1(volumenDia);
                }else {
                    diasSem1.get(i).setVolHabilidad1((volResis+volDiferencia)+"");
                }
            }else{
                diasSem1.get(i).setTipoHabilidad1("");
                diasSem1.get(i).setVolHabilidad1("");
            }
            //resto 1 a la variable decremento
            decremento--;
        }

        volHabilidad1 = Float.parseFloat(diasSem2.get(0).getVolHabilidad1());
        //Agrego las resistencias dependiendo del valor
        // de la variable decremento a los dias en la ultima semana
        for (int i=0; i<diasSem2.size(); i++){

            if (decremento==11 || decremento==9 || decremento==7 || decremento==5 ||
                    decremento==3 || decremento==1){ //En estos casos agrego R3 al tipo y volumen de habilidad1

                diasSem2.get(i).setTipoHabilidad1("R3");
                volumenDia = "2000";

            }else if (decremento==4){ //En este caso agrego RL al tipo y volumen de habilidad1

                diasSem2.get(i).setTipoHabilidad1("RL");
                volumenDia = "1200";

            }else{//Para el resto de casos agrego R2 al tipo y volumen de habilidad1

                diasSem2.get(i).setTipoHabilidad1("R2");
                volumenDia = "3000";
            }

            //Verificar si todavia hay volumen disponible
            float volResis = Float.parseFloat(volumenDia);
            float volDiferencia = volHabilidad1-volResis;
            //Verifica si trabaja ese dia
            if (!diasSem2.get(i).getTipoHabilidad1().equals("NoTrabajo")){
                if (volDiferencia>=0){
                    diasSem2.get(i).setVolHabilidad1(volumenDia);
                }else {
                    diasSem2.get(i).setVolHabilidad1((volResis+volDiferencia)+"");
                }
            }else{
                diasSem2.get(i).setTipoHabilidad1("");
                diasSem2.get(i).setVolHabilidad1("");
            }

            //resto 1 a la variable decremento
            decremento--;
        }
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
