package com.specificday;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.callback.CallBackListener;
import com.e.periodizacionnatacion.Clases.Cronograma;
import com.e.periodizacionnatacion.Clases.Dato;
import com.e.periodizacionnatacion.Clases.Dia;
import com.e.periodizacionnatacion.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MostrarSpecificDay extends Fragment {

    private TextView txPeriodo;

    private TextView txMes;

    private TextView txSemana;

    private TextView txDia;

    private TextView txHabilidades;

    //Para mostrar el dia
    private ArrayList<Dia> dias;

    private Button pdf;

    private Button salir;

    private Button retroceder;


    /**
     * Objeto tipo CallBackListener
     */
    private CallBackListener callback;

    private ArrayList<Cronograma> cronogramas;

    private String fechaDia;

    public MostrarSpecificDay() {
        // Required empty public constructor
    }

    /**
     * Método OnActivityCreated
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof CallBackListener) {
            callback = (CallBackListener) getActivity();
        }
        if (callback != null){
            callback.onCallBack("MostrarSpecificDay", null, null, null);
            fechaDia = callback.onCallBackMostrar("MostrarSpecificDay");

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    if (callback.onCallBackCambioFragment()){
                        cronogramas = new ArrayList<Cronograma>();
                        dias = new ArrayList<Dia>();
                        cronogramas = callback.onCallBackCronograma("MostrarSpecificDay");
                        AgregarDia();
                        cancel();
                    }
                }
            }, 1000,1000);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mostrar_specific_day, container, false);
    }

    /**
     * Método OnViewCreated
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarID(view);

        final NavController navController= Navigation.findNavController(view);

        funcionBttPdf(navController);
        funcionBttSalir(navController);
        funcionBttRetroceder(navController);

    }

    /**
     * Inicializa todos los atributos de la clase MostrarInfoMonths
     * @param view vista
     */
    public void inicializarID(View view){

        txPeriodo = view.findViewById(R.id.tx3_perinfo_specific_day);
        txMes = view.findViewById(R.id.tx5_mesinfo_specific_day);
        txSemana = view.findViewById(R.id.tx7_seminfo_specific_day);
        txDia = view.findViewById(R.id.tx1_diainfo_specific_day);
        txHabilidades = view.findViewById(R.id.tx9_habinfo_specific_day);
        pdf = view.findViewById(R.id.pdf_specific_day);
        salir = view.findViewById(R.id.avan_show_specific_day);
        retroceder = view.findViewById(R.id.retro_show_specific_day);

        dias = new ArrayList<Dia>();
    }

    public void funcionBttPdf(NavController navController){

        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Generar PDF
            }
        });

    }

    /**
     * Método funcionBttRetroceder: Encargado de realizar el movimiento al fragment anterior: showCycles
     * @param navController control de navegación de la clase
     */
    public void funcionBttRetroceder(NavController navController){

        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_select_specific_day);
            }
        });

    }

    /**
     * Método funcionBttSalir: Encargado de realizar el movimiento al fragment principal: nav_home
     * @param navController control de navegación de la clase
     */
    public void funcionBttSalir(NavController navController){

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_home);
            }
        });

    }

    public void AgregarDia(){

        Cronograma referencia = cronogramas.get(0);
        String periodo = "";
        int[] pos = new int[3];

        if (validarFecha(referencia.getPeriodo1().getInicio(),referencia.getPeriodo1().getFin())){
            periodo = "Periodo1";

            //Busco las posiciones del mes, semana y dia correspondientes a la fecha seleccionada
            pos = buscarPosicion(referencia.getPeriodo1());

            //Agregar los dias de cada cronograma al array dias
            dias.add(cronogramas.get(0).getPeriodo1().getFecha().get(pos[0]).getFecha().get(pos[1]).getDias().get(pos[2]));
            dias.add(cronogramas.get(1).getPeriodo1().getFecha().get(pos[0]).getFecha().get(pos[1]).getDias().get(pos[2]));

        }else if (validarFecha(referencia.getPeriodo2().getInicio(),referencia.getPeriodo2().getFin())){
            periodo = "Periodo2";

            //Busco las posiciones del mes, semana y dia correspondientes a la fecha seleccionada
            pos = buscarPosicion(referencia.getPeriodo2());

            //Agregar los dias de cada cronograma al array dias
            dias.add(cronogramas.get(0).getPeriodo2().getFecha().get(pos[0]).getFecha().get(pos[1]).getDias().get(pos[2]));
            dias.add(cronogramas.get(1).getPeriodo2().getFecha().get(pos[0]).getFecha().get(pos[1]).getDias().get(pos[2]));

        }else if (validarFecha(referencia.getPeriodo3().getInicio(),referencia.getPeriodo3().getFin())){
            periodo = "Periodo3";

            //Busco las posiciones del mes, semana y dia correspondientes a la fecha seleccionada
            pos = buscarPosicion(referencia.getPeriodo3());

            //Agregar los dias de cada cronograma al array dias
            dias.add(cronogramas.get(0).getPeriodo3().getFecha().get(pos[0]).getFecha().get(pos[1]).getDias().get(pos[2]));
            dias.add(cronogramas.get(1).getPeriodo3().getFecha().get(pos[0]).getFecha().get(pos[1]).getDias().get(pos[2]));

        }else{

            //Dia
            txDia.setText("Competencia: "+fechaDia);

            txHabilidades.setText("\tHa elegido el día de la competencia");

            return;
        }


        //Periodo
        txPeriodo.setText("\t"+periodo);

        //Mes
        txMes.setText("\tMes"+(pos[0]+1));

        //Semana
        txSemana.setText("\tSemana"+(pos[1]+1));

        //Dia
        txDia.setText("Día"+(pos[2]+1)+": "+dias.get(0).getDiaDeSemana()+"  "+fechaDia);

        String habilidades = "";

        //Agregar valor de las habilidades de DiasAgua
        habilidades = "Trabajo en agua\n";

        if (!dias.get(0).getVolHabilidad1().isEmpty()){
            habilidades = habilidades+"\tResistencia: "+dias.get(0).getVolHabilidad1()+" ("+dias.get(0).getTipoHabilidad1()+")\n";
        }
        if (!dias.get(0).getVolHabilidad2().isEmpty()){
            habilidades = habilidades+"\tTécnica: "+dias.get(0).getVolHabilidad2()+"\n";
        }
        if (!dias.get(0).getVolHabilidad3().isEmpty()){
            habilidades = habilidades+"\tVelocidad: "+dias.get(0).getVolHabilidad3()+"\n";
        }

        //Agregar valor de las habilidades de DiasTierra

        habilidades = habilidades + "Trabajo en tierra\n";

        if (!dias.get(1).getVolHabilidad1().isEmpty()){
            habilidades = habilidades+"\tFuerza de Conversión: "+dias.get(1).getVolHabilidad1()+"\n";
        }
        if (!dias.get(1).getVolHabilidad2().isEmpty()){
            habilidades = habilidades+"\tFuerza de Construcción: "+dias.get(1).getVolHabilidad2()+"\n";
        }
        if (!dias.get(1).getVolHabilidad3().isEmpty()){
            habilidades = habilidades+"\tFuerza Máxima: "+dias.get(1).getVolHabilidad3()+"\n";
        }
        if (!dias.get(1).getVolHabilidad4().isEmpty()){
            habilidades = habilidades+"\tCoordinación: "+dias.get(1).getVolHabilidad4()+"\n";
        }
        if (!dias.get(1).getVolHabilidad5().isEmpty()){
            habilidades = habilidades+"\tFlexibilidad: "+dias.get(1).getVolHabilidad5()+"\n";
        }

        if (habilidades.equals("Trabajo en agua\nTrabajo en tierra\n")){
            habilidades = "No hay actividades para este día";
        }
        Log.e("Habilidades", habilidades+"<<<<<<<");
        txHabilidades.setText(habilidades);

    }

    public boolean validarFecha(String inicio, String fin){

        boolean valido = false;

        String[] inicioDato = inicio.split("-");
        String[] finDato = fin.split("-");
        String[] test = fechaDia.split("-");

        Calendar inicioCal= Calendar.getInstance();
        inicioCal.set(Integer.parseInt(inicioDato[2]),Integer.parseInt(inicioDato[1])-1,Integer.parseInt(inicioDato[0]));

        Calendar finCal = Calendar.getInstance();
        finCal.set(Integer.parseInt(finDato[2]),Integer.parseInt(finDato[1])-1,Integer.parseInt(finDato[0]));

        Calendar fecha = Calendar.getInstance();
        fecha.set(Integer.parseInt(test[2]),Integer.parseInt(test[1])-1,Integer.parseInt(test[0]));

        if (!fecha.before(inicioCal) && !fecha.after(finCal)){
            valido = true;
        }
        return valido;
    }

    public int[] buscarPosicion(Dato valor){
        int[] pos = new int[3];
        int tam = valor.getFecha().size();
        Dato referencia = null;
        Dia refDia = null;

        //Mes
        for (int i=0;i<tam;i++){
            referencia = valor.getFecha().get(i);
            if (validarFecha(referencia.getInicio(),referencia.getFin())){
                pos[0]=i;
                break;
            }
        }

        //Semana
        tam = valor.getFecha().get(pos[0]).getFecha().size();
        for (int i=0;i<tam;i++){
            referencia = valor.getFecha().get(pos[0]).getFecha().get(i);
            if (validarFecha(referencia.getInicio(),referencia.getFin())){
                pos[1]=i;
                break;
            }
        }

        tam = valor.getFecha().get(pos[0]).getFecha().get(pos[1]).getDias().size();
        for (int i=0;i<tam;i++){
            refDia = valor.getFecha().get(pos[0]).getFecha().get(pos[1]).getDias().get(i);
            if (refDia.getFecha().equals(fechaDia)){
                pos[2]=i;
                break;
            }
        }
        return pos;
    }


}
