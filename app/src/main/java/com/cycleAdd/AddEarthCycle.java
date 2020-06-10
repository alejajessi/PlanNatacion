package com.cycleAdd;

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

import com.e.periodizacionnatacion.MainActivity;
import com.e.periodizacionnatacion.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEarthCycle#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEarthCycle extends Fragment {

    private TextView tittletxt;
    private TextView enunciadotxt;
    private Button fconversionbtt;
    private Button fconstruccionbtt;
    private Button fmaxbtt;
    private Button avanzar;
    private Button retroceder;

    public AddEarthCycle() {
            // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_add_earth_cycle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarID(view);

        final NavController navController= Navigation.findNavController(view);

        funcionBttAvanzar(navController);

        funcionBttRetroceder(navController);

    }

    public void inicializarID(View view){

        tittletxt = view.findViewById(R.id.tx_enun1_earthcycle);
        enunciadotxt = view.findViewById(R.id.tx_enun2_earthcycle);
        fconstruccionbtt = view.findViewById(R.id.fuer_const_earthcycle);
        fconversionbtt = view.findViewById(R.id.fuer_conv_earthcycle);
        fmaxbtt = view.findViewById(R.id.fuer_max_earthcycle);


        avanzar = view.findViewById(R.id.avan_earthcycle);
        retroceder = view.findViewById(R.id.retro_earthcycle);

    }

    //Método funcionBttAvanzar: Encargado de realizar el movimiento al fragment siguiente: AddVolumenCycle, a tráves del NavController
    public void funcionBttAvanzar(NavController navController){

        avanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //crearMacroCiclo(actividad);
                    Navigation.findNavController(v).navigate(R.id.nav_volumen);
            }
        });
    }

    //Método funcionBttRetroceder: Encargado de realizar el movimiento del fragment anterior: Home, a tráves del NavController
    public void funcionBttRetroceder(NavController navController){

        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_addwater);
            }
        });

    }

    public void agregarDiasTierra(MainActivity actividad){
        //Pido los datos de los dias
        actividad.agregarDiasTierra("Construccion","Conversion","Maximo");
    }

    }
