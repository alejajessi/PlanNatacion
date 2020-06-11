package com.cycleadd;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.e.periodizacionnatacion.MainActivity;
import com.e.periodizacionnatacion.R;

import java.util.ArrayList;


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
    private ArrayList<ArrayList> diasTierra;
    private FragmentActivity actividad;

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

    public  void funcionFMax(NavController navController){


    }

    public void funcionFConversionBtt(NavController navController){

        fconversionbtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog dialog= crearDialogo();
                dialog.show();
            }
        });
    }

    public void funcionFConstruccionBtt(NavController navController){

        fconstruccionbtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog= crearDialogo();
                dialog.show();
            }
        });
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

    public AlertDialog crearDialogo(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = actividad.getLayoutInflater();

        View v= inflater.inflate(R.layout.dialog_semana, null);

        builder.setView(v);

        final ArrayList<CheckBox> semana = organizacionDias(v);
        final ArrayList itemSelected = new ArrayList();

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < semana.size(); i++) {
                    if (semana.get(i).isChecked()) {
                        itemSelected.add(semana.get(i).getText());
                    }
                }
                diasTierra.add(itemSelected);
            }
        });

        builder.setNegativeButton("Cancelar",null);

        return  builder.create();
    }

    public ArrayList<CheckBox> organizacionDias(View v){
        CheckBox lunes = v.findViewById(R.id.lunes_dialogo);
        CheckBox martes = v.findViewById(R.id.martes_dialogo);
        CheckBox miercoles = v.findViewById(R.id.mierco_dialogo);
        CheckBox jueves = v.findViewById(R.id.jueves_dialogo);
        CheckBox viernes = v.findViewById(R.id.viernes_dialogo);
        CheckBox sabado = v.findViewById(R.id.sabado_dialogo);
        CheckBox domingo = v.findViewById(R.id.domingo_dialogo);

        final ArrayList<CheckBox> semana = new ArrayList<CheckBox>();

        semana.add(lunes);
        semana.add(martes);
        semana.add(miercoles);
        semana.add(jueves);
        semana.add(viernes);
        semana.add(sabado);
        semana.add(domingo);

        return semana;
    }


    public void agregarDiasTierra(MainActivity actividad){
        //Pido los datos de los dias
        actividad.agregarDiasTierra("Construccion","Conversion","Maximo");
    }

    }
