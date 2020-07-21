package com.cycleshow;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.callback.CallBackListener;
import com.e.periodizacionnatacion.Clases.Dato;
import com.e.periodizacionnatacion.Clases.Dia;
import com.e.periodizacionnatacion.R;

import java.util.ArrayList;

public class MostrarInfoDay extends Fragment {

    private TextView txPeriodo;

    private TextView txMes;

    private TextView txSemana;

    private TextView txDia;

    private TextView txHabilidades;

    //Para mostrar el dia
    private Dia dia;

    private CallBackListener callback;

    private String mostrando;

    /**
     * Componente gráfico del xml fragment_mostrar_info_day tipo Button
     */
    private Button salir;

    /**
     * Componente gráfico del xml fragment_mostrar_info_day tipo Button
     */
    private Button retroceder;


    /**
     * Constructor de la clase MostrarInfoDay
     */
    public MostrarInfoDay() {
        // Required empty public constructor
    }

    /**
     *Método OnCreateView heredado de Fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mostrar_info_day, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof CallBackListener) {
            callback = (CallBackListener) getActivity();
        }
        if (callback != null) {
            dia = callback.onCallBackMostrarDia("MostrarInfoDay");
            mostrando = callback.onCallBackMostrar("MostrarInfoDay");
            Log.e("Llega a "," MostrarInfoDay <<<<<<< "+mostrando);
        }
        AgregarDia();
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

        funcionBttSalir(navController);
        funcionBttRetroceder(navController);

    }

    /**
     * Inicializa todos los atributos de la clase MostrarInfoMonths
     * @param view vista
     */
    public void inicializarID(View view){
        txPeriodo = view.findViewById(R.id.tx3_per_infoday);
        txMes = view.findViewById(R.id.tx5_mes_infoday);
        txSemana = view.findViewById(R.id.tx7_sem_infoday);
        txDia = view.findViewById(R.id.tx1_diainfo);
        txHabilidades = view.findViewById(R.id.tx9_hab_infoday);
        salir = view.findViewById(R.id.avan_minfoDay);
        retroceder = view.findViewById(R.id.retro_minfoDay);

        dia = null;
    }

    /**
     * Método funcionBttRetroceder: Encargado de realizar el movimiento al fragment anterior: showWeeks
     * @param navController control de navegación de la clase
     */
    public void funcionBttRetroceder(NavController navController){

        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dia = null;
                int largo = mostrando.length() - 2;
                mostrando = mostrando.substring(0,largo);
                callback.onCallBack("MostrarInfoDay",mostrando,"",null);
                mostrando = callback.onCallBackMostrar("MostrarInfoDay");
                Navigation.findNavController(v).navigate(R.id.nav_showWeeks);
            }
        });

    }

    /**
     * Método funcionBttSalir: Encargado de realizar el movimiento al fragment principal: nav_home
     * @param navController control de navegación de la clase
     */
    public void funcionBttSalir(NavController navController){

        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_home);
            }
        });

    }

    public void AgregarDia(){

        if (dia != null){


            Log.e("Dia", dia.getFecha()+"<<<<<<<");
            String[] datos = mostrando.split("-");

            //Periodo
            txPeriodo.setText(datos[1]);

            //Mes
            int mes= Integer.parseInt(datos[2]);
            txMes.setText("Mes"+(mes+1));

            //Semana
            int semana= Integer.parseInt(datos[3]);
            txSemana.setText("Semana"+(semana+1));

            //Dia
            int numDia= Integer.parseInt(datos[4]);
            txDia.setText("Día"+(numDia+1)+": "+dia.getFecha());

            String habilidades = "";

            if (datos[0].equals("Agua")){

                if (!dia.getVolHabilidad1().isEmpty()){
                    habilidades = "Resistencia: "+dia.getVolHabilidad1()+"\n";
                }
                if (!dia.getVolHabilidad2().isEmpty()){
                    habilidades = habilidades+"Técnica: "+dia.getVolHabilidad2()+"\n";
                }
                if (!dia.getVolHabilidad3().isEmpty()){
                    habilidades = habilidades+"Velocidad: "+dia.getVolHabilidad3()+"\n";
                }
                if (!dia.getVolHabilidad4().isEmpty()){
                    habilidades = habilidades+"H4: "+dia.getVolHabilidad4()+"\n";
                }
                if (!dia.getVolHabilidad5().isEmpty()){
                    habilidades = habilidades+"H5: "+dia.getVolHabilidad5()+"\n";
                }

            }else if (datos[0].equals("Tierra")){

                if (!dia.getVolHabilidad1().isEmpty()){
                    habilidades = "Fuerza de Conversión: "+dia.getVolHabilidad1()+"\n";
                }
                if (!dia.getVolHabilidad2().isEmpty()){
                    habilidades = habilidades+"Fuerza de Construcción: "+dia.getVolHabilidad2()+"\n";
                }
                if (!dia.getVolHabilidad3().isEmpty()){
                    habilidades = habilidades+"Fuerza Máxima: "+dia.getVolHabilidad3()+"\n";
                }
                if (!dia.getVolHabilidad4().isEmpty()){
                    habilidades = habilidades+"H4: "+dia.getVolHabilidad4()+"\n";
                }
                if (!dia.getVolHabilidad5().isEmpty()){
                    habilidades = habilidades+"H5: "+dia.getVolHabilidad5()+"\n";
                }

            }

            if (habilidades.isEmpty()){
                habilidades = "No hay actividades para este día";
            }
            Log.e("Habilidades", habilidades+"<<<<<<<");
            txHabilidades.setText(habilidades);

        }else{
            Toast.makeText(getContext(),"No es posible acceder al día",Toast.LENGTH_LONG).show();
            Navigation.findNavController(getView()).navigate(R.id.nav_showWeeks);
        }

    }
}
