package com.cycleadd;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.callback.CallBackListener;
import com.e.periodizacionnatacion.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class AddEarthCycle extends Fragment {

    private TextView tittletxt;
    private TextView enunciadotxt;
    private Button fconversionbtt;
    private Button fconstruccionbtt;
    private Button fmaxbtt;
    private Button coordi;
    private Button flexi;
    private Button avanzar;
    private Button retroceder;

    private ArrayList<ArrayList> diasTierra;

    private ArrayList<String> conv;

    private ArrayList<String> cons;

    private ArrayList<String> max;

    private String cualBoton;

    private CallBackListener callback;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof CallBackListener) {
            callback = (CallBackListener) getActivity();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarID(view);

        funcionFConstruccionBtt();

        funcionFConversionBtt();

        funcionFMax();

        funcionCoordi();

        funcionFlexi();

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
        coordi = view.findViewById(R.id.coordi_earthcycle);
        avanzar = view.findViewById(R.id.avan_earthcycle);
        retroceder = view.findViewById(R.id.retro_earthcycle);
        diasTierra = new ArrayList<ArrayList>();
        conv = new ArrayList<String>();
        cons = new ArrayList<String>();
        max = new ArrayList<String>();

    }

    public  void funcionFMax(){

        fmaxbtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cualBoton = "Maxima";
                AlertDialog dialog= crearDialogo();
                dialog.show();
            }
        });

    }

    public void funcionFConversionBtt(){

        fconversionbtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cualBoton = "Conversion";
                AlertDialog dialog= crearDialogo();
                dialog.show();
            }
        });
    }
    public  void funcionCoordi(){

        fmaxbtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cualBoton = "Coordinacion";
                AlertDialog dialog= crearDialogo();
                dialog.show();
            }
        });

    }

    public void funcionFlexi(){

        fconversionbtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cualBoton = "Flexibilidad";
                AlertDialog dialog= crearDialogo();
                dialog.show();
            }
        });
    }

    public void funcionFConstruccionBtt(){

        fconstruccionbtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cualBoton = "Construccion";
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
                if (!conv.isEmpty() && !cons.isEmpty() && !max.isEmpty()) {
                    diasTierra.add(conv);
                    diasTierra.add(cons);
                    diasTierra.add(max);
                    agregarDiasTierra();
                    Navigation.findNavController(v).navigate(R.id.nav_volumen);
                }else{
                    Toast.makeText(getContext(),"Selecciona los días para todas las opciones",Toast.LENGTH_LONG).show();
                }
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
                if (cualBoton.equals("Conversion")){
                    conv = itemSelected;
                    cualBoton = "";
                }else if (cualBoton.equals("Construccion")){
                    cons = itemSelected;
                    cualBoton = "";
                }else if (cualBoton.equals("Maxima")){
                    max = itemSelected;
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

        if (cualBoton.equals("Conversion") && !conv.isEmpty()){
            for (int i=0;i<semana.size();i++){
                for (int j=0;j<conv.size();j++){
                    if (semana.get(i).getText().toString().trim().equals(conv.get(j))){
                        semana.get(i).setChecked(true);
                        break;
                    }
                }
            }
        }else if (cualBoton.equals("Construccion") && !cons.isEmpty()){
            for (int i=0;i<semana.size();i++){
                for (int j=0;j<cons.size();j++){
                    if (semana.get(i).getText().toString().trim().equals(cons.get(j))){
                        semana.get(i).setChecked(true);
                        break;
                    }
                }
            }
        }else if (cualBoton.equals("Maxima") && !max.isEmpty()){
            for (int i=0;i<semana.size();i++){
                for (int j=0;j<max.size();j++){
                    if (semana.get(i).getText().toString().trim().equals(max.get(j))){
                        semana.get(i).setChecked(true);
                        break;
                    }
                }
            }
        }

        return semana;
    }

    public void agregarDiasTierra(){
        //Pido los datos de los dias
        String construccion = "";
        String conversion = "";
        String maxima = "";

        for (int i=0;i<diasTierra.size();i++){
            ArrayList<String> trabajo = diasTierra.get(i);
            for (int j=0;j<trabajo.size();j++){
                if (i==0){
                    construccion += trabajo.get(j)+"-";
                }else if (i==1){
                    conversion += trabajo.get(j)+"-";
                }else {
                    maxima += trabajo.get(j)+"-";
                }
            }
        }
        if (callback != null){
            callback.onCallBack("AddEarthCycle",construccion, conversion, maxima);
        }
    }

}
