package com.cycleadd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.e.periodizacionnatacion.MainActivity;
import com.e.periodizacionnatacion.R;

import java.util.ArrayList;

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

    private ArrayList<ArrayList> diasAgua;

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
        diasAgua= new ArrayList<>();

    }

    //Método funcionBttResistencia: Encargado de emerger el dialogo para la selección de días en la semana a trabajar resistencia
    public void funcionBttResistencia(){

        bttResistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog= crearDialogo();
                dialog.show();
            }
        });
    }

    public AlertDialog crearDialogo(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = actividad.getLayoutInflater();

        View v= inflater.inflate(R.layout.dialog_semana, null);

        builder.setView(v);

        CheckBox lunes = v.findViewById(R.id.lunes_dialogo);
        CheckBox martes = v.findViewById(R.id.martes_dialogo);
        CheckBox miercoles = v.findViewById(R.id.mierco_dialogo);
        CheckBox jueves = v.findViewById(R.id.jueves_dialogo);
        CheckBox viernes = v.findViewById(R.id.viernes_dialogo);
        CheckBox sabado = v.findViewById(R.id.sabado_dialogo);
        CheckBox domingo = v.findViewById(R.id.domingo_dialogo);

        final ArrayList<CheckBox> semana = new ArrayList<>();
        final ArrayList itemSelected = new ArrayList();

        semana.add(lunes);
        semana.add(martes);
        semana.add(miercoles);
        semana.add(jueves);
        semana.add(viernes);
        semana.add(sabado);
        semana.add(domingo);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < semana.size(); i++) {
                    if (semana.get(i).isChecked()) {
                        itemSelected.add(semana.get(i).getText());
                    }
                }
                diasAgua.add(itemSelected);
            }
        });

        builder.setNegativeButton("Cancelar",null);
        return  builder.create();
    }

    //Método funcionBttAvanzar: Encargado de realizar el movimiento al fragment siguiente: AddEarthCycle, a tráves del NavController
    public void funcionBttAvanzar(NavController navController){

        avanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (diasAgua.size()==3) {
                    Navigation.findNavController(v).navigate(R.id.nav_earthcycle);
                }else{
                    Toast.makeText(getContext(),"Selecciona los días para todas las opciones",Toast.LENGTH_LONG).show();
                }
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
                AlertDialog dialog= crearDialogo();
                dialog.show();
            }
        });
    }

    public void funcionBttVelocidad (){

        bttVelocidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog= crearDialogo();
                dialog.show();
            }
        });
    }

    public void agregarDiasAgua(MainActivity actividad){
        //Pido los datos de los dias
        actividad.agregarDiasAgua("Resistencia","Tecnica","Velocidad");
    }
}
