package com.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.dialog.Dialog_Dias;
import com.e.periodizacionnatacion.MainActivity;
import com.e.periodizacionnatacion.R;

import java.util.ArrayList;

public class AddWaterCycle_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final CharSequence[] SEMANA= {"Lunes", "Martes", "Miércoles","Jueves","Viernes","Sábado","Domingo"};



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button bttResistencia;
    private Button bttVelocidad;
    private Button bttTecnica;
    private Button avanzar;
    private Button retroceder;
    private FragmentActivity actividad;

    public AddWaterCycle_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddVolumenCycle.
     */
    // TODO: Rename and change types and number of parameters
    public static AddWaterCycle_Fragment newInstance(String param1, String param2) {
        AddWaterCycle_Fragment fragment = new AddWaterCycle_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_water_cycle, container, false);
    }

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

    public void inicializarID(View view){

        bttResistencia = view.findViewById(R.id.btt_res_watercycle);
        bttTecnica = view.findViewById(R.id.btt_tec_watercycle);
        bttVelocidad = view.findViewById(R.id.btt_vel_watercycle);
        avanzar = view.findViewById(R.id.avan_addwater);
        retroceder = view.findViewById(R.id.retro_addwater);

    }

    public void funcionBttResistencia(){

        bttResistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_Dias dialogDias = new Dialog_Dias(actividad);
                dialogDias.selectDaysOfWeek().show();
            }
        });

    }

    public void funcionBttAvanzar(NavController navController){

        avanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.nav_earthcycle);

            }
        });
    }


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
