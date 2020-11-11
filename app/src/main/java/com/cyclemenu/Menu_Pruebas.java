package com.cyclemenu;

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

import com.e.periodizacionnatacion.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Menu_Pruebas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Menu_Pruebas extends Fragment {


    private Button retroceder;
    private Button anhadir;
    private Button ver;
    private Button prueba;
    private Button estadisticas;


    public Menu_Pruebas() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu__pruebas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inicializarID(view);

        final NavController navController= Navigation.findNavController(view);

        funcionBttAnhadir(navController);
        funcionBttRetroceder(navController);
        funcionBttPrueba(navController);
    }

    public void inicializarID(View view){

        retroceder = view.findViewById(R.id.retro_menu_pruebas);
        anhadir = view.findViewById(R.id.bttadd_notificaction);
        ver = view.findViewById(R.id.bttview_pruebas);
        prueba = view.findViewById(R.id.bttadd_prueba);
        estadisticas = view.findViewById(R.id.btt_view_stadistics);

    }

    public void funcionBttRetroceder(NavController navController){

        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_home);
            }
        });

    }

    public void funcionBttAnhadir(NavController navController){

        anhadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_select_Cycle);
            }
        });
    }

    public void funcionBttPrueba(NavController navController){
        prueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_select_cycle_test);
            }
        });
    }

}