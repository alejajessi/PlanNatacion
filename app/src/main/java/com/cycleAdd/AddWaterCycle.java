package com.cycleAdd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dialog.Dialog_Dias;
import com.e.periodizacionnatacion.MainActivity;
import com.e.periodizacionnatacion.R;

public class AddWaterCycle extends Fragment {

    //Componente gráfico del xml fragment_add_watercycle tipo Button
    private Button bttResistencia;

    //Componente gráfico del xml fragment_add_watercycle tipo Button
    private Button bttVelocidad;

    //Componente gráfico del xml fragment_add_watercycle tipo Button
    private Button bttTecnica;

    //Componente gráfico del xml fragment_add_watercycle tipo Button
    private Button avanzar;

    //Componente gráfico del xml fragment_add_watercycle tipo Button
    private Button retroceder;

    //Componente gráfico del xml fragment_add_watercycle tipo Button
    private FragmentActivity actividad;

    //Constructor de la clase AddWaterCycle
    public AddWaterCycle() {
        // Required empty public constructor
    }

    //Método onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_water_cycle, container, false);
    }

    //Método OnViewCreated: maneja varias funcionalidades de los componentes xml
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarID(view);

        actividad = getActivity();

        funcionBttResistencia();

        funcionBttTecnica();

        funcionBttVelocidad();

        final NavController navController = Navigation.findNavController(view);

        funcionBttAvanzar(navController);

        funcionBttRetroceder(navController);

    }

    //Método inicializar ID, se encarga de definir el ID correspondiente para cada componente xml con los declarados en la clase.
    public void inicializarID(View view){

        bttResistencia = view.findViewById(R.id.btt_res_watercycle);
        bttTecnica = view.findViewById(R.id.btt_tec_watercycle);
        bttVelocidad = view.findViewById(R.id.btt_vel_watercycle);
        avanzar = view.findViewById(R.id.avan_addwater);
        retroceder = view.findViewById(R.id.retro_addwater);

    }

    //Método funcionBttResistencia: Encargado de emerger el dialogo para la selección de días en la semana a trabajar resistencia
    public void funcionBttResistencia(){

        bttResistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_Dias dialogDias = new Dialog_Dias(actividad);
                dialogDias.selectDaysOfWeek().show();
            }
        });

    }

    //Método funcionBttAvanzar: Encargado de realizar el movimiento al fragment siguiente: AddEarthCycle, a tráves del NavController
    public void funcionBttAvanzar(NavController navController){

        avanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.nav_earthcycle);

            }
        });
    }


    //Método funcionBttRetroceder: Encargado de realizar el movimiento del fragment anterior: AddCycle, a tráves del NavController
    public void funcionBttRetroceder(NavController navController){

       retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.nav_addcyle);

            }
        });
    }

    public void funcionBttTecnica (){

        bttTecnica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void funcionBttVelocidad (){

        bttVelocidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void agregarDiasAgua(MainActivity actividad){
        //Pido los datos de los dias
        actividad.agregarDiasAgua("Resistencia","Tecnica","Velocidad");
    }
}
