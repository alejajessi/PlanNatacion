package com.cycleadd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.callback.CallBackListener;
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

    private ArrayList<ArrayList> diasAgua;

    private ArrayList<String> resis;

    private ArrayList<String> tec;

    private ArrayList<String> vel;

    private String cualBoton;

    private CallBackListener callback;

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof CallBackListener) {
            callback = (CallBackListener) getActivity();
        }
    }
    //Método OnViewCreated: maneja varias funcionalidades de los componentes xml
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarID(view);

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
        diasAgua = new ArrayList<ArrayList>();
        resis = new ArrayList<String>();
        tec = new ArrayList<String>();
        vel = new ArrayList<String>();

    }

    //Método funcionBttResistencia: Encargado de emerger el dialogo para la selección de días en la semana a trabajar resistencia
    public void funcionBttResistencia(){

        bttResistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cualBoton = "Resistencia";
                AlertDialog dialog= crearDialogo();
                dialog.show();
            }
        });
    }

    public AlertDialog crearDialogo(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v= inflater.inflate(R.layout.dialog_semana, null);

        builder.setView(v);

        final ArrayList<CheckBox> semana = organizacionDias(v);
        final ArrayList<String> itemSelected = new ArrayList<String>();

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < semana.size(); i++) {
                    if (semana.get(i).isChecked()) {
                        itemSelected.add(semana.get(i).getText().toString().trim());
                    }
                }
                if (cualBoton.equals("Resistencia")){
                    resis = itemSelected;
                    cualBoton = "";
                }else if (cualBoton.equals("Tecnica")){
                    tec = itemSelected;
                    cualBoton = "";
                }else if (cualBoton.equals("Velocidad")){
                    vel = itemSelected;
                    cualBoton = "";
                }
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

        if (cualBoton.equals("Resistencia") && !resis.isEmpty()){
            for (int i=0;i<semana.size();i++){
                for (int j=0;j<resis.size();j++){
                    if (semana.get(i).getText().toString().trim().equals(resis.get(j))){
                        semana.get(i).setChecked(true);
                        break;
                    }
                }
            }
        }else if (cualBoton.equals("Tecnica") && !tec.isEmpty()){
            for (int i=0;i<semana.size();i++){
                for (int j=0;j<tec.size();j++){
                    if (semana.get(i).getText().toString().trim().equals(tec.get(j))){
                        semana.get(i).setChecked(true);
                        break;
                    }
                }
            }
        }else if (cualBoton.equals("Velocidad") && !vel.isEmpty()){
            for (int i=0;i<semana.size();i++){
                for (int j=0;j<vel.size();j++){
                    if (semana.get(i).getText().toString().trim().equals(vel.get(j))){
                        semana.get(i).setChecked(true);
                        break;
                    }
                }
            }
        }

        return semana;
    }

    //Método funcionBttAvanzar: Encargado de realizar el movimiento al fragment siguiente: AddEarthCycle, a tráves del NavController
    public void funcionBttAvanzar(NavController navController){

        avanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!resis.isEmpty() && !tec.isEmpty() && !vel.isEmpty()) {
                    diasAgua.add(resis);
                    diasAgua.add(tec);
                    diasAgua.add(vel);
                    agregarDiasAgua();
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
                cualBoton = "Tecnica";
                AlertDialog dialog= crearDialogo();
                dialog.show();
            }
        });
    }

    public void funcionBttVelocidad (){

        bttVelocidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cualBoton = "Velocidad";
                AlertDialog dialog= crearDialogo();
                dialog.show();
            }
        });
    }

    public void agregarDiasAgua(){
        //Pido los datos de los dias
        String resistencia = "";
        String tecnica = "";
        String velocidad = "";

        for (int i=0;i<diasAgua.size();i++){
            ArrayList<String> trabajo = diasAgua.get(i);
            for (int j=0;j<trabajo.size();j++){
                if (i==0){
                    resistencia += trabajo.get(j)+"-";
                }else if (i==1){
                    tecnica += trabajo.get(j)+"-";
                }else {
                    velocidad += trabajo.get(j)+"-";
                }
            }
        }
        if (callback != null){
            callback.onCallBack("AddWaterCycle",resistencia,tecnica,velocidad);
        }
    }
}
