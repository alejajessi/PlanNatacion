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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.e.periodizacionnatacion.R;

import java.util.ArrayList;

public class MostrarInfoDay extends Fragment {

    /**
     * Componente gráfico del xml fragment_mostrar_info_day tipo TextView
     */
    private TextView enun1txt;

    /**
     * Componente gráfico del xml fragment_mostrar_info_day tipo TextView
     */
    private TextView enun2txt;

    /**
     * Componente gráfico del xml fragment_mostrar_info_day tipo TextView
     */
    private TextView enun3txt;

    /**
     * Componente gráfico del xml fragment_mostrar_info_day tipo ListView
     */
    private ListView lview1;

    /**
     * Componente gráfico del xml fragment_mostrar_info_day tipo ListView
     */
    private ListView lview2;

    /**
     * Componente gráfico del xml fragment_mostrar_info_day tipo Button
     */
    private Button salir;

    /**
     * Componente gráfico del xml fragment_mostrar_info_day tipo Button
     */
    private Button retroceder;

    /**
     * Componente gráfico del xml fragment_mostrar_info_day tipo Button
     */
    private Button opciones;

    /**
     * Objeto tipo ArrayAdapter para modificación del ListView 1
     */
    private ArrayAdapter<String> adaptadorAgua;

    /**
     * Objeto tipo ArrayList para  asignar información de la lista del ListView 1
     */
    private ArrayList<String> diasAgua;

    /**
     * Objeto tipo ArrayAdapter para modificación del ListView 2
     */
    private ArrayAdapter<String> adaptadorTierra;

    /**
     * Objeto tipo ArrayList para  asignar información de la lista del ListView 2
     */
    private ArrayList<String> diasTierra;

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

        enun1txt = view.findViewById(R.id.enun1_minfoDay);
        enun2txt = view.findViewById(R.id.enun2_minfoDay);
        enun3txt = view.findViewById(R.id.enun3_minfoDay);
        lview1 = view.findViewById(R.id.list1_minfoDay);
        lview2 = view.findViewById(R.id.list2_minfoDay);
        salir = view.findViewById(R.id.avan_minfoDay);
        retroceder = view.findViewById(R.id.retro_minfoDay);
        opciones = view.findViewById(R.id.opc_minfoDay);

        diasAgua = new ArrayList<String>();
        adaptadorAgua = new ArrayAdapter<String>(getContext(),R.layout.mes_trabajo,diasAgua);
        lview2.setAdapter(adaptadorAgua);

        diasTierra = new ArrayList<String>();
        adaptadorTierra = new ArrayAdapter<String>(getContext(),R.layout.mes_trabajo,diasTierra);
        lview1.setAdapter(adaptadorTierra);


    }

    /**
     * Método funcionBttRetroceder: Encargado de realizar el movimiento al fragment anterior: showWeeks
     * @param navController control de navegación de la clase
     */
    public void funcionBttRetroceder(NavController navController){

        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

}
