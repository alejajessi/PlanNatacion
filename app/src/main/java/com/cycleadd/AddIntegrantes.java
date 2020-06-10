package com.cycleadd;

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
import android.widget.ListView;

import com.e.periodizacionnatacion.R;

public class AddIntegrantes extends Fragment {

    private ListView lview1;
    private Button bttadd;
    private Button crear;
    private Button retroceder;

    public AddIntegrantes() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_integrantes, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarID(view);

        final NavController navController= Navigation.findNavController(view);

        funcionBttCrear();

        funcionBttRetroceder(navController);

    }

    public void inicializarID(View view){

        lview1 = view.findViewById(R.id.lview_addinteg);
        bttadd = view.findViewById(R.id.bttmore_addinteg);
        crear = view.findViewById(R.id.avan_addinteg);
        retroceder = view.findViewById(R.id.retro_addinteg);

    }

    //Método funcionBttAvanzar: Encargado de realizar el movimiento al fragment siguiente: AddVolumenCycle, a tráves del NavController
    public void funcionBttCrear(){

        avanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //Método funcionBttRetroceder: Encargado de realizar el movimiento del fragment anterior: Home, a tráves del NavController
    public void funcionBttRetroceder(NavController navController){

        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_vistaprevia);
            }
        });

    }
}
