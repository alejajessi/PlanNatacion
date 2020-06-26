package com.cycleshow;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.callback.CallBackListener;
import com.e.periodizacionnatacion.Clases.MacroCiclo;
import com.e.periodizacionnatacion.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MostrarInfoCycle extends Fragment {

    /**
     * Componente gráfico del xml fragment_mostrar_info_cycle tipo TextView
     */
    private TextView txNombre;

    /**
     * Componente gráfico del xml fragment_mostrar_info_cycle tipo TextView
     */
    private TextView txInicio;

    /**
     * Componente gráfico del xml fragment_mostrar_info_cycle tipo TextView
     */
    private TextView txFin;

    /**
     * Componente gráfico del xml fragment_mostrar_info_cycle tipo TextView
     */
    private TextView txVolAgua;

    /**
     * Componente gráfico del xml fragment_mostrar_info_cycle tipo TextView
     */
    private TextView txVolTierra;

    /**
     * Componente gráfico del xml fragment_mostrar_info_cycle tipo Button
     */
    private Button avanzar;

    /**
     * Componente gráfico del xml fragment_mostrar_info_cycle tipo Button
     */
    private Button retroceder;

    /**
     * Componente gráfico del xml fragment_mostrar_info_day tipo Button
     */
    private Button opciones;

    /**
     * Objeto tipo MacroCiclo
     */
    private MacroCiclo macroClico;

    /**
     * Objeto tipo CallBackListener
     */
    private CallBackListener callback;

    /**
     * Constructor de la clase MostrarInfoCycle
     */
    public MostrarInfoCycle() {
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
        return inflater.inflate(R.layout.fragment_mostrar_info_cycle, container, false);
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
            macroClico = callback.onCallBackInfoCycle("MostrarInfoCycle");
        }
        if (macroClico == null){
            Toast.makeText(getContext(),"No es posible acceder al MacroCiclo",Toast.LENGTH_LONG).show();
            Navigation.findNavController(getView()).navigate(R.id.nav_showcycle);
        }else{
            cargarDatosMacroCiclo(macroClico);
        }
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

        funcionBttAvanzar(navController);
        funcionBttRetroceder(navController);

    }

    /**
     * Inicializa todos los atributos de la clase MostrarInfoCycle
     * @param view vista
     */
    public void inicializarID(View view){

        txFin = view.findViewById(R.id.tx6_nomb_infocycle);
        txInicio = view.findViewById(R.id.tx4_nomb_infocycle);
        txNombre = view.findViewById(R.id.tx2_nomb_infocycle);
        txVolAgua = view.findViewById(R.id.tx10_nomb_infocycle);
        txVolTierra = view.findViewById(R.id.tx8_nomb_infocycle);
        avanzar = view.findViewById(R.id.avan_infocycle);
        retroceder = view.findViewById(R.id.retro_infocycle);
        opciones = view.findViewById(R.id.opc_infocycle);

    }

    /**
     * Método cargarDatosMacroCiclo para mostrar la información
     * nombre - inicio - fin - volumen1 - volumen2
     * @param ciclo Objeto tipo MacroCiclo
     */
    public void cargarDatosMacroCiclo(MacroCiclo ciclo){

        String nombre = ciclo.getNombre();
        String inicio = ciclo.getInicio();
        String fin = ciclo.getFin();
        String vol1 = ciclo.getDiasTierra().getVolumen();
        String vol2 = ciclo.getDiasAgua().getVolumen();

        txNombre.setText(nombre);
        txInicio.setText(inicio);
        txFin.setText(fin);
        txVolTierra.setText(vol1);
        txVolAgua.setText(vol2);

    }

    /**
     * Método funcionBttAvanzar: Encargado de realizar el movimiento al fragment siguiente: showMonths
     * @param navController control de navegación de la clase
     */
    public void funcionBttAvanzar(NavController navController){

        avanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_showMonths);
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
                Navigation.findNavController(v).navigate(R.id.nav_showcycle);
            }
        });

    }

}
